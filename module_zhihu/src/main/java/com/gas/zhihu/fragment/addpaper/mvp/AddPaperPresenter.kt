package com.gas.zhihu.fragment.addpaper.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter

import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/21/2020 11:45
 * ================================================
 */

@FragmentScope
class AddPaperPresenter
@Inject
constructor(model: AddPaperContract.Model, rootView: AddPaperContract.View) :
        BasePresenter<AddPaperContract.Model, AddPaperContract.View>(model, rootView) {


    override fun onDestroy() {
        super.onDestroy();
    }
}
