package com.base.baseui.dialog;

import android.content.Context;

import androidx.annotation.IntDef;

import com.base.lib.util.Preconditions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;


public class SelectBottomDialog {


    public static final int MODE_CLICK = 1;
    public static final int MODE_SELECT = 2;
    public static final int MODE_CHECK = 3;

    // 自定义一个注解MyState
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MODE_CLICK, MODE_SELECT, MODE_CHECK})
    public @interface Mode {
    }

    private Context mContext;
    private CommonBottomDialog.Builder mBuilder;
    private onItemOptionListener mListener;
    private @Mode
    int mMode = MODE_CLICK;

    private SelectBottomDialog(Context context) {
        mContext = context;
        mBuilder = new CommonBottomDialog.Builder(context);
    }


    public static SelectBottomDialog getInstance(Context context) {
        return new SelectBottomDialog(context);
    }

    public SelectBottomDialog setCancelable(boolean isCancelable) {
        Preconditions.checkNotNull(mBuilder, "You should call getInstance first");
        mBuilder.setCancelable(isCancelable);
        return this;
    }

    public SelectBottomDialog setOnItemOptionListener(onItemOptionListener listener) {
        Preconditions.checkNotNull(mBuilder, "You should call getInstance first");
        mBuilder.setDialogClickListener(listener);
        mListener = listener;
        return this;
    }

    public SelectBottomDialog setMode(@Mode int mode) {
        Preconditions.checkNotNull(mContext, "You should call getInstance first");
        mMode = mode;
        return this;
    }

    public void show(){

    }

    public interface onItemOptionListener extends CommonBottomDialog.onDialogClickListener {


        default void onItemClickListener(int itemId) {

        }

        default void onItemSelectListener(int itemId) {

        }

        default void onItemCheckSureListener(List<Integer> itemIds) {

        }

    }


}


