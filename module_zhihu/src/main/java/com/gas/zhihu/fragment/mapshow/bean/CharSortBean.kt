package com.gas.zhihu.fragment.mapshow.bean

data class CharSortBean(val charName:String) :ISortBean{
    override val showChar: String
        get() = charName
    override val showTitle: String
        get() = charName
}