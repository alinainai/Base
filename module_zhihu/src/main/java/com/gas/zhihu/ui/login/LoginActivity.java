package com.gas.zhihu.ui.login;

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
import com.gas.zhihu.app.ZhihuConstants;
import com.gas.zhihu.dialog.TipShowDialog;
import com.gas.zhihu.ui.base.FragmentContainerActivity;
import com.gas.zhihu.ui.main.MainActivity;
import com.lib.commonsdk.constants.RouterHub;
import com.lib.commonsdk.utils.FastClickUtils;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.gas.zhihu.R;
import com.gas.zhihu.R2;
import com.gas.zhihu.ui.login.di.DaggerLoginComponent;
import com.gas.zhihu.ui.login.mvp.LoginContract;
import com.gas.zhihu.ui.login.mvp.LoginPresenter;
import com.gas.zhihu.ui.map.MapActivity;
import com.gas.zhihu.view.CleanEditText;
import com.lib.commonsdk.utils.AppUtils;

import butterknife.BindView;
import butterknife.OnClick;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

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
//    private TextWatcher mTextWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            //登录不做限制只要输入就提交
//            if (etPhoneNum.getNoSpaceText().length() > 1 && etUserpsw.getNoSpaceText().length() > 1) {
//                mBtnGoLogin.setEnabled(true);
//            } else {
//                mBtnGoLogin.setEnabled(false);
//            }
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//        }
//    };

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

//        etPhoneNum.addTextChangedListener(mTextWatcher);
//        etUserpsw.addTextChangedListener(mTextWatcher);
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
            showForgetTip();

        }
    }
    private void showForgetTip() {
      new TipShowDialog().show(this, "提示", "登录密码提示", () -> null,true);
    }

    private void login() {

        String userName = etPhoneNum.getNoSpaceText();
        String passWord = etUserpsw.getNoSpaceText();


        if (TextUtils.isEmpty(userName)) {
            AppUtils.toast("请输入登录账号");
            return;
        }

        if (TextUtils.isEmpty(passWord)) {
            AppUtils.toast("请输入登录密码");
            return;
        }

        if (userName.equals(ZhihuConstants.ZHIHU_USER_NAME) && passWord.equals(ZhihuConstants.ZHIHU_PASSWORD)) {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        } else {
            AppUtils.toast("请输入正确的用户名和密码");
        }

    }
}
