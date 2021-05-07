package com.gas.zhihu.ui.fragment.mine

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.gas.zhihu.R
import com.gas.zhihu.ui.fragment.mine.di.DaggerMineComponent
import com.gas.zhihu.ui.fragment.mine.mvp.MineContract
import com.gas.zhihu.ui.fragment.mine.mvp.MinePresenter


class MineFragment : BaseFragment<MinePresenter?>(), MineContract.View {
    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMineComponent.builder().appComponent(appComponent)!!.view(this)!!.build()!!.inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.zhihu_fragment_mine, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {}
    override fun setData(data: Any?) {}
    override val wrapContext: Context
        get() = mContext

    override fun showMessage(message: String) {}

    companion object {
        const val TAG = "MineFragment"
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }
}