package trunk.doi.base.ui.activity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.util.Const;
import trunk.doi.base.base.RxBus;
import trunk.doi.base.bean.rxmsg.MainEvent;
import trunk.doi.base.bean.User;
import trunk.doi.base.dialog.AlertDialog;
import trunk.doi.base.util.AppUtils;
import trunk.doi.base.util.SPUtils;
import trunk.doi.base.util.ScreenUtils;
import trunk.doi.base.view.MyVideoView;



/**
 * Created by li on 2016/6/29.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.back_to_login)
    ImageView backToLogin;
    @BindView(R.id.accountNum)
    EditText accountNum;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.logup_phone)
    TextView logupPhone;
    @BindView(R.id.forget_pwd)
    TextView forgetPwd;
    @BindView(R.id.btn_login)
    Button login;
    @BindView(R.id.qq)
    TextView qq;
    @BindView(R.id.sina)
    TextView sina;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.videoView)
    MyVideoView mVideoView;
    @BindView(R.id.re_login)
    RelativeLayout re_login;


    private  File videoFile;
    public static final String VIDEO_NAME = "welcome_video.mp4";

    @Override
    protected int initLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.trans));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBar.setVisibility(View.VISIBLE);
            statusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(mContext);
            statusBar.setLayoutParams(statusBar.getLayoutParams());
        } else {
            statusBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void setListener() {

        accountNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().matches(AppUtils.ISPHONENUM)){

                    if(password.getEditableText().toString().matches(AppUtils.ISPWD))
                        login.setEnabled(true);

                }else{
                    login.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().matches(AppUtils.ISPWD)){

                    if(accountNum.getEditableText().toString().matches(AppUtils.ISPHONENUM))
                        login.setEnabled(true);
                }else{
                    login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void initData() {

        try{
//             videoFile = getFileStreamPath(VIDEO_NAME);
//            if (!videoFile.exists()) {
//                videoFile = copyVideoFile();
//            }
            //   playVideo(videoFile);

        }catch (Exception e){
            //如果有异常背景变为黑
//            re_login.setBackgroundColor(Color.BLACK);
        }
        re_login.setBackgroundColor(Color.BLACK);

    }

    /**
     * 播放背景动画
     * @param videoFile
     */
    private void playVideo(File videoFile) {
        mVideoView.setVideoPath(videoFile.getPath());
        mVideoView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        });
    }

    /**
     * 复制文件到/data/data/目录下
     * @return
     */
    @NonNull
    private File copyVideoFile() {
        File videoFile=null;
        try {
            FileOutputStream fos = openFileOutput(VIDEO_NAME, MODE_PRIVATE);
            InputStream in = getResources().openRawResource(R.raw.welcome_video);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = in.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch (FileNotFoundException  e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoFile = getFileStreamPath(VIDEO_NAME);
        if (!videoFile.exists())
            throw new RuntimeException("video file has problem, are you sure you have welcome_video.mp4 in res/raw folder?");
        return videoFile;

    }

    @OnClick({R.id.back_to_login,  R.id.logup_phone, R.id.forget_pwd, R.id.btn_login, R.id.qq, R.id.sina})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.back_to_login:
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.logup_phone:

                break;
            case R.id.forget_pwd:

                new AlertDialog(mContext).builder().setTitle("退出登录")
                        .setMsg("是否退出登录")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RxBus.getDefault().post(new MainEvent(0,""));
                                finish();
                                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();


              //  overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.btn_login:

                User user = new User();
                user.setPhone(accountNum.getEditableText().toString());
                user.setPassword(password.getEditableText().toString());
                user.setUsername(accountNum.getEditableText().toString());
                SPUtils.saveString(mContext, Const.USER_INFO, new Gson().toJson(user), toString());
                SPUtils.saveBoolean(mContext, Const.LOGIN_STATE, true);
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.qq://起吊qq
                break;
            case R.id.sina://起吊微信
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
