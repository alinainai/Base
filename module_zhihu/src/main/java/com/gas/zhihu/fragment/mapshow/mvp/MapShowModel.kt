package com.gas.zhihu.fragment.mapshow.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.bean.PaperBean
import com.gas.zhihu.fragment.mapshow.bean.CharSortBean
import com.gas.zhihu.fragment.mapshow.bean.ISortBean
import com.gas.zhihu.fragment.mapshow.bean.MapShowBean
import com.gas.zhihu.utils.MapBeanDbUtils
import com.gas.zhihu.utils.PagerBeanDbUtils
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/31/2020 15:45
 * ================================================
 */
@FragmentScope
class MapShowModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MapShowContract.Model {

    private val originList = mutableListOf<PaperBean>()
    private val originMapBeanMap = mutableMapOf<String, MapBean>()
    private val sortMapList = mutableListOf<MapShowBean>()
    private var mType: Int = 0
    private  val sortCharList = mutableListOf<CharSortBean>()

    override fun setType(type: Int) {
        mType = type
    }

    private fun getPapers(): List<PaperBean> {
        if (originList.isNotEmpty()) {
            return originList
        }
        originList.addAll(PagerBeanDbUtils.queryAllPaperDataByType(mType))
        return originList
    }

    override fun getValidMapList(): List<MapBean> {

        if (originMapBeanMap.isNotEmpty()) {
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

    override fun resetOriginData() {
        originList.clear()
        originMapBeanMap.clear()
    }

    override fun getMapsByFilter(filter: String?): List<ISortBean> {

        val list = mutableListOf<ISortBean>()

        if (sortMapList.isEmpty()) {
            val ori = getValidMapList()
            ori.filterNot { it.mapName.isNullOrBlank() }
                    .forEach {
                        val bean = MapShowBean(it)
                        sortMapList.add(bean)
                    }
        }
        sortCharList.clear()
        var temp=""
        sortMapList
                .filter {
                    if (filter.isNullOrBlank()) true
                    else it.mapNamePinyin.contains(filter, true) || it.mapNameSpell.contains(filter, true)
                }.sortedBy { it.mapNameSpell }.forEach {
                    val tag: String = it.showChar
                    if (temp != tag) {
                        val charSort=CharSortBean(tag)
                        sortCharList.add(charSort)
                        list.add(charSort)
                        temp = tag
                    }
                    list.add(it)
                }

        return list
    }



    override fun onDestroy() {
        super.onDestroy()
    }
}
