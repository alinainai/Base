package com.lib.commonsdk.mvvm.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.lib.commonsdk.mvvm.BaseViewModel

abstract class BaseVMDBFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVMFragment<VM>() {

    //该类绑定的ViewDataBinding
    lateinit var mDataBind: DB

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mDataBind = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        mDataBind.lifecycleOwner = this
        return mDataBind.root
    }

}