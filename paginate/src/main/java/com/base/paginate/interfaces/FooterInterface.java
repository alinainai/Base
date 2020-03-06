package com.base.paginate.interfaces;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface FooterInterface {


    int STATUS_LOADING = 0xF1;
     int STATUS_FAIL = 0xF2;
    int STATUS_END = 0xF4;
    int STATUS_PRE_LOADING = 0xF5;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_FAIL, STATUS_END,STATUS_PRE_LOADING})
    public @interface FooterType {
    }


    @FooterType
    public abstract int getLoadMoreStatus();

    @LayoutRes
    public abstract int getLayoutID();

    public abstract void setStatus(@FooterType int status);


}
