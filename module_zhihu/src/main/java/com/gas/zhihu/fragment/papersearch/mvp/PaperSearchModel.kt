package com.gas.zhihu.fragment.papersearch.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import com.gas.zhihu.app.MapConstants
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.bean.PaperShowBean
import com.gas.zhihu.bean.paperToShow
import com.gas.zhihu.utils.MapBeanDbUtils
import com.gas.zhihu.utils.PagerBeanDbUtils
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 06/20/2020 17:46
 * ================================================
 */
@FragmentScope
class PaperSearchModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), PaperSearchContract.Model {

    private var mType: Int = MapConstants.PAPER_TYPE_DEFAULT
    private val originMapBeanMap = mutableMapOf<String, MapBean>()

    override fun setType(type: Int) {
        mType = type
    }

    override fun getPagersByFilter(paperName: String): List<PaperShowBean> {

        if(paperName.isBlank()){
            return emptyList()
        }
        return PagerBeanDbUtils.queryAllPaperDataByTypeAndStr(mType, paperName).map { bean ->
            PaperShowBean().apply {
                val map = getMapInfo(bean.mapKey)
                map?.let { paperToShow(bean, map) }
            }
        }
    }

    private fun getMapInfo(key: String?): MapBean? {
        if (key.isNullOrBlank())
            return null
        var map = originMapBeanMap[key]
        if (map == null) {
            map = MapBeanDbUtils.queryData(key)
            map?.let {
                originMapBeanMap.put(key, it)
            }
        }
        return map

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
