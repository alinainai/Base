package com.gas.zhihu.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.IntDef;

import com.base.baseui.dialog.CommonBottomDialog;
import com.gas.zhihu.R;
import com.lib.commonsdk.utils.GasAppUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static com.gas.zhihu.utils.LocationUtils.*;

public class SelectMapDialog {


    private Disposable mDisposable;

    public void show(Context context, OnMapClickListener mapClickListener) {


        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.zhihu_dialog_select_map, null);
        View loading = view.findViewById(R.id.loading);
        View empty = view.findViewById(R.id.empty);
        View dialog_cancel = view.findViewById(R.id.dialog_cancel);
        View tv_amap_map = view.findViewById(R.id.tv_amap_map);
        View tv_baidu_map = view.findViewById(R.id.tv_baidu_map);
        View tv_tecent_map = view.findViewById(R.id.tv_tecent_map);


        CommonBottomDialog dialog = new CommonBottomDialog
                .Builder(context)
                .setDialogClickListener(mapClickListener)
                .setCancelable(true)
                .setCustomView(view)
                .create();

        tv_amap_map.setOnClickListener(v -> {

            if (GasAppUtils.checkMapAppsIsExist(AMAP_MAP_PACKAGE)) {
                if (mapClickListener != null) {
                    dialog.dismiss();
                    mapClickListener.onMapClick(MAP_AMAP);
                }
            } else {
                GasAppUtils.toast("高德地图未安装");
            }


        });
        tv_baidu_map.setOnClickListener(v -> {

            if (GasAppUtils.checkMapAppsIsExist(BAIDU_MAP_PACKAGE)) {
                if (mapClickListener != null) {
                    dialog.dismiss();
                    mapClickListener.onMapClick(MAP_BAIDU);
                }
            } else {
                GasAppUtils.toast("百度地图未安装");
            }

        });
        tv_tecent_map.setOnClickListener(v -> {
            if (GasAppUtils.checkMapAppsIsExist(TECENT_MAP_PACKAGE)) {
                if (mapClickListener != null) {
                    dialog.dismiss();
                    mapClickListener.onMapClick(MAP_TECENT);
                }
            } else {
                GasAppUtils.toast("腾讯地图未安装");
            }

        });


        dialog_cancel.setOnClickListener(v -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        });

        mDisposable = Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {

                    loading.setVisibility(View.GONE);

                    if (GasAppUtils.checkMapAppsIsExist(TECENT_MAP_PACKAGE)) {
                        tv_tecent_map.setVisibility(View.VISIBLE);
                    }
                    if (GasAppUtils.checkMapAppsIsExist(BAIDU_MAP_PACKAGE)) {
                        tv_baidu_map.setVisibility(View.VISIBLE);
                    }
                    if (GasAppUtils.checkMapAppsIsExist(AMAP_MAP_PACKAGE)) {
                        tv_amap_map.setVisibility(View.VISIBLE);
                    }

                    if (tv_tecent_map.getVisibility() == View.GONE
                            && tv_baidu_map.getVisibility() == View.GONE
                            && tv_amap_map.getVisibility() == View.GONE) {
                        empty.setVisibility(View.VISIBLE);
                    }

                    mDisposable.dispose();

                });
        dialog.show();


    }


    public interface OnMapClickListener extends CommonBottomDialog.onDialogClickListener {

        void onMapClick(@MapType int map);

    }


}
