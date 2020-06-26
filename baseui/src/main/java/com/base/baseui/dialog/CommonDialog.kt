package com.base.baseui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.base.baseui.R

class CommonDialog : Dialog {
    constructor(context: Context, theme: Int) : super(context, theme) {}
    constructor(context: Context) : super(context) {}

    class Builder {
        private var mTitle: String? = null
        private var mPositiveBtnText: String? = null
        private var mNegativeBtnText: String? = null
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

        fun setDialogClickListener(listener: OnDialogClickListener?): Builder {
            mDialogClickListener = listener
            return this
        }

        fun create(context: Context,contentView: View?): CommonDialog {
            val dialog = CommonDialog(context, R.style.public_common_dialog)
            if (contentView == null) {
                @SuppressLint("InflateParams") val view = LayoutInflater.from(context).inflate(R.layout.public_dialog_common, null)
                val title = view.findViewById<TextView>(R.id.dialog_title)
                val leftTitle = view.findViewById<TextView>(R.id.left_title)
                val rightTitle = view.findViewById<TextView>(R.id.right_title)
                if (!TextUtils.isEmpty(mTitle)) {
                    title.text = mTitle
                }
                if (!TextUtils.isEmpty(mPositiveBtnText)) {
                    leftTitle.visibility = View.VISIBLE
                    leftTitle.text = mPositiveBtnText
                } else {
                    leftTitle.visibility = View.GONE
                }
                if (!TextUtils.isEmpty(mNegativeBtnText)) {
                    rightTitle.text = mNegativeBtnText
                }
                rightTitle.setOnClickListener {
                    if (dialog.isShowing) {
                        dialog.dismiss()
                    }
                    if (mDialogClickListener != null) {
                        mDialogClickListener!!.onRightClick()
                    }
                }
                leftTitle.setOnClickListener {
                    if (dialog.isShowing) {
                        dialog.dismiss()
                    }
                    if (mDialogClickListener != null) {
                        mDialogClickListener!!.onLeftClick()
                    }
                }
                dialog.setContentView(view)
            } else {
                dialog.setContentView(contentView)
            }
            dialog.setOnDismissListener {
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