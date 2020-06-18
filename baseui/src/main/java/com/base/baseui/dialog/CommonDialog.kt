package com.base.baseui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.base.baseui.R

class CommonDialog : Dialog {
    constructor(context: Context?, theme: Int) : super(context!!, theme) {}
    constructor(context: Context?) : super(context!!) {}

    class Builder {
        private var mTitle: String? = null
        private var mPositiveBtnText: String? = null
        private var mNegativeBtnText: String? = null
        private var mContentView: View? = null
        private var mCancelable = true
        private var mDialogClickListener: OnDialogClickListener? = null
        fun setCancelable(cancelable: Boolean): Builder {
            mCancelable = cancelable
            return this
        }

        fun setTitle(title: String?): Builder {
            mTitle = title
            return this
        }

        fun setLeftTitle(leftTitle: String?): Builder {
            mPositiveBtnText = leftTitle
            return this
        }

        fun setRightTitle(rightTitle: String?): Builder {
            mNegativeBtnText = rightTitle
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

        fun create(context: Context?): CommonDialog {
            val dialog = CommonDialog(context, R.style.public_common_dialog)
            if (mContentView == null) {
                @SuppressLint("InflateParams") val view = LayoutInflater.from(context).inflate(R.layout.public_dialog_common, null)
                val title = view.findViewById<TextView>(R.id.dialog_title)
                val left_title = view.findViewById<TextView>(R.id.left_title)
                val right_title = view.findViewById<TextView>(R.id.right_title)
                if (!TextUtils.isEmpty(mTitle)) {
                    title.text = mTitle
                }
                if (!TextUtils.isEmpty(mPositiveBtnText)) {
                    left_title.visibility = View.VISIBLE
                    left_title.text = mPositiveBtnText
                } else {
                    left_title.visibility = View.GONE
                }
                if (!TextUtils.isEmpty(mNegativeBtnText)) {
                    right_title.text = mNegativeBtnText
                }
                right_title.setOnClickListener { view12: View? ->
                    if (dialog.isShowing) {
                        dialog.dismiss()
                    }
                    if (mDialogClickListener != null) {
                        mDialogClickListener!!.onRightClick()
                    }
                }
                left_title.setOnClickListener { view1: View? ->
                    if (dialog.isShowing) {
                        dialog.dismiss()
                    }
                    if (mDialogClickListener != null) {
                        mDialogClickListener!!.onLeftClick()
                    }
                }
                dialog.setContentView(view)
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
            window!!.setGravity(Gravity.CENTER)
            window.setDimAmount(0.5f)
            //设置窗口出现和窗口隐藏的动画
//            window.setWindowAnimations(R.style.dialogCenterWindowAnim);
            val lp = window.attributes
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
            return dialog
        }
    }

    interface OnDialogClickListener {
        fun onLeftClick() {}
        fun onRightClick() {}
        fun onDismiss() {}
    }
}