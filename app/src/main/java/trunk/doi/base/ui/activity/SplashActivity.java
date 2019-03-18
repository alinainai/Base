package trunk.doi.base.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.base.lib.base.BaseActivity;
import com.base.lib.view.StatusBarHeight;
import com.base.lib.view.TitleView;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseApplication;
import trunk.doi.base.ui.MainActivity;
import trunk.doi.base.util.AppUtils;
import trunk.doi.base.util.PermissionUtils;
import trunk.doi.base.util.ToastUtil;

public class SplashActivity extends BaseActivity {


    @BindView(R.id.tv_version)
    TextView tv_version;


    private static final String ANIM_NAME = "confetti.json";

    @Override
    protected int initLayoutId(StatusBarHeight statusBar, TitleView titleView) {
        statusBar.setVisibility(View.GONE);
        titleView.setVisibility(View.GONE);
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        tv_version.setText(String.format("当前版本 v%s", AppUtils.getVersionName(BaseApplication.getInstance())));
        String[] pers = {android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        PermissionUtils.requestAppPermissions(SplashActivity.this, 1008, pers);


    }

    @Override
    protected void initData() {





    }

    private void turnToMain() {

        mContext.startActivity(new Intent(SplashActivity.this, MainActivity.class));
        mContext.finish();
        mContext.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        handler.removeCallbacks(toMain);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001 && grantResults.length > 0) {
            if (permissions.length > 0 && permissions.length == grantResults.length) {
                for (int grantResult : grantResults) {
                    //do something
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        ToastUtil.show(SplashActivity.this, "请去设置中开启软件读取文件信息的权限，否则软件不能正常使用");
                        break;
                    }
                }
            }
        }

    }


}
