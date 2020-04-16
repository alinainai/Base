package com.gas.zhihu.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "mapbean")
public class MapBean {


    @Id(autoincrement = true)
    Long id;
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
    @Unique
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

    /**
     * 额外参数一
     */
    private String extraStr1;

    /**
     * 额外参数二
     */
    private String extraStr3;

    /**
     * 额外参数参数
     */
    private String extraStr4;


    @Generated(hash = 116259395)
    public MapBean(Long id, String pathName, String locationInfo, String mapName,
            String keyName, String note, double longitude, double latitude,
            String extraStr1, String extraStr3, String extraStr4) {
        this.id = id;
        this.pathName = pathName;
        this.locationInfo = locationInfo;
        this.mapName = mapName;
        this.keyName = keyName;
        this.note = note;
        this.longitude = longitude;
        this.latitude = latitude;
        this.extraStr1 = extraStr1;
        this.extraStr3 = extraStr3;
        this.extraStr4 = extraStr4;
    }

    @Generated(hash = 412228366)
    public MapBean() {
    }


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

    public String getExtraStr1() {
        return extraStr1;
    }

    public void setExtraStr1(String extraStr1) {
        this.extraStr1 = extraStr1;
    }

    public String getExtraStr3() {
        return extraStr3;
    }

    public void setExtraStr3(String extraStr3) {
        this.extraStr3 = extraStr3;
    }

    public String getExtraStr4() {
        return extraStr4;
    }

    public void setExtraStr4(String extraStr4) {
        this.extraStr4 = extraStr4;
    }

    public LocationBean getLocationBean(){

        LocationBean bean = new LocationBean();
        bean.dname=getLocationInfo();
        bean.dlat=getLatitude();
        bean.dlon=getLongitude();
        return bean;

    }

    public void updateInfo(MapBean bean){

        this.latitude=bean.getLatitude();
        this.longitude=bean.getLongitude();
        this.locationInfo=bean.getLocationInfo();
        this.mapName=bean.getMapName();
        this.pathName=bean.getPathName();

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
