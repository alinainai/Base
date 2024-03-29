package com.gas.test.ui.activity.trans

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.baseui.ui.base.FragmentContainerActivity
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.test.R
import com.gas.test.learn.lesson001_lifecycle.MainActivity
import com.gas.test.learn.lesson002_lauchmode.AStandardActivity
import com.gas.test.ui.activity.dialog.BottomSheetDialogFragment
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
            startActivity(Intent(this, MainActivity::class.java))
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
            startActivity(Intent(this, AStandardActivity::class.java))
        }
    }

    private fun showBatteryOptimizeTimeDialog(ctx: Context, selectKey: String, action: (item: BottomSheetDialogFragment.DefaultItemSelect) -> Unit) {
        val items = mutableListOf(
                BottomSheetDialogFragment.DefaultItemSelect("10$", "10"),
                BottomSheetDialogFragment.DefaultItemSelect("15$111", "15"),
                BottomSheetDialogFragment.DefaultItemSelect("20$111", "20"),
                BottomSheetDialogFragment.DefaultItemSelect("30$111", "30"),
                BottomSheetDialogFragment.DefaultItemSelect("60$111", "60")
        )
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