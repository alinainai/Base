package com.base.baseui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.base.baseui.R;

public class GasAlertDialog extends GasDialog {


    private static final String GASTITLE = "GASTITLE";
    private static final String GASMESSAGE = "GASMESSAGE";
    private static final String GASBTNNEGATIVE = "GASBTNNEGATIVE";
    private static final String GASBTNPOSITIVE = "GASBTNPOSITIVE";
    private static final String CANCELABLE = "CANCELABLE";
    private GasDialog.OnGasBtnClickListener mOnNegBtnClickListener;
    private GasDialog.OnGasBtnClickListener mOnPosBtnClickListener;


    public GasAlertDialog() {
    }

    public static GasAlertDialog newInstance(String title, String message, String btnNegative, String btnPositive, boolean cancelable) {
        GasAlertDialog dialog = new GasAlertDialog();
        Bundle arguments = new Bundle();
        arguments.putString(GASTITLE, title);
        arguments.putString(GASMESSAGE, message);
        arguments.putString(GASBTNNEGATIVE, btnNegative);
        arguments.putString(GASBTNPOSITIVE, btnPositive);
        arguments.putBoolean(CANCELABLE, cancelable);
        dialog.setArguments(arguments);
        return dialog;
    }

    public void setOnNegBtnClickListener(GasDialog.OnGasBtnClickListener onNegBtnClickListener) {
        this.mOnNegBtnClickListener = onNegBtnClickListener;
    }

    public void setOnPosBtnClickListener(GasDialog.OnGasBtnClickListener onPosBtnClickListener) {
        this.mOnPosBtnClickListener = onPosBtnClickListener;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.baseui_dialog_gas;
    }

    @Override
    public void bindView(View v) {

        TextView tvTitle = v.findViewById(R.id.tv_gas_title);
        TextView tvMessage = v.findViewById(R.id.tv_gas_msg);
        TextView tvNegative = v.findViewById(R.id.btn_neg);
        TextView tvPositive = v.findViewById(R.id.btn_pos);
        FrameLayout imgline = v.findViewById(R.id.img_line);

        if (null != getArguments()) {
            String title = getArguments().getString(GASTITLE, "");
            String msg = getArguments().getString(GASMESSAGE, "");
            String posBtnTxt = getArguments().getString(GASBTNPOSITIVE, "");
            String negBtnTxt = getArguments().getString(GASBTNNEGATIVE, "");
            boolean cancel = getArguments().getBoolean(CANCELABLE, true);
            setCancelable(cancel);
            getDialog().setCanceledOnTouchOutside(cancel);
            //title 和 msg 必须显示一个
            if (TextUtils.isEmpty(title)) {
                tvTitle.setVisibility(View.GONE);
                tvMessage.setTextSize(16);
            } else {
                tvTitle.setText(title);
                if (TextUtils.isEmpty(msg))
                    tvMessage.setVisibility(View.GONE);
            }
            if (tvMessage.getVisibility() != View.GONE)
                tvMessage.setText(msg);
            if (TextUtils.isEmpty(posBtnTxt)) {
                tvPositive.setText("确定");
            }
            tvPositive.setOnClickListener(v1 -> {
                if (null != mOnPosBtnClickListener) {
                    mOnPosBtnClickListener.onClick(tvPositive);
                } else {
                    dismiss();
                }
            });
            if (TextUtils.isEmpty(negBtnTxt)) {
                if (tvNegative.getVisibility() != View.GONE)
                    tvNegative.setVisibility(View.GONE);
                if (imgline.getVisibility() != View.GONE)
                    imgline.setVisibility(View.GONE);
                tvPositive.setBackgroundResource(R.drawable.gas_select_middle_btn);
            } else {
                tvNegative.setText(negBtnTxt);
                tvNegative.setOnClickListener(v2 -> {
                    if (null != mOnNegBtnClickListener) {
                        mOnNegBtnClickListener.onClick(tvNegative);
                    } else {
                        dismiss();
                    }
                });
            }
        }
    }

    @Override
    public float getDimAmount() {
        return 0.5f;
    }


    public static class Builder {

        public Builder() {
        }

        private String mTitle;
        private String mMsg;
        private String mPosBtnTxt;
        private String mNegBtnTxt;
        private boolean mCancelable;
        private GasDialog.OnGasBtnClickListener mOnNegBtnClickListener;
        private GasDialog.OnGasBtnClickListener mOnPosBtnClickListener;

        public Builder setTitle(String mTitle) {
            this.mTitle = mTitle;
            return this;
        }

        public Builder setMsg(String msg) {
            this.mMsg = msg;
            return this;
        }

        public Builder setPosBtnTxt(String posBtnTxt, @Nullable GasDialog.OnGasBtnClickListener onPosBtnClickListener) {
            this.mPosBtnTxt = posBtnTxt;
            this.mOnPosBtnClickListener = onPosBtnClickListener;
            return this;
        }

        public Builder setNegBtnTxt(String negBtnTxt, @Nullable GasDialog.OnGasBtnClickListener onNegBtnClickListener) {
            this.mNegBtnTxt = negBtnTxt;
            this.mOnNegBtnClickListener = onNegBtnClickListener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public GasAlertDialog creat() {
            GasAlertDialog dialog = GasAlertDialog.newInstance(mTitle, mMsg, mNegBtnTxt, mPosBtnTxt, mCancelable);
            dialog.setOnNegBtnClickListener(mOnNegBtnClickListener);
            dialog.setOnPosBtnClickListener(mOnPosBtnClickListener);
            return dialog;
        }

    }

}
