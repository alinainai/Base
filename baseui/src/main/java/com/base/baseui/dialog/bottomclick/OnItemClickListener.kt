package com.base.baseui.dialog.bottomclick

import com.base.baseui.dialog.CommonBottomDialog.OnDialogClickListener

interface OnItemClickListener : OnDialogClickListener {
    fun onItemClickListener(itemId: IClickItem) {}
}