package com.base.baseui.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.base.baseui.R

class CommonBottomDialog : Dialog {
    constructor(context: Context?, theme: Int) : super(context!!, theme) {}
    constructor(context: Context?) : super(context!!) {}

    interface OnDialogClickListener {
        fun onDismiss() {}
    }

    class Builder {
        private var mContentView: View? = null
        private var mCancelable = true
        private var mDialogClickListener: OnDialogClickListener? = null
        fun setCancelable(cancelable: Boolean): Builder {
            mCancelable = cancelable
            return this
        }

        fun setCustomView(contentView: View?): Builder {
            mContentView = contentView
            return this
        }

        fun setDialogClickListener(listener: OnDialogClickListener?): Builder {
            mDialogClickListener = listener
            return this
        }

        fun create(context: Context?): CommonBottomDialog {
            val dialog = CommonBottomDialog(context, R.style.public_common_dialog)
            if (mContentView == null) {
                throw RuntimeException("mContentView cann't be null")
            } else {
                dialog.setContentView(mContentView!!)
            }
            dialog.setOnDismissListener { dialogInterface: DialogInterface? ->
                if (mDialogClickListener != null) {
                    mDialogClickListener!!.onDismiss()
                }
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