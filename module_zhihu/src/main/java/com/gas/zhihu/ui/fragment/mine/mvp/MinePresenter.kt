package com.gas.zhihu.ui.fragment.mine.mvp

import android.app.Application
import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import javax.inject.Inject

@FragmentScope
class MinePresenter @Inject constructor(model: MineModle?, rootView: MineContract.View?) : BasePresenter<MineModle?, MineContract.View?>(model, rootView) {
    @JvmField
    @Inject
    var mApplication: Application? = null
    override fun onDestroy() {
        super.onDestroy()
    }
}