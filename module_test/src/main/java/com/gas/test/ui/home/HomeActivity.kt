package com.gas.test.ui.home

import android.content.Context
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.gas.test.R
import com.gas.test.ui.home.di.DaggerHomeComponent
import com.gas.test.ui.home.mvp.HomeContract
import com.gas.test.ui.home.mvp.HomePresenter
import com.lib.commonsdk.constants.RouterHub

@Route(path = RouterHub.TEST_HOMEACTIVITY)
class HomeActivity : BaseActivity<HomePresenter>(), HomeContract.View {


//    @BindView(R.id.test_btn_ratio_view)
//    internal var testBtnRatioView: Button? = null
//    @BindView(R.id.test_btn_two)
//    internal var testBtnTwo: Button? = null


    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.test_activity_home
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getWrapContext(): Context {
        return this
    }


    override fun success() {

    }

    override fun showMessage(message: String) {
    }




//    @OnClick(R.id.test_btn_ratio_view, R.id.test_btn_two)
//    fun onViewClicked(view: View) {
//        when (view.id) {
//            R.id.test_btn_ratio_view -> startActivity(Intent(mContext, RatioViewActivity::class.java))
//            R.id.test_btn_two -> startActivity(Intent(mContext, KotlinActivity::class.java))
//        }
//    }
}
