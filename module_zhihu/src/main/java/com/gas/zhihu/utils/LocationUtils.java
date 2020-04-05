package com.gas.zhihu.utils;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.IntDef;

import com.base.lib.util.LogUtils;
import com.gas.zhihu.bean.LocationBean;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import timber.log.Timber;


/**
 * ================================================
 * desc: https://www.jianshu.com/p/9bdb2d519309
 * <p>
 * created by author ljx
 * date  2020/4/5
 * email 569932357@qq.com
 * <p>
 * ================================================
 */
public class LocationUtils {

    public static final int MAP_AMAP = 1;
    public static final int MAP_BAIDU = 2;
    public static final int MAP_TECENT = 3;

    // 自定义一个注解MyState
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MAP_AMAP, MAP_BAIDU, MAP_TECENT})
    public @interface MapType {
    }

    public static final String TECENT_MAP_PACKAGE = "com.tencent.map";
    public static final String BAIDU_MAP_PACKAGE = "com.baidu.BaiduMap";
    public static final String AMAP_MAP_PACKAGE = "com.autonavi.minimap";

    private static final String APP_NAME = "com.gas.zhihu";

    public static Intent getBaiduMapIntent(@NotNull LocationBean bean) {

        StringBuilder uri = new StringBuilder("baidumap://map/direction?origin=我的位置");
        uri.append("&destination=name:").append(bean.dname).append("|latlng:").append(bean.dlat).append(",").append(bean.dlon)
                .append("&coord_type=wgs84")
                .append("&mode=driving")
                .append("&car_type=dis")
                .append("&src=").append(APP_NAME);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri.toString()));

        return intent;

    }


    public static Intent getAMapMapIntent(@NotNull LocationBean bean) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.autonavi.minimap");
        intent.addCategory("android.intent.category.DEFAULT");

        StringBuilder uri = new StringBuilder("androidamap://route?sourceApplication=");
        uri.append(APP_NAME)
                .append("&sname=我的位置")
                .append("&dlat=").append(bean.dlat)
                .append("&dlon=").append(bean.dlon)
                .append("&dname=").append(bean.dname)
                .append("&dev=0&t=0");
        intent.setData(Uri.parse(uri.toString()));
        return intent;

    }


    public static Intent getTecentMapIntent(@NotNull LocationBean bean) {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        StringBuilder uri = new StringBuilder("qqmap://map/routeplan?type=drive");
        uri.append("&from=我的位置")
                .append("&fromcoord=0,0")
                .append("&to=" + bean.dname)
                .append("&tocoord=").append(bean.dlat).append(",").append(bean.dlon)
                .append("&referer=").append(APP_NAME);
        intent.setData(Uri.parse(uri.toString()));
        return intent;

    }


}
