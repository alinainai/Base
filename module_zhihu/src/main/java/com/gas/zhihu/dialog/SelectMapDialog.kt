package com.gas.zhihu.dialog

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.base.baseui.dialog.CommonBottomDialog
import com.gas.zhihu.R
import com.gas.zhihu.utils.LocationUtils
import com.gas.zhihu.utils.LocationUtils.MapType
import com.lib.commonsdk.utils.AppUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class SelectMapDialog constructor(val context: Context) {
    private var mDisposable: Disposable? = null
    private val shortAnimationDuration by lazy {
        context.resources.getInteger(android.R.integer.config_shortAnimTime)
    }

    fun show(mapClickListener: OnMapClickListener?) {
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
            if (AppUtils.checkMapAppsIsExist(LocationUtils.AMAP_MAP_PACKAGE)) {
                if (mapClickListener != null) {
                    dialog.dismiss()
                    mapClickListener.onMapClick(LocationUtils.MAP_AMAP)
                }
            } else {
                AppUtils.toast("高德地图未安装")
            }
        }
        tv_baidu_map.setOnClickListener { v: View? ->
            if (AppUtils.checkMapAppsIsExist(LocationUtils.BAIDU_MAP_PACKAGE)) {
                if (mapClickListener != null) {
                    dialog.dismiss()
                    mapClickListener.onMapClick(LocationUtils.MAP_BAIDU)
                }
            } else {
                AppUtils.toast("百度地图未安装")
            }
        }
        tv_tecent_map.setOnClickListener { v: View? ->
            if (AppUtils.checkMapAppsIsExist(LocationUtils.TECENT_MAP_PACKAGE)) {
                if (mapClickListener != null) {
                    dialog.dismiss()
                    mapClickListener.onMapClick(LocationUtils.MAP_TECENT)
                }
            } else {
                AppUtils.toast("腾讯地图未安装")
            }
        }
        dialog_cancel.setOnClickListener { v: View? ->
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
        mDisposable = Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewFadaOut(loading)
                    if (AppUtils.checkMapAppsIsExist(LocationUtils.TECENT_MAP_PACKAGE)) {
                        viewFadaIn(tv_tecent_map)
                    }
                    if (AppUtils.checkMapAppsIsExist(LocationUtils.BAIDU_MAP_PACKAGE)) {
                        viewFadaIn(tv_baidu_map)
                    }
                    if (AppUtils.checkMapAppsIsExist(LocationUtils.AMAP_MAP_PACKAGE)) {
                        viewFadaIn(tv_amap_map)
                    }
                    if (tv_tecent_map.visibility == View.GONE && tv_baidu_map.visibility == View.GONE && tv_amap_map.visibility == View.GONE) {
                        viewFadaIn(empty)
                    }
                }
        dialog.setOnDismissListener {
            mDisposable?.apply {
                mDisposable!!.dispose()
            }
        }
        dialog.show()
    }

    private fun viewFadaIn(view:View){
        view.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                    .alpha(1f)
                    .setDuration(shortAnimationDuration.toLong())
                    .setListener(null)
        }
    }

    private fun viewFadaOut(view:View){
        view.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = View.GONE
                    }
                })
    }

    interface OnMapClickListener : CommonBottomDialog.OnDialogClickListener {
        fun onMapClick(@MapType map: Int)
    }
}