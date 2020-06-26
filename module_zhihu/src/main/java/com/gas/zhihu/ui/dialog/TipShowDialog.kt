package com.gas.zhihu.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.base.baseui.dialog.CommonDialog
import com.gas.zhihu.R

object TipShowDialog {

    fun show(context: Context, title: String, info: String, action: () -> Unit = {}) {
        val view = LayoutInflater.from(context).inflate(R.layout.zhihu_dialog_forget_pwd, null)
        val tvTitle = view.findViewById<TextView>(R.id.dialog_title)
        val tvInfo = view.findViewById<TextView>(R.id.dialog_info)
        tvTitle.text = title
        tvInfo.text = info
        val dialog = CommonDialog.Builder()
                .setCancelable(true)
                .create(context,view)
        view.findViewById<View>(R.id.btn_sure).setOnClickListener {
            dialog.dismiss()
            action.invoke()
        }
        dialog.show()
    }
}