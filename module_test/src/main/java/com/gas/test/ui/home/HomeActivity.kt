package com.gas.test.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button

import com.alibaba.android.arouter.facade.annotation.Route
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.gas.test.R
import com.gas.test.ui.home.di.DaggerHomeComponent
import com.gas.test.ui.home.mvp.HomeContract
import com.gas.test.ui.home.mvp.HomePresenter
import com.gas.test.ui.kotlin.KotlinActivity
import com.gas.test.ui.test.RatioViewActivity
import com.lib.commonsdk.constants.RouterHub

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

@Route(path = RouterHub.TEST_HOMEACTIVITY)
class HomeActivity : BaseActivity<HomePresenter>(), HomeContract.View {


//    @BindView(R.id.test_btn_ratio_view)
//    internal var testBtnRatioView: Button? = null
//    @BindView(R.id.test_btn_two)
//    internal var testBtnTwo: Button? = null

    override val activity: Activity
        get() = mContext

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this)
    }

    override fun initLayoutId(): Int {
        return R.layout.test_activity_home
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun success() {

    }

    override fun onError() {

    }


//    @OnClick(R.id.test_btn_ratio_view, R.id.test_btn_two)
//    fun onViewClicked(view: View) {
//        when (view.id) {
//            R.id.test_btn_ratio_view -> startActivity(Intent(mContext, RatioViewActivity::class.java))
//            R.id.test_btn_two -> startActivity(Intent(mContext, KotlinActivity::class.java))
//        }
//    }
}
