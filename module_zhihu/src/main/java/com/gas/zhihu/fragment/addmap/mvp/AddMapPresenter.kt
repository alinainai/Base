package com.gas.zhihu.fragment.addmap.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter

import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/21/2020 11:44
 * ================================================
 */

@FragmentScope
class AddMapPresenter
@Inject
constructor(model: AddMapContract.Model, rootView: AddMapContract.View) :
        BasePresenter<AddMapContract.Model, AddMapContract.View>(model, rootView) {


    override fun onDestroy() {
        super.onDestroy();
    }
}
