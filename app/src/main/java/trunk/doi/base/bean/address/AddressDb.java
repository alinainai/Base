package trunk.doi.base.bean.address;

/**
 * 数据库中数据模型
 * 作者：Mr.Lee on 2016-10-31 11:56
 * 邮箱：569932357@qq.com
 */

public class AddressDb {

    private String cityid;
    private String pcityid;
    private String cityname;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getPcityid() {
        return pcityid;
    }

    public void setPcityid(String pcityid) {
        this.pcityid = pcityid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
