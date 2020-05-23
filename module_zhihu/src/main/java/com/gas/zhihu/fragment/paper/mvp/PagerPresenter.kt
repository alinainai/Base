package com.gas.zhihu.fragment.paper.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import com.gas.zhihu.bean.MapBean

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


    fun initOriginData(type:Int){
        mModel.setType(type)
        mModel.getValidMapList()
    }

    fun getFilterData( voltage: String, mapKey: String){
        mView.setPaperData(mModel.getPagersByFilter(voltage,mapKey))
    }

    fun getValidMaps():List<MapBean>{
        return mModel.getValidMapList()
    }

    override fun onDestroy() {
        super.onDestroy();
    }
}
