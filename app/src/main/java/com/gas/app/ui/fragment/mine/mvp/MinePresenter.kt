package com.gas.app.ui.fragment.mine.mvp

import android.app.Application
import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import javax.inject.Inject

@FragmentScope
class MinePresenter @Inject constructor(model: MineModle, rootView: MineContract.View) : BasePresenter<MineModle, MineContract.View>(model, rootView) {
    @Inject
    lateinit var mApplication: Application
    override fun onDestroy() {
        super.onDestroy()
    }
}