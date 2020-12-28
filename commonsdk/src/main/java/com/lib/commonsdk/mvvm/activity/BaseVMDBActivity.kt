package com.lib.commonsdk.mvvm.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lib.commonsdk.mvvm.BaseViewModel

abstract class BaseVMDBActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVMActivity<VM>() {

    lateinit var mDataBind: DB
    abstract fun bindId(): Int
    abstract fun initAfterBind(): Int
    override fun layoutId(): Int {
        return 0
    }

    override fun init() {
        mDataBind = DataBindingUtil.setContentView(this, bindId())
        mDataBind.lifecycleOwner = this
        initAfterBind()
    }


}