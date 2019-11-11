package com.base.paginate.interfaces;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface FooterInterface {


    public static final int STATUS_LOADING = 0xF1;
    public static final int STATUS_FAIL = 0xF2;
    public static final int STATUS_END = 0xF4;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_FAIL, STATUS_END})
    public @interface FooterType {
    }


    @FooterType
    public abstract int getLoadMoreStatus();

    @LayoutRes
    public abstract int getLayoutID();

    public abstract void setStatus(@FooterType int status);


}
