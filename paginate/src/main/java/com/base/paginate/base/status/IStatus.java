package com.base.paginate.base.status;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class IStatus {


    public static final int STATUS_LOADING = 0x0000F001;
    public static final int STATUS_FAIL = 0x0000F002;
    public static final int STATUS_EMPTY = 0x0000F003;
    public static final int STATUS_END = 0x0000F004;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_FAIL, STATUS_EMPTY, STATUS_END})
    public @interface StatusType {
    }


}
