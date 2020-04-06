package com.gas.zhihu.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.base.baseui.dialog.CommonDialog
import com.gas.zhihu.R

class TipShowDialog {
    fun show(context: Context?, title: String, info: String) {
        val view = LayoutInflater.from(context).inflate(R.layout.zhihu_dialog_forget_pwd, null)
        val tv_title = view.findViewById<TextView>(R.id.dialog_title)
        val tv_info = view.findViewById<TextView>(R.id.dialog_info)
        tv_title.text = title
        tv_info.text = info
        val dialog = CommonDialog.Builder(context)
                .setCustomView(view)
                .setCancelable(true)
                .create()
        view.findViewById<View>(R.id.btn_sure).setOnClickListener { view1: View? -> dialog.dismiss() }
        dialog.show()
    }
}