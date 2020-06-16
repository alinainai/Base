package com.base.paginate.interfaces;

import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public interface EmptyInterface {


    int STATUS_LOADING = 0xF1;
    int STATUS_FAIL = 0xF2;
    int STATUS_EMPTY = 0xF3;
    int STATUS_NETWORK_FAIL = 0xF4;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_FAIL, STATUS_EMPTY, STATUS_NETWORK_FAIL})
    public @interface EmptyType {
    }

    @LayoutRes
    int getLayoutID();

    void setStatus(@EmptyType int status);

    View getRefreshView();

}
