package com.gas.zhihu.bean;

import android.text.TextUtils;

public class LocationBean {


    private static final double DEFAULT_NUM=-1D;

    /**
     * 纬度
     */
   public double dlat = DEFAULT_NUM;
    /**
     * 经度
     */
    public double dlon = DEFAULT_NUM;
    /**
     * 名称
     */
    public String dname = "";


    public boolean isInfoError(){
        return dlon == DEFAULT_NUM || dlat == DEFAULT_NUM;
    }



}
