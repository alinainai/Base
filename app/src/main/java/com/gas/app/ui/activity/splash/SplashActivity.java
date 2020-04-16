package com.gas.app.ui.activity.splash;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.gas.app.R;
import com.gas.app.ui.activity.splash.di.DaggerSplashComponent;
import com.gas.app.ui.activity.splash.mvp.SplashContract;
import com.gas.app.ui.activity.splash.mvp.SplashPresenter;
import com.gas.app.ui.main.MainActivity;
import com.gas.app.utils.ToastUtil;
import com.gas.app.view.MyVideoView;
import com.lib.commonsdk.constants.Constants;
import com.lib.commonsdk.constants.RouterHub;
import com.lib.commonsdk.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


@Route(path = RouterHub.APP_SPLASHACTIVITY)
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    public static final String VIDEO_NAME = "welcome_video.mp4";
    private boolean isRelease = false;

    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_skip)
    TextView tv_skip;
    @BindView(R.id.rl_video)
    RelativeLayout mRlVideo;

    private MyVideoView mVideoView;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

        DaggerSplashComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mPresenter.showVersionCode();

        try {
            File videoFile = getFileStreamPath(VIDEO_NAME);
            if (!videoFile.exists()) {
                videoFile = copyVideoFile();
            }
            playVideo(videoFile);

        } catch (Exception e) {
            //如果有异常背景变为黑

        }
        Objects.requireNonNull(mPresenter).autoTimeDown();

    }


    /**
     * 播放背景动画
     *
     * @param videoFile
     */
    private void playVideo(File videoFile) {

        mVideoView = new MyVideoView(this.getApplicationContext());
        mVideoView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        mRlVideo.addView(mVideoView);
        mVideoView.setVideoPath(videoFile.getPath());
        mVideoView.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        });
        mVideoView.setOnErrorListener((mp, what, extra) -> true);

    }

    /**
     * 复制文件到/data/data/目录下
     *
     * @return
     */
    @NonNull
    private File copyVideoFile() {
        File videoFile;
        FileOutputStream fos = null;
        InputStream in = null;
        try {
            fos = openFileOutput(VIDEO_NAME, MODE_PRIVATE);
            in = getResources().openRawResource(R.raw.welcome_video);
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        videoFile = getFileStreamPath(VIDEO_NAME);
        if (!videoFile.exists())
            throw new RuntimeException("video file has problem, are you sure you have welcome_video.mp4 in res/raw folder?");
        return videoFile;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.CODE_REQUEST_PERMISSION && grantResults.length > 0) {
            if (permissions.length > 0 && permissions.length == grantResults.length) {
                for (int grantResult : grantResults) {
                    //do something
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        ToastUtil.show(SplashActivity.this, "请去设置中开启软件的相关权限，否则软件不能正常使用");
                        break;
                    }
                }
            }
        }

    }


    @OnClick(R.id.tv_skip)
    public void onViewClicked() {
        tv_skip.setEnabled(false);
        mPresenter.turnToMainForce();
    }


    @Override
    public void goMainActivity() {


        if (isRelease) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            Utils.navigation(this, RouterHub.ZHIHU_HOMEACTIVITY, new NavCallback() {
                @Override
                public void onArrival(Postcard postcard) {

                }

                @Override
                public void onLost(Postcard postcard) {
                    super.onLost(postcard);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            });
        }
        finish();
    }

    @Override
    public void showTimeDown(long time) {
        tv_skip.setText(String.format(Locale.CHINA, "跳过 %d", time));
    }

    @Override
    public void showVersionCode(String code) {
        tv_version.setText(String.format("当前版本 v%s", code));
    }


    @Override
    protected void onDestroy() {
        if (null != mVideoView) {
            mVideoView.suspend();
            mVideoView.setOnErrorListener(null);
            mVideoView.setOnPreparedListener(null);
            mVideoView.setOnCompletionListener(null);
            mVideoView = null;
            mRlVideo.removeAllViews();
        }
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name))
                    return getApplicationContext().getSystemService(name);
                return super.getSystemService(name);
            }
        });
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
