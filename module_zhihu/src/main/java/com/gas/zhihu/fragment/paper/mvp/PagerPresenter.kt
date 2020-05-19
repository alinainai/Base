package com.gas.zhihu.fragment.paper.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter

import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/17/2020 10:01
 * ================================================
 */

@FragmentScope
class PagerPresenter
@Inject
constructor(model: PagerContract.Model, rootView: PagerContract.View) :
        BasePresenter<PagerContract.Model, PagerContract.View>(model, rootView) {


    override fun onDestroy() {
        super.onDestroy();
    }
}
