package com.base.baseui.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.base.baseui.R

class CommonBottomDialog(context: Context, theme: Int = R.style.public_common_dialog) : Dialog(context, theme) {


    class Builder {
        var mCancelable = true
        var mDialogClickListener: DialogInterface.OnDismissListener? = null
        fun cancelable(cancelable: Boolean) = apply {
            mCancelable = cancelable
        }

        fun onDismissClickListener(listener: DialogInterface.OnDismissListener) = apply {
            mDialogClickListener = listener
        }

        fun create(context: Context, view: View): CommonBottomDialog {
            val dialog = CommonBottomDialog(context, R.style.public_common_dialog)
            dialog.setContentView(view)
            mDialogClickListener?.let {
                dialog.setOnDismissListener(mDialogClickListener)
            }
            dialog.setCanceledOnTouchOutside(mCancelable)
            //点击back键可以取消dialog
            dialog.setCancelable(mCancelable)
            val window = dialog.window
            //让Dialog显示在屏幕的底部
            window!!.setGravity(Gravity.BOTTOM)
            window.setDimAmount(0.5f)
            //设置窗口出现和窗口隐藏的动画
            window.setWindowAnimations(R.style.dialogBottomWindowAnim)
            val lp = window.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
            return dialog
        }
    }
}