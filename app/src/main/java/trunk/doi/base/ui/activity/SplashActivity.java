package trunk.doi.base.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.base.lib.base.BaseActivity;
import com.base.lib.view.StatusBarHeight;
import com.base.lib.view.TitleView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseApplication;
import trunk.doi.base.constant.Constant;
import trunk.doi.base.ui.MainActivity;
import trunk.doi.base.util.AppUtils;
import trunk.doi.base.util.ToastUtil;

public class SplashActivity extends BaseActivity {


    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_skip)
    TextView tv_skip;

    private Disposable mDisposable;


    @Override
    protected int initLayoutId(StatusBarHeight statusBar, TitleView titleView) {
        statusBar.setVisibility(View.GONE);
        titleView.setVisibility(View.GONE);
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        tv_version.setText(String.format("当前版本 v%s", AppUtils.getVersionName(BaseApplication.getInstance())));
//        String[] pers = {android.Manifest.permission.READ_PHONE_STATE,
//                android.Manifest.permission.CAMERA,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE,
//        };
//        PermissionUtils.requestAppPermissions(SplashActivity.this, Constant.CODE_REQUEST_PERMISSION, pers);


    }

    @Override
    protected void initData() {

        mDisposable = Flowable.intervalRange(0, 4, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> {
                            if (aLong != 3) {
                                tv_skip.setText(String.format(Locale.CHINA, "跳过 %d", 3 - aLong));
                            }
                        }
                )
                .doOnComplete(this::turnToMain)
                .subscribe();

    }

    /**
     * turn to MainActivity
     */
    private void turnToMain() {

        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(0, android.R.anim.fade_out);

    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mDisposable=null;
        super.onDestroy();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.CODE_REQUEST_PERMISSION && grantResults.length > 0) {
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
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mDisposable=null;
        turnToMain();
    }
}
