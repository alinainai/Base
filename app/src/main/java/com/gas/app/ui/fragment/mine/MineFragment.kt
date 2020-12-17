package com.gas.app.ui.fragment.mine

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.LazyLoadFragment
import com.base.lib.di.component.AppComponent
import com.gas.app.R
import com.gas.app.ui.fragment.mine.di.DaggerMineComponent
import com.gas.app.ui.fragment.mine.mvp.MineContract
import com.gas.app.ui.fragment.mine.mvp.MinePresenter
import com.gas.app.utils.RemoteActivity
import com.gas.app.utils.getProcessName
import com.lib.commonsdk.extension.app.debug
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Author:
 * Time: 2016/8/12 14:28
 */
class MineFragment : LazyLoadFragment<MinePresenter>(), MineContract.View {




    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMineComponent.builder().appComponent(appComponent).view(this).build().inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        btnModule1.setOnClickListener {
            debug(getProcessName(activity!!.application, Process.myPid()))
        }
        btnModule2.setOnClickListener {
            startActivity(Intent(activity,RemoteActivity::class.java))
        }

    }

    override fun setData(data: Any?) {}


    override fun lazyLoadData() {}
    override val wrapContext: Context
        get() = context!!

    override fun showMessage(message: String) {}

    companion object {
        const val TAG = "MineFragment"

        @JvmStatic
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }
}