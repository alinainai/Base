package com.gas.beauty.ui.home.mvp

import com.base.lib.di.scope.ActivityScope
import com.base.lib.mvp.BasePresenter
import com.base.lib.mvp.IModel
import javax.inject.Inject

@ActivityScope
class HomePresenter @Inject constructor(rootView: HomeContract.View?) : BasePresenter<IModel?, HomeContract.View?>(rootView) {
    override fun onDestroy() {}
}