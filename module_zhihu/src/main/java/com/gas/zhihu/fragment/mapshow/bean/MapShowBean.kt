package com.gas.zhihu.fragment.mapshow.bean

import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.utils.toAllPySpell
import com.gas.zhihu.utils.toFirstPySpell
import com.gas.zhihu.utils.toPinYin

data class MapShowBean(val mapBean: MapBean) : ISortBean {
    override val showChar: String
        get() = mapBean.mapName.toFirstPySpell() ?: "#"
    override val showTitle: String
        get() = mapBean.mapName

    val mapNamePinyin = mapBean.mapName.toPinYin()?:""
    val mapNameSpell = mapBean.mapName.toAllPySpell()?:""
}