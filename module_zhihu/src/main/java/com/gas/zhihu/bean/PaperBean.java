package com.gas.zhihu.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

@Entity(nameInDb = "paperbean")
public class PaperBean {

    @Id(autoincrement = true)
    Long id;
    private String pathName= "";
    private String mapKey;
    private int voltageLevel;
    private String fileName= "";
    private String extraStr1;
    private String extraStr2;
    @Generated(hash = 379483639)
    public PaperBean(Long id, String pathName, String mapKey, int voltageLevel,
            String fileName, String extraStr1, String extraStr2) {
        this.id = id;
        this.pathName = pathName;
        this.mapKey = mapKey;
        this.voltageLevel = voltageLevel;
        this.fileName = fileName;
        this.extraStr1 = extraStr1;
        this.extraStr2 = extraStr2;
    }
    @Generated(hash = 1608836968)
    public PaperBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPathName() {
        return this.pathName;
    }
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }
    public String getMapKey() {
        return this.mapKey;
    }
    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }
    public int getVoltageLevel() {
        return this.voltageLevel;
    }
    public void setVoltageLevel(int voltageLevel) {
        this.voltageLevel = voltageLevel;
    }
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getExtraStr1() {
        return this.extraStr1;
    }
    public void setExtraStr1(String extraStr1) {
        this.extraStr1 = extraStr1;
    }
    public String getExtraStr2() {
        return this.extraStr2;
    }
    public void setExtraStr2(String extraStr2) {
        this.extraStr2 = extraStr2;
    }



}
