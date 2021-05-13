package com.gas.beauty.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.lib.util.Preconditions
import com.gas.beauty.R
import com.gas.beauty.ui.article.ArticleFragment.Companion.newInstance
import com.gas.beauty.ui.beauty.mvvm.BeautyFragment
import com.gas.beauty.ui.home.di.DaggerHomeComponent
import com.gas.beauty.ui.home.mvp.HomeContract
import com.gas.beauty.ui.home.mvp.HomePresenter
import com.lib.commonsdk.constants.RouterHub

@Route(path = RouterHub.GANK_HOMEACTIVITY)
class HomeActivity : BaseActivity<HomePresenter?>(), HomeContract.View {
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerHomeComponent.builder()
                .appComponent(appComponent)!!
                .view(this)!!
                .build()!!
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.gank_activity_home
    }

    override fun initData(savedInstanceState: Bundle?) {
        val fragment: Fragment = BeautyFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commitNow()
        fragment.userVisibleHint = true
    }

    override val activity: Activity
        get() = this

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
}