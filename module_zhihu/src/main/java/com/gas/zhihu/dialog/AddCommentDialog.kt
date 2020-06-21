package com.gas.zhihu.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.base.baseui.dialog.CommonDialog
import com.gas.zhihu.R
import kotlinx.android.synthetic.main.zhihu_dialog_add_comment.view.*

class AddCommentDialog {

    fun show(context: Context?, mapClickListener: OnCommitClickListener?) {
        @SuppressLint("InflateParams") val view = LayoutInflater.from(context).inflate(R.layout.zhihu_dialog_add_comment, null)

        val dialog = CommonDialog.Builder()
                .setDialogClickListener(mapClickListener)
                .setCancelable(true)
                .create(context,view)

        view.apply {
            btnCancel.setOnClickListener { v: View? ->
                dialog.dismiss()
            }
            btnCommit.setOnClickListener { v: View? ->
                if (mapClickListener != null) {
                    dialog.dismiss()
                    mapClickListener.onCommitClick( etContent.text.toString())
                }
            }
        }


        dialog.show()
    }

    interface OnCommitClickListener : CommonDialog.onDialogClickListener {
        fun onCommitClick(str:String)
    }
}