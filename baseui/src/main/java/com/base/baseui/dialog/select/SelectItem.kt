package com.base.baseui.dialog.select

data class SelectItem(override val id: String, override val name: String, override var isSelect: Boolean=false) : ISelectItem {
}