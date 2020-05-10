package com.gas.zhihu.fragment.option.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import com.base.lib.mvp.IModel

import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/10/2020 22:52
 * ================================================
 */

@FragmentScope
class OptionPresenter
@Inject
constructor(rootView: OptionContract.View) :
        BasePresenter<IModel, OptionContract.View>(rootView) {


    override fun onDestroy() {
        super.onDestroy();
    }
}
