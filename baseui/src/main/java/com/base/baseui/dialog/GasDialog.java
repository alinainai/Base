package com.base.baseui.dialog;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.base.baseui.R;

import java.util.Objects;

public abstract class GasDialog extends DialogFragment {


    private static final String TAG = "GasDialog";
    private static final float DEFAULT_DIM = 0.2f;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.gas_dialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        final Window window = getDialog().getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.dimAmount = getDimAmount();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(getDialog().getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(getLayoutRes(), container, false);
        bindView(v);
        return v;
    }

    @LayoutRes
    public abstract int getLayoutRes();

    public abstract void bindView(View v);

    public void show(FragmentManager fragmentManager) {
            showNow(fragmentManager, TAG);
    }

    public float getDimAmount() {
        return DEFAULT_DIM;
    }


    public interface OnGasBtnClickListener {
        void onClick(View view);
    }

}
