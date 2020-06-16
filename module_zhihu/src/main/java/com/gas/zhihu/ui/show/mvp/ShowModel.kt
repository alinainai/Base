package com.gas.zhihu.ui.show.mvp

import com.base.lib.di.scope.ActivityScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.utils.MapBeanDbUtils
import javax.inject.Inject

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpTemplate on 03/28/2020 21:18
 * ================================================
 */
@ActivityScope
class ShowModel @Inject constructor(repositoryManager: IRepositoryManager?) : BaseModel(repositoryManager), ShowContract.Model {
    override fun onDestroy() {
        super.onDestroy()
    }

    override fun getMapInfo(key: String?): MapBean? {
        return MapBeanDbUtils.queryData(key)
    }

    override val defaultMapInfo: MapBean
        get() = MapBean()
}