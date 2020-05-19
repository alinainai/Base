package com.gas.zhihu.bean;

import com.base.baseui.dialog.select.ISelectItem;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VoltageLevelBean implements ISelectItem {

    public VoltageLevelBean(String vID, String name) {
        this.name = name;
        this.vID = vID;
    }

    private String name = "";
    private String vID;
    private boolean mSelect=false;

    @Override
    public String getId() {
        return vID;
    }

    @Nullable
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isSelect() {
        return mSelect;
    }

    @Override
    public void setSelect(boolean select) {
        mSelect = select;
    }


    public static List<VoltageLevelBean> getVoltageLevelItems() {

        List<VoltageLevelBean> items = new ArrayList<>(5);
        items.add(new VoltageLevelBean("0", "220KV"));
        items.add(new VoltageLevelBean("1", "110KV"));
        items.add(new VoltageLevelBean("2", "35KV"));
        items.add(new VoltageLevelBean("3", "10KV及6KV"));
        items.add(new VoltageLevelBean("4", "公用部分"));
        return items;


    }

    public static String getVoltageName(String level) {

        String name = "";
        switch (level) {
            case "0":
                name = "220KV";
                break;
            case "1":
                name = "110KV";
                break;
            case "2":
                name = "35KV";
                break;
            case "3":
                name = "10KV及6KV";
                break;
            case "4":
                name = "公用部分";
                break;
        }
        return name;

    }



}
