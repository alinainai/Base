package trunk.doi.base.bean;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Author: Othershe
 * Time: 2016/8/12 14:34
 */


public class CollectionBean {



    private String _id;
    private String desc;
    private String dataTime;
    private String url;



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CollectionBean{" +
                "_id='" + _id + '\'' +
                ", desc='" + desc + '\'' +
                ", dataTime='" + dataTime + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
