package com.gas.zhihu.utils

import android.content.Intent
import android.net.Uri
import androidx.annotation.IntDef
import com.gas.zhihu.bean.LocationBean
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * ================================================
 * desc: https://www.jianshu.com/p/9bdb2d519309
 *
 *
 * created by author ljx
 * date  2020/4/5
 * email 569932357@qq.com
 *
 *
 * ================================================
 */
object LocationUtils {

    const val MAP_AMAP = 1
    const val MAP_BAIDU = 2
    const val MAP_TECENT = 3
    const val TECENT_MAP_PACKAGE = "com.tencent.map"
    const val BAIDU_MAP_PACKAGE = "com.baidu.BaiduMap"
    const val AMAP_MAP_PACKAGE = "com.autonavi.minimap"
    private const val APP_NAME = "com.gas.zhihu"

    @JvmStatic
    fun getBaiduMapIntent(bean: LocationBean): Intent {
        val uri = StringBuilder("baidumap://map/direction?origin=我的位置")
        uri.append("&destination=name:").append(bean.dname).append("|latlng:").append(bean.dlat).append(",").append(bean.dlon)
                .append("&coord_type=wgs84")
                .append("&mode=driving")
                .append("&car_type=dis")
                .append("&src=").append(APP_NAME)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(uri.toString())
        return intent
    }

    @JvmStatic
    fun getAMapMapIntent(bean: LocationBean): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.autonavi.minimap")
        intent.addCategory("android.intent.category.DEFAULT")
        val uri = StringBuilder("androidamap://route?sourceApplication=")
        uri.append(APP_NAME)
                .append("&sname=我的位置")
                .append("&dlat=").append(bean.dlat)
                .append("&dlon=").append(bean.dlon)
                .append("&dname=").append(bean.dname)
                .append("&dev=0&t=0")
        intent.data = Uri.parse(uri.toString())
        return intent
    }

    @JvmStatic
    fun getTecentMapIntent(bean: LocationBean): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = StringBuilder("qqmap://map/routeplan?type=drive")
        uri.append("&from=我的位置")
                .append("&fromcoord=0,0")
                .append("&to=" + bean.dname)
                .append("&tocoord=").append(bean.dlat).append(",").append(bean.dlon)
                .append("&referer=").append(APP_NAME)
        intent.data = Uri.parse(uri.toString())
        return intent
    }

    // 自定义一个注解MyState
    @Retention(RetentionPolicy.SOURCE)
    @IntDef(MAP_AMAP, MAP_BAIDU, MAP_TECENT)
    annotation class MapType
}