package com.gas.zhihu.fragment.mapshow.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import com.gas.zhihu.app.MapConstants.Companion.PAPER_TYPE_DEFAULT
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

    private val originMapBeanMap = mutableListOf<MapBean>()
    private var mType: Int = 0
    private val sortCharList = mutableListOf<CharSortBean>()

    override fun setType(type: Int) {
        mType = type
    }

    override fun getValidMapList(): List<MapBean> {
        if (originMapBeanMap.isNotEmpty()) {
            return originMapBeanMap
        }
        when (mType) {
            PAPER_TYPE_DEFAULT -> {
                originMapBeanMap.addAll(MapBeanDbUtils.queryAllMapData())
            }
            else -> {
                PagerBeanDbUtils.queryAllPaperDataByType(mType)
                        .asSequence()
                        .map { it.mapKey }
                        .toSet().mapNotNull {
                            MapBeanDbUtils.queryData(it)
                        }.toList().let { originMapBeanMap.addAll(it) }
            }
        }
        return originMapBeanMap
    }

    override fun resetOriginData() {
        originMapBeanMap.clear()
    }

    override fun getOrderChars(): List<String> {
        return sortCharList.map { it.showChar }
    }

    private fun getMapsByFilter(filter: String?): List<MapShowBean> {
        sortCharList.clear()
        val c1: Comparator<MapShowBean> = Comparator { o1, o2 ->
            if (o1.showChar == "#" && o2.showChar != "#" ) {
                1
            } else if(o1.showChar != "#" && o2.showChar == "#"){
                -1
            } else {
                o1.mapNameSpell.compareTo(o2.mapNameSpell)
            }
        }
        return getValidMapList()
                .asSequence()
                .filter { it.mapName.isNotBlank() }
                .map { MapShowBean(it) }
                .sortedWith(c1)
                .filter { bean ->
                    filter?.let {
                        bean.mapBean.mapName.contains(it, true) ||
                                bean.mapNamePinyin.contains(it, true) ||
                                bean.mapNameSpell.contains(it, true)
                    } ?: true
                }.toList()

    }

    override fun getSortBeanWithFilter(filter: String?): List<ISortBean> {
        var temp = ""
        val sortMapBeanAndCharTag = mutableListOf<ISortBean>()
        getMapsByFilter(filter).forEach {
            it.showChar.let { tag ->
                if (temp != tag) {
                    val charSort = CharSortBean(tag)
                    sortCharList.add(charSort)
                    sortMapBeanAndCharTag.add(charSort)
                    temp = tag
                }
                sortMapBeanAndCharTag.add(it)
            }
        }
        return sortMapBeanAndCharTag
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
