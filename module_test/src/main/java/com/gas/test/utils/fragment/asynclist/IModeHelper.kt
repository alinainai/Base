package com.gas.test.utils.fragment.asynclist

enum class DisPlayMode(val mode: Int) {
    LIST(0), // 0：列表
    GRID(1); // 1：小图

    companion object {
        fun parseFromInt(mode: Int) = values().find { it.mode == mode } ?: LIST
    }
}

fun DisPlayMode.isList():Boolean{
    return this==DisPlayMode.LIST
}

interface IModeHelper {
    companion object{
        // 普通事件类型 list 风格
        const val VIEW_TYPE_EVENT_ITEM = 0x01
        // 普通事件类型 grid 风格
        const val VIEW_TYPE_EVENT_ITEM_GRID = 0x02
    }
    var displayMode: DisPlayMode
}