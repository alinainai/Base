package com.gas.zhihu.fragment.addpaper.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import com.gas.zhihu.bean.MapSelectShowBean
import com.gas.zhihu.bean.VoltageLevelBean

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


    fun showMapDialog(){
        mModel.getMapList().apply {
            val maps = mutableListOf<MapSelectShowBean>()
            forEach {
                maps.add(MapSelectShowBean(it))
            }
            mView.showMapSelectDialog(maps)
        }
    }

    fun showVoltageDialog(){
       mView.showVolSelectDialog(mModel.getVoltageList())
    }

    override fun onDestroy() {
        super.onDestroy();
    }
}
