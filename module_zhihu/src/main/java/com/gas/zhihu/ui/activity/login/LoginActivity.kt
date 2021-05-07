package com.gas.zhihu.ui.activity.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.baseui.ui.base.FragmentContainerActivity.Companion.startActivity
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.lib.util.Preconditions
import com.gas.zhihu.R
import com.gas.zhihu.R2
import com.gas.zhihu.app.ZhihuConstants
import com.gas.zhihu.ui.activity.login.di.DaggerLoginComponent
import com.gas.zhihu.ui.activity.login.mvp.LoginContract
import com.gas.zhihu.ui.activity.login.mvp.LoginPresenter
import com.gas.zhihu.ui.fragment.mine.MineFragment
import com.gas.zhihu.view.CleanEditText
import com.lib.commonsdk.constants.RouterHub
import com.lib.commonsdk.extension.app.toast
import com.lib.commonsdk.kotlin.FastClickCheck.isFastClick

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpTemplate on 03/24/2020 20:50
 * ================================================
 */
@Route(path = RouterHub.ZHIHU_HOMEACTIVITY)
class LoginActivity : BaseActivity<LoginPresenter?>(), LoginContract.View {
    @BindView(R2.id.et_phone_num)
    lateinit var etPhoneNum: CleanEditText

    @BindView(R2.id.divider_line)
    lateinit var dividerLine: View

    @BindView(R2.id.et_userpsw)
    lateinit  var etUserpsw: CleanEditText

    @BindView(R2.id.iv_eye)
    lateinit var ivEye: ImageView

    @BindView(R2.id.divider)
    lateinit var divider: View

    @BindView(R2.id.btn_go_login)
    lateinit var mBtnGoLogin: Button

    private var mIsPwsVisible = false
    private var mContext: Context? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                ?.view(this)
                ?.build()
                ?.inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        mContext = this
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        return R.layout.zhihu_activity_login
    }

    override fun initData(savedInstanceState: Bundle?) {
        etUserpsw!!.transformationMethod = PasswordTransformationMethod.getInstance()
        etPhoneNum!!.onFocusChangeListener = View.OnFocusChangeListener { v: View?, hasFocus: Boolean ->
            if (dividerLine != null) {
                dividerLine!!.setBackgroundColor(ContextCompat.getColor(mContext!!, if (hasFocus) R.color.zhihu_c6293f9 else R.color.zhihu_ce1e1e1))
            }
        }
        etUserpsw!!.onFocusChangeListener = View.OnFocusChangeListener { v: View?, hasFocus: Boolean ->
            if (divider != null) {
                divider!!.setBackgroundColor(ContextCompat.getColor(mContext!!, if (hasFocus) R.color.zhihu_c6293f9 else R.color.zhihu_ce1e1e1))
            }
        }
    }

    override fun showMessage(message: String) {
        Preconditions.checkNotNull(message)
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        Preconditions.checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }

    @OnClick(R2.id.iv_eye, R2.id.btn_go_login, R2.id.tv_login_other)
    fun onViewClicked(view: View) {
        val id = view.id
        if (id == R.id.iv_eye) {
            if (mIsPwsVisible) {
                mIsPwsVisible = false
                ivEye!!.setImageResource(R.mipmap.zhihu_open_icon)
                etUserpsw!!.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                mIsPwsVisible = true
                ivEye!!.setImageResource(R.mipmap.zhihu_password_icon)
                etUserpsw!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            etUserpsw!!.setSelection(etUserpsw!!.noSpaceText.length)
        } else if (id == R.id.btn_go_login) {
            if (isFastClick) return
            login()
        } else if (id == R.id.tv_login_other) {
        }
    }

    private fun login() {
        val userName = etPhoneNum!!.noSpaceText
        val passWord = etUserpsw!!.noSpaceText
        if (TextUtils.isEmpty(userName)) {
            toast("请输入登录账号")
            return
        }
        if (TextUtils.isEmpty(passWord)) {
            toast("请输入登录密码")
            return
        }
        if (userName == ZhihuConstants.ZHIHU_USER_NAME && passWord == ZhihuConstants.ZHIHU_PASSWORD) {
            startActivity(this, MineFragment::class.java)
        } else {
            toast("请输入正确的用户名和密码")
        }
    }
}