package com.gas.test.ui.activity.trans

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.baseui.ui.base.FragmentContainerActivity
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.test.R
import com.gas.test.learn.lesson001_lifecycle.FirstActivity
import com.gas.test.utils.RegionBusiness
import com.gas.test.utils.fragment.customview.CustomViewFragment
import com.lib.commonsdk.constants.RouterHub
import com.lib.commonsdk.extension.app.*
import kotlinx.android.synthetic.main.activity_test.*


@Route(path = RouterHub.TEST_HOMEACTIVITY)
class TestActivity : BaseActivity<IPresenter>() {


//    private val vm by lazy {
//        ViewModelProvider(this).get(LoginVm::class.java)
//    }

    private val region = RegionBusiness.ins

    override fun setupActivityComponent(appComponent: AppComponent) {
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_test
    }


    override fun initData(savedInstanceState: Bundle?) {

        region.log()

//        vm.loginBean.observe(this, object : Observer<LoginBean> {
//            override fun onChanged(t: LoginBean) {
//                /*数据发生变化*/
//                toast(t.message)
//            }
//        })


        btnModule1.setOnClickListener {
//            vm.account = "111"
//            vm.pwd = "123"
//            vm.doLogin()
            startActivity(Intent(this, FirstActivity::class.java))
        }
        btnModule2.setOnClickListener {
            debug("btnModule2")
            FragmentContainerActivity.startActivity(this, CustomViewFragment::class.java)
//            debug(getWiFiSsid())
        }
        btnModule3.setOnClickListener {

            list2Map()
        }
        btnPlugin1.setOnClickListener {

        }
        btnPlugin2.setOnClickListener {

        }
        btnPlugin3.setOnClickListener {

        }
    }

    fun list2Map() {
        val colors = listOf(Color("red",1),
                Color("green",2),
                Color("blue",3))
        val map =  colors.map { it.name to it }
        debug(map)
    }

    data class Color(val name:String, val value:Int)

}