package com.lib.commonsdk.mvvm.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.lib.commonsdk.mvvm.BaseViewModel

abstract class BaseVMFragment<VM : BaseViewModel> :BaseFragment<IPresenter>(){

    lateinit var mViewModel: VM

    abstract fun layoutId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel =ViewModelProvider(this).get(getVmClazz(this))
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
    }

    override fun setData(data: Any?) {
    }
}