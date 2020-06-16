package com.gas.zhihu.ui.office.mvp

import com.base.lib.di.scope.ActivityScope
import com.base.lib.mvp.BasePresenter

import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 05/17/2020 10:42
 * ================================================
 */

@ActivityScope
class OfficePresenter
@Inject
constructor(model: OfficeContract.Model, rootView: OfficeContract.View) :
        BasePresenter<OfficeContract.Model, OfficeContract.View>(model, rootView) {



    override fun onDestroy() {
        super.onDestroy();
    }
}
