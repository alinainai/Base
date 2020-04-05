package com.base.baseui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.base.baseui.R;


public class CommonDialog extends Dialog {

    public CommonDialog(Context context, int theme) {
        super(context, theme);
    }

    public CommonDialog(Context context) {
        super(context);
    }


    public interface onDialogClickListener {

        default void onLeftClick() {

        }

        void onRightClick();

        default void onDismiss() {

        }


    }

    public static class Builder {

        private Context mContext;

        private String mTitle;
        private String mPositiveBtnText;
        private String mNegativeBtnText;
        private View mContentView;
        private boolean mCancelable = true;
        private onDialogClickListener mDialogClickListener;


        public CommonDialog.Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }


        public CommonDialog.Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public CommonDialog.Builder setLeftTitle(String leftTitle) {
            mPositiveBtnText = leftTitle;
            return this;
        }

        public CommonDialog.Builder setRightTitle(String rightTitle) {
            mNegativeBtnText = rightTitle;
            return this;
        }


        public CommonDialog.Builder setTitle(int resId) {
            mTitle = mContext.getString(resId);
            return this;
        }

        public CommonDialog.Builder setCustomView(View contentView) {
            mContentView = contentView;
            return this;
        }

        public CommonDialog.Builder setDialogClickListener(onDialogClickListener listener) {
            mDialogClickListener = listener;
            return this;
        }


        public Builder(Context context) {
            mContext = context;
        }

        public CommonDialog create() {
            CommonDialog dialog = new CommonDialog(mContext, R.style.public_common_dialog);
            if (mContentView == null) {

                @SuppressLint("InflateParams")
                View view = LayoutInflater.from(mContext).inflate(R.layout.public_dialog_common, null);
                TextView title = view.findViewById(R.id.dialog_title);
                TextView left_title = view.findViewById(R.id.left_title);
                TextView right_title = view.findViewById(R.id.right_title);

                if (!TextUtils.isEmpty(mTitle)) {
                    title.setText(mTitle);
                }
                if (!TextUtils.isEmpty(mPositiveBtnText)) {
                    left_title.setVisibility(View.VISIBLE);
                    left_title.setText(mPositiveBtnText);
                } else {
                    left_title.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(mNegativeBtnText)) {
                    right_title.setText(mNegativeBtnText);
                }

                right_title.setOnClickListener(view12 -> {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (mDialogClickListener != null) {
                        mDialogClickListener.onRightClick();
                    }
                });
                left_title.setOnClickListener(view1 -> {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (mDialogClickListener != null) {
                        mDialogClickListener.onLeftClick();
                    }
                });

                dialog.setContentView(view);


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
            window.setGravity(Gravity.CENTER);
            window.setDimAmount(0.5f);
            //设置窗口出现和窗口隐藏的动画
//            window.setWindowAnimations(R.style.dialogCenterWindowAnim);

            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);

            return dialog;

        }


    }
}


