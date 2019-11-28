package com.gas.test.bean;

public class TestInfoBean {

    private String showInfo;
    private String tag;

    public TestInfoBean(String showInfo, String tag) {
        this.showInfo = showInfo;
        this.tag = tag;
    }

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
