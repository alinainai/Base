package com.gas.test.ui.home.mvp

import com.base.lib.di.scope.ActivityScope
import com.base.lib.mvp.BasePresenter

import javax.inject.Inject

@ActivityScope
class HomePresenter @Inject
constructor(model: HomeContract.Model, rootView: HomeContract.View) : BasePresenter<HomeContract.Model, HomeContract.View>(model, rootView) {


    override fun onDestroy() {

    }


}
