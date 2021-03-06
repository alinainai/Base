package com.gas.zhihu.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.baseui.ui.base.FragmentContainerActivity;
import com.gas.zhihu.app.ZhihuConstants;

import com.gas.zhihu.ui.fragment.mine.MineFragment;
import com.lib.commonsdk.constants.RouterHub;
import com.lib.commonsdk.extension.app.AppExtKt;
import com.lib.commonsdk.utils.FastClickUtils;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.gas.zhihu.R;
import com.gas.zhihu.R2;
import com.gas.zhihu.ui.activity.login.di.DaggerLoginComponent;
import com.gas.zhihu.ui.activity.login.mvp.LoginContract;
import com.gas.zhihu.ui.activity.login.mvp.LoginPresenter;
import com.gas.zhihu.view.CleanEditText;

import butterknife.BindView;
import butterknife.OnClick;

import static com.base.lib.util.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/24/2020 20:50
 * ================================================
 */
@Route(path = RouterHub.ZHIHU_HOMEACTIVITY)
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R2.id.et_phone_num)
    CleanEditText etPhoneNum;
    @BindView(R2.id.divider_line)
    View dividerLine;
    @BindView(R2.id.et_userpsw)
    CleanEditText etUserpsw;
    @BindView(R2.id.iv_eye)
    ImageView ivEye;

    @BindView(R2.id.divider)
    View divider;

    @BindView(R2.id.btn_go_login)
    Button mBtnGoLogin;

    private boolean mIsPwsVisible;
    private Context mContext;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        mContext = this;
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        return R.layout.zhihu_activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        etUserpsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etPhoneNum.setOnFocusChangeListener((v, hasFocus) -> {
            if (dividerLine != null) {
                dividerLine.setBackgroundColor(ContextCompat.getColor(mContext, hasFocus ? R.color.zhihu_c6293f9 : R.color.zhihu_ce1e1e1));
            }
        });
        etUserpsw.setOnFocusChangeListener((v, hasFocus) -> {
            if (divider != null) {
                divider.setBackgroundColor(ContextCompat.getColor(mContext, hasFocus ? R.color.zhihu_c6293f9 : R.color.zhihu_ce1e1e1));
            }
        });
    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }



    @OnClick({R2.id.iv_eye, R2.id.btn_go_login, R2.id.tv_login_other})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.iv_eye) {

            if (mIsPwsVisible) {
                mIsPwsVisible = false;
                ivEye.setImageResource(R.mipmap.zhihu_open_icon);
                etUserpsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                mIsPwsVisible = true;
                ivEye.setImageResource(R.mipmap.zhihu_password_icon);
                etUserpsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            etUserpsw.setSelection(etUserpsw.getNoSpaceText().length());

        } else if (id == R.id.btn_go_login) {

            if (FastClickUtils.isFastClick()) return;
            login();

        } else if (id == R.id.tv_login_other) {

        }
    }

    private void login() {

        String userName = etPhoneNum.getNoSpaceText();
        String passWord = etUserpsw.getNoSpaceText();


        if (TextUtils.isEmpty(userName)) {
            AppExtKt.toast("请输入登录账号");
            return;
        }

        if (TextUtils.isEmpty(passWord)) {
            AppExtKt.toast("请输入登录密码");
            return;
        }

        if (userName.equals(ZhihuConstants.ZHIHU_USER_NAME) && passWord.equals(ZhihuConstants.ZHIHU_PASSWORD)) {

            FragmentContainerActivity.Companion.startActivity(this, MineFragment.class);
        } else {
            AppExtKt.toast("请输入正确的用户名和密码");
        }

    }
}
