package com.base.paginate.interfaces;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public interface EmptyInterface {


    public static final int STATUS_LOADING = 0xF1;
    public static final int STATUS_FAIL = 0xF2;
    public static final int STATUS_EMPTY = 0xF3;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_FAIL, STATUS_EMPTY})
    public @interface EmptyType {
    }

    @LayoutRes
    int getLayoutID();

    void setStatus(@EmptyType int status);

}
