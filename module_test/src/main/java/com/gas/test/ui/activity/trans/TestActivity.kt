package com.gas.test.ui.activity.trans

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.baseui.ui.base.FragmentContainerActivity
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.test.R
import com.gas.test.utils.fragment.asynclist.AsyncListFragment
import com.lib.commonsdk.constants.RouterHub
import com.lib.commonsdk.kotlin.extension.app.debug
import kotlinx.android.synthetic.main.activity_test.*

@Route(path = RouterHub.TEST_HOMEACTIVITY)
class TestActivity : BaseActivity<IPresenter>() {
    override fun setupActivityComponent(appComponent: AppComponent) {
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_test
    }

    override fun initData(savedInstanceState: Bundle?) {

        btnModule1.setOnClickListener {
            FragmentContainerActivity.startActivity(this, AsyncListFragment::class.java)
        }
        btnModule2.setOnClickListener {

            val list1 = mutableListOf<String>("11","12","13","14")
            val list2 = mutableListOf<String>("11","12","13","14")
            debug("btnModule2=${list1 == list2}")

        }
        btnModule3.setOnClickListener {



        }
        btnPlugin1.setOnClickListener {
            val list1 = mutableListOf<String>("11","12","13","14")
            val list2 = mutableListOf<String>("11","13","12","14")
            debug("btnPlugin1=${list1 == list2}")
        }
        btnPlugin2.setOnClickListener {
            val list1 = mutableListOf<String>("11","12","13")
            val list2 = mutableListOf<String>("11","13","12","14")
            debug("btnPlugin2=${list1 == list2}")
        }
        btnPlugin3.setOnClickListener {
            val list1 = mutableListOf<String>()
            val list2 = mutableListOf<String>()
            debug("btnPlugin3=${list1 == list2}")
        }
    }
}