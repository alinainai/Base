package com.base.baseui.dialog.select

interface ISelectItem {
    val id: String?
    val name: String?
    val isSelect: Boolean
    fun setSelect(select:Boolean);
}