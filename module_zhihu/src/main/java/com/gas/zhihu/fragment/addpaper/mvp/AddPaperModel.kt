package com.gas.zhihu.fragment.addpaper.mvp

import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel

import com.base.lib.di.scope.FragmentScope;
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.bean.PaperBean
import com.gas.zhihu.bean.VoltageLevelBean
import javax.inject.Inject

import com.gas.zhihu.fragment.addpaper.mvp.AddPaperContract
import com.gas.zhihu.utils.MapBeanDbUtils
import com.gas.zhihu.utils.PagerBeanDbUtils


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/21/2020 11:45
 * ================================================
 */
@FragmentScope
class AddPaperModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), AddPaperContract.Model {

    override fun onDestroy() {
        super.onDestroy();
    }

    override fun getMapList():List<MapBean>{
        return MapBeanDbUtils.queryAllMapData()
    }

    override fun getVoltageList():List<VoltageLevelBean>{
        return VoltageLevelBean.voltageLevelItems
    }

    override fun insertPaperBean(bean:PaperBean):Boolean{
       return PagerBeanDbUtils.insertMapBean(bean)
    }

}
