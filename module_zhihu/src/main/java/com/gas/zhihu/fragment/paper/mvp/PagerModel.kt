package com.gas.zhihu.fragment.paper.mvp

import android.util.Log
import com.base.lib.di.scope.FragmentScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import com.gas.zhihu.app.ZhihuConstants
import com.gas.zhihu.app.ZhihuConstants.DEFAULT_TYPE
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.bean.PaperBean
import com.gas.zhihu.bean.PaperShowBean
import com.gas.zhihu.bean.paperToShow
import com.gas.zhihu.utils.MapBeanDbUtils
import com.gas.zhihu.utils.PagerBeanDbUtils
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/17/2020 10:01
 * ================================================
 */
@FragmentScope
class PagerModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), PagerContract.Model {

    private val originList = mutableListOf<PaperBean>()
    private val originMapBeanMap = mutableMapOf<String,MapBean>()
    private var mType: Int = 0

    override fun setType(type:Int){
        mType=type
    }
    private fun getPapers(): List<PaperBean>{
        if (originList.isNotEmpty()) {
            return originList
        }
        originList.addAll(PagerBeanDbUtils.queryAllPaperDataByType(mType))
        return originList
    }

    override fun getValidMapList(): List<MapBean> {

        if(originMapBeanMap.isNotEmpty()){
            return originMapBeanMap.values.toList()
        }

        val map = mutableSetOf<String>()
        getPapers().forEach {
            map.add(it.mapKey)
        }
        map.takeIf { map.isNotEmpty() }?.forEach {
            MapBeanDbUtils.queryData(it)?.apply {
                originMapBeanMap[this.keyName] = this
            }
        }
        return originMapBeanMap.values.toList()
    }

    override fun resetOriginData(){
        originList.clear()
        originMapBeanMap.clear()
    }

    override fun getPagersByFilter(voltage: String, mapKey: String): List<PaperShowBean> {
        if (getPapers().isEmpty())
            return emptyList()
        val ori = getPapers()
        val handleList = mutableListOf<PaperShowBean>()
        ori.filter {
            if( voltage != DEFAULT_TYPE) it.voltageLevel==voltage.toInt() else true
        }.filter {
            if( mapKey != DEFAULT_TYPE) it.mapKey==mapKey else true
        }.filterNot { it.mapKey.isBlank() }.
        forEach {
           val bean= PaperShowBean()
            bean.paperToShow(it,originMapBeanMap[it.mapKey])
            handleList.add(bean)
        }
        return handleList
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
