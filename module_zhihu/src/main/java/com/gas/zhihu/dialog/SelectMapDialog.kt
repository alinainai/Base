package com.gas.zhihu.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.base.baseui.dialog.CommonBottomDialog
import com.gas.zhihu.R
import com.gas.zhihu.utils.LocationUtils
import com.gas.zhihu.utils.LocationUtils.MapType
import com.lib.commonsdk.utils.GasAppUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class SelectMapDialog {
    private var mDisposable: Disposable? = null
    fun show(context: Context?, mapClickListener: OnMapClickListener?) {
        @SuppressLint("InflateParams") val view = LayoutInflater.from(context).inflate(R.layout.zhihu_dialog_select_map, null)
        val loading = view.findViewById<View>(R.id.loading)
        val empty = view.findViewById<View>(R.id.empty)
        val dialog_cancel = view.findViewById<View>(R.id.dialog_cancel)
        val tv_amap_map = view.findViewById<View>(R.id.tv_amap_map)
        val tv_baidu_map = view.findViewById<View>(R.id.tv_baidu_map)
        val tv_tecent_map = view.findViewById<View>(R.id.tv_tecent_map)
        val dialog = CommonBottomDialog.Builder(context)
                .setDialogClickListener(mapClickListener)
                .setCancelable(true)
                .setCustomView(view)
                .create()
        tv_amap_map.setOnClickListener { v: View? ->
            if (GasAppUtils.checkMapAppsIsExist(LocationUtils.AMAP_MAP_PACKAGE)) {
                if (mapClickListener != null) {
                    dialog.dismiss()
                    mapClickListener.onMapClick(LocationUtils.MAP_AMAP)
                }
            } else {
                GasAppUtils.toast("高德地图未安装")
            }
        }
        tv_baidu_map.setOnClickListener { v: View? ->
            if (GasAppUtils.checkMapAppsIsExist(LocationUtils.BAIDU_MAP_PACKAGE)) {
                if (mapClickListener != null) {
                    dialog.dismiss()
                    mapClickListener.onMapClick(LocationUtils.MAP_BAIDU)
                }
            } else {
                GasAppUtils.toast("百度地图未安装")
            }
        }
        tv_tecent_map.setOnClickListener { v: View? ->
            if (GasAppUtils.checkMapAppsIsExist(LocationUtils.TECENT_MAP_PACKAGE)) {
                if (mapClickListener != null) {
                    dialog.dismiss()
                    mapClickListener.onMapClick(LocationUtils.MAP_TECENT)
                }
            } else {
                GasAppUtils.toast("腾讯地图未安装")
            }
        }
        dialog_cancel.setOnClickListener { v: View? ->
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
        mDisposable = Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { aLong: Long? ->
                    loading.visibility = View.GONE
                    if (GasAppUtils.checkMapAppsIsExist(LocationUtils.TECENT_MAP_PACKAGE)) {
                        tv_tecent_map.visibility = View.VISIBLE
                    }
                    if (GasAppUtils.checkMapAppsIsExist(LocationUtils.BAIDU_MAP_PACKAGE)) {
                        tv_baidu_map.visibility = View.VISIBLE
                    }
                    if (GasAppUtils.checkMapAppsIsExist(LocationUtils.AMAP_MAP_PACKAGE)) {
                        tv_amap_map.visibility = View.VISIBLE
                    }
                    if (tv_tecent_map.visibility == View.GONE && tv_baidu_map.visibility == View.GONE && tv_amap_map.visibility == View.GONE) {
                        empty.visibility = View.VISIBLE
                    }
                    mDisposable!!.dispose()
                }
        dialog.show()
    }

    interface OnMapClickListener : CommonBottomDialog.onDialogClickListener {
        fun onMapClick(@MapType map: Int)
    }
}