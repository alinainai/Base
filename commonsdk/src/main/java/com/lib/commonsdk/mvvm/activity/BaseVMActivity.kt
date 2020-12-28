package com.lib.commonsdk.mvvm.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.lib.commonsdk.mvvm.BaseViewModel
import com.lib.commonsdk.mvvm.fragment.getVmClazz

abstract class BaseVMActivity<VM : BaseViewModel>:BaseActivity<IPresenter>() {

    lateinit var mViewModel: VM

    abstract fun layoutId():Int
    abstract fun init()

    override fun setupActivityComponent(appComponent: AppComponent) {
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return layoutId()
    }

    override fun initData(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProvider(this).get(getVmClazz(this))
        init()
    }
}