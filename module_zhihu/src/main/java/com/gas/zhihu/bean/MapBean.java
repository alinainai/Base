package com.gas.zhihu.bean;

public class MapBean {

    /**
     * 存储地址
     */
    private String pathName= "";
    /**
     * 定位信息
     */
    private String locationInfo= "";
    /**
     * 地图名
     */
    private String mapName= "";
    /**
     * 搜索键名
     */
    private String keyName= "";
    /**
     * 备注
     */
    private String note= "";

    /**
     * 经度
     */
    private double longitude;

    /**
     * 纬度
     */
    private double latitude;


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocationBean getLocationBean(){

        LocationBean bean = new LocationBean();
        bean.dname=getLocationInfo();
        bean.dlat=getLatitude();
        bean.dlon=getLongitude();
        return bean;

    }

}
