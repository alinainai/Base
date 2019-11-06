package trunk.doi.base.ui.activity.splash;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.constant.Constant;
import trunk.doi.base.ui.MainActivity;
import trunk.doi.base.ui.activity.splash.di.DaggerSplashComponent;
import trunk.doi.base.ui.activity.splash.mvp.SplashContract;
import trunk.doi.base.ui.activity.splash.mvp.SplashPresenter;
import trunk.doi.base.util.GasUtils;
import trunk.doi.base.util.ToastUtil;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {


    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_skip)
    TextView tv_skip;

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
    public int initLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mPresenter.showVersionCode();
    }

    @Override
    public void initData() {
        mPresenter.autoTimeDown();
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
        mPresenter.turnToMainForce();
    }

    @Override
    public boolean needTitle() {
        return false;
    }

    @Override
    public boolean needStatusBar() {
        return false;
    }

    @Override
    public void goMainActivity() {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
        overridePendingTransition(android.R.anim.fade_in, 0);
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
    public void onError() {

    }
}
