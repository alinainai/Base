package com.gas.app.test.fragment.target30adapt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.BasePresenter
import com.base.lib.mvp.IModel
import com.base.lib.mvp.IView
import com.gas.app.R


class FileStoragePermissionAdaptFragment : BaseFragment<BasePresenter<IModel, IView>>() {
    override fun setupFragmentComponent(appComponent: AppComponent) {

    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {}

    override fun setData(data: Any?) {}

    companion object {
        @JvmStatic
        fun newInstance(): FileStoragePermissionAdaptFragment {
            return FileStoragePermissionAdaptFragment()
        }
    }
}