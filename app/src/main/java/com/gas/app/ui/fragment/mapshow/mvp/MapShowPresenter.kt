package com.gas.app.ui.fragment.mapshow.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter

import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/31/2020 15:45
 * ================================================
 */

@FragmentScope
class MapShowPresenter
@Inject
constructor(model: MapShowContract.Model, rootView: MapShowContract.View) :
        BasePresenter<MapShowContract.Model, MapShowContract.View>(model, rootView) {


    init {

    }


}
