package com.base.baseui.dialog.select

import com.base.baseui.dialog.CommonBottomDialog.OnDialogClickListener

interface OnItemOperateListener : OnDialogClickListener {
    fun onItemClickListener(itemId: ISelectItem) {}
    fun onItemSelectListener(itemId: ISelectItem) {}
    fun onItemCheckSureListener(itemIds: List<ISelectItem?>?) {}
}