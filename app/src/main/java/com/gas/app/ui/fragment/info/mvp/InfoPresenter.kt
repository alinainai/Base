package com.gas.app.ui.fragment.info.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import javax.inject.Inject

@FragmentScope
class InfoPresenter @Inject constructor(model: InfoContract.Model, rootView: InfoContract.View) : BasePresenter<InfoContract.Model, InfoContract.View>(model, rootView) {
    override fun onDestroy() {
        super.onDestroy()
    }
}