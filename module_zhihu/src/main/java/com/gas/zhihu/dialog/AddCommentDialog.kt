package com.gas.zhihu.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.base.baseui.dialog.CommonBottomDialog
import com.base.baseui.dialog.CommonDialog
import com.gas.zhihu.R
import com.gas.zhihu.utils.LocationUtils
import com.gas.zhihu.utils.LocationUtils.MapType
import com.lib.commonsdk.utils.GasAppUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class AddCommentDialog {

    fun show(context: Context?, mapClickListener: OnCommitClickListener?) {
        @SuppressLint("InflateParams") val view = LayoutInflater.from(context).inflate(R.layout.zhihu_dialog_add_comment, null)

        val btnCancel = view.findViewById<View>(R.id.btnCancel)
        val etContent = view.findViewById<EditText>(R.id.etContent)
        val btnCommit = view.findViewById<View>(R.id.btnCommit)

        val dialog = CommonDialog.Builder(context)
                .setDialogClickListener(mapClickListener)
                .setCancelable(true)
                .setCustomView(view)
                .create()

        btnCancel.setOnClickListener { v: View? ->
            dialog.dismiss()
        }
        btnCommit.setOnClickListener { v: View? ->
            if (mapClickListener != null) {
                dialog.dismiss()
                mapClickListener.onCommitClick(etContent.text.toString())
            }
        }

        dialog.show()
    }

    interface OnCommitClickListener : CommonDialog.onDialogClickListener {
        fun onCommitClick(str:String)
    }
}