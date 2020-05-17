package com.gas.zhihu.bean;

import com.base.baseui.dialog.select.ISelectItem;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VoltageLevelBean implements ISelectItem {

    public VoltageLevelBean( int vID,String name) {
        this.name = name;
        this.vID = vID;
    }

    private String name="";
    private int vID;

    @Override
    public int getId() {
        return vID;
    }

    @Nullable
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isSelect() {
        return false;
    }


    public static List<VoltageLevelBean> getVoltageLevelItems(){

        List<VoltageLevelBean> items= new ArrayList<>(5);
        items.add(new VoltageLevelBean(0,"220KV"));
        items.add(new VoltageLevelBean(1,"110KV"));
        items.add(new VoltageLevelBean(2,"35KV"));
        items.add(new VoltageLevelBean(3,"10KV及6KV"));
        items.add(new VoltageLevelBean(4,"公用部分"));
        return items;


    }


}
