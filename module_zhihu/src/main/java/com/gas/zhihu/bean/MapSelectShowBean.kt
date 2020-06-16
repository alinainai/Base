package com.gas.zhihu.bean

import com.base.baseui.dialog.select.ISelectItem
import com.gas.zhihu.app.ZhihuConstants

data class MapSelectShowBean(val mapBean: MapBean) : ISelectItem {
    override val id: String
        get() = mapBean.keyName
    override val name: String
        get() = "${mapBean.mapName}${if (mapBean.keyName != ZhihuConstants.DEFAULT_TYPE) "（${mapBean.keyName}）" else ""}"
    override var isSelect: Boolean = false
        get() = field
        set(value) {
            field = value
        }
}