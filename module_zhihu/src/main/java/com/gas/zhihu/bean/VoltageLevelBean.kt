package com.gas.zhihu.bean

import com.base.baseui.dialog.select.ISelectItem
import java.util.*

class VoltageLevelBean(vId: String, vName: String) : ISelectItem {
    override val name :String
    override var isSelect: Boolean=false
    override val id: String


    companion object {
        val voltageLevelItems: List<VoltageLevelBean>
            get() {
                val items: MutableList<VoltageLevelBean> = ArrayList(5)
                items.add(VoltageLevelBean("0", "220KV"))
                items.add(VoltageLevelBean("1", "110KV"))
                items.add(VoltageLevelBean("2", "35KV"))
                items.add(VoltageLevelBean("3", "10KV及6KV"))
                items.add(VoltageLevelBean("4", "公用部分"))
                return items
            }

        fun getVoltageName(level: String?): String {
            var name = ""
            when (level) {
                "0" -> name = "220KV"
                "1" -> name = "110KV"
                "2" -> name = "35KV"
                "3" -> name = "10KV及6KV"
                "4" -> name = "公用部分"
            }
            return name
        }
    }

    init {
        name = vName
        id = vId
    }
}