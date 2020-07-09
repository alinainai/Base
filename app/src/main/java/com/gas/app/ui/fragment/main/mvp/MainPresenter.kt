package com.gas.app.ui.fragment.main.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import javax.inject.Inject

@FragmentScope
class MainPresenter @Inject constructor(model: MainContract.Model, rootView: MainContract.View) : BasePresenter<MainContract.Model, MainContract.View>(model, rootView) {
    override fun onDestroy() {
        super.onDestroy()
    }
}