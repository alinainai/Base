package com.gas.app.ui.activity.main.mvp

import com.base.lib.di.scope.ActivityScope
import com.base.lib.mvp.BasePresenter

import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 07/22/2020 20:05
 * ================================================
 */

@ActivityScope
class MainPresenter
@Inject
constructor(model: MainContract.Model, rootView: MainContract.View) : BasePresenter<MainContract.Model, MainContract.View>(model, rootView) {

    override fun onDestroy() {
        super.onDestroy();
    }
}
