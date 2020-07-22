package com.gas.app.ui.activity.newsplash.mvp

import com.base.lib.di.scope.ActivityScope
import com.base.lib.mvp.BasePresenter

import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 07/22/2020 20:29
 * ================================================
 */

@ActivityScope
class SplashPresenter
@Inject
constructor(model: SplashContract.Model, rootView: SplashContract.View) :
        BasePresenter<SplashContract.Model, SplashContract.View>(model, rootView) {


    override fun onDestroy() {
        super.onDestroy();
    }
}
