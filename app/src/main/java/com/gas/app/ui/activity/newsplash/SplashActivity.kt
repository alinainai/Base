package com.gas.app.ui.activity.newsplash

import android.content.Intent
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.Nullable


import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils

import com.gas.app.ui.activity.newsplash.di.DaggerSplashComponent
import com.gas.app.ui.activity.newsplash.di.SplashModule
import com.gas.app.ui.activity.newsplash.mvp.SplashContract
import com.gas.app.ui.activity.newsplash.mvp.SplashPresenter

import com.gas.app.R


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 07/22/2020 20:29
 * ================================================
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class SplashActivity : BaseActivity<SplashPresenter>(), SplashContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSplashComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .splashModule(SplashModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        return R.layout.activity_splash_new

    }


    override fun initData(savedInstanceState: Bundle?) {

    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }
}
