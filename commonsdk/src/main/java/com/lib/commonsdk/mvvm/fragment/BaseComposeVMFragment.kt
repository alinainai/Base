package com.lib.commonsdk.mvvm.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.lib.commonsdk.mvvm.BaseViewModel
import com.lib.commonsdk.mvvm.getVmClazz

abstract class BaseVMCPFragment<VM : BaseViewModel> : BaseVMFragment<VM>() {

    //fake
    override fun layoutId() = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(getVmClazz(this))
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
    }

    override fun setData(data: Any?) {
    }
}