package com.gas.zhihu.utils;

import com.lib.commonsdk.utils.GasAppUtils;
import com.lib.commonsdk.utils.sp.SPUtils;

public class ZhihuUtils {


    private static final String ZHIHU_SP="ZHIHU_SP";
    private static final String APP_VERSION_CODE="APP_VERSION_CODE";

    public static int getSpVersionCode(){
       return SPUtils.getInstance(ZHIHU_SP).getInt(APP_VERSION_CODE,0);
    }

    public static void setSpVersionCode(){
        SPUtils.getInstance(ZHIHU_SP).put(APP_VERSION_CODE, GasAppUtils.getAppVersionCode());
    }



}
