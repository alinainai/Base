package com.base.baseui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.base.baseui.R;


public class CommonBottomPop extends Dialog {

    public CommonBottomPop(Context context, int theme) {
        super(context, theme);
    }

    public CommonBottomPop(Context context) {
        super(context);
    }


    public interface onDialogClickListener {


        default void onDismiss() {

        }


    }

    public static class Builder {

        private Context mContext;
        private View mContentView;
        private boolean mCancelable = true;
        private onDialogClickListener mDialogClickListener;


        public CommonBottomPop.Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public CommonBottomPop.Builder setCustomView(View contentView) {
            mContentView = contentView;
            return this;
        }

        public CommonBottomPop.Builder setDialogClickListener(onDialogClickListener listener) {
            mDialogClickListener = listener;
            return this;
        }


        public Builder(Context context) {
            mContext = context;
        }

        public CommonBottomPop create() {
            CommonBottomPop dialog = new CommonBottomPop(mContext, R.style.public_common_dialog);
            if (mContentView == null) {
                throw new RuntimeException("mContentView cann't be null");
            } else {
                dialog.setContentView(mContentView);
            }

            dialog.setOnDismissListener(dialogInterface -> {
                if (mDialogClickListener != null) {
                    mDialogClickListener.onDismiss();
                }
            });

            dialog.setCanceledOnTouchOutside(mCancelable);
            //点击back键可以取消dialog
            dialog.setCancelable(mCancelable);

            Window window = dialog.getWindow();
            //让Dialog显示在屏幕的底部
            window.setGravity(Gravity.BOTTOM);
            window.setDimAmount(0.5f);
            //设置窗口出现和窗口隐藏的动画
            window.setWindowAnimations(R.style.dialogBottomWindowAnim);

            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);

            return dialog;

        }


    }
}


