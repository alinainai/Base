package com.gas.zhihu.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.base.baseui.dialog.CommonDialog
import com.gas.zhihu.R
import com.lib.commonsdk.utils.QRCode
import kotlinx.android.synthetic.main.zhihu_dialog_qr_code_show.view.*


class QrCodeShowDialog {
    fun show(context: Context?, title: String, info: String) {
        val view = LayoutInflater.from(context).inflate(R.layout.zhihu_dialog_qr_code_show, null)
        view.dialog_title.text = title
        view.dialog_info.setImageBitmap(QRCode.createQRCode(info, 800))

        val dialog = CommonDialog.Builder()
                .setCustomView(view)
                .setCancelable(true)
                .create(context)
        view.btn_sure.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}