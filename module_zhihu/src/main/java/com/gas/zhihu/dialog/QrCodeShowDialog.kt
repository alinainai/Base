package com.gas.zhihu.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.base.baseui.dialog.CommonDialog
import com.gas.zhihu.R
import com.lib.commonsdk.utils.QRCode


class QrCodeShowDialog {
    fun show(context: Context?, title: String, info: String) {
        val view = LayoutInflater.from(context).inflate(R.layout.zhihu_dialog_qr_code_show, null)
        val dialog_title = view.findViewById<TextView>(R.id.dialog_title)
        val dialog_info = view.findViewById<ImageView>(R.id.dialog_info)
        dialog_title.text = title
        dialog_info.setImageBitmap(QRCode.createQRCode(info, 800))

        val dialog = CommonDialog.Builder(context)
                .setCustomView(view)
                .setCancelable(true)
                .create()
        view.findViewById<View>(R.id.btn_sure).setOnClickListener { view1: View? -> dialog.dismiss() }
        dialog.show()
    }
}