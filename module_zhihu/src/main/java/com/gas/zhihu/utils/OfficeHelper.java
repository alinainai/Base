package com.gas.zhihu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.gas.zhihu.ui.office.OfficeActivity;
import com.tencent.smtt.sdk.QbSdk;

public class OfficeHelper {

    public static final String FILEPATH = "FILE_PATH";

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public static void init(@NonNull Context context) {
        init(context, null);
    }

    /**
     * 初始化
     *
     * @param context  上下文
     * @param callback 初始化监听回调
     */
    public static void init(@NonNull Context context, QbSdk.PreInitCallback callback) {
        QbSdk.setDownloadWithoutWifi(true);
        if (callback == null) {
            callback = new QbSdk.PreInitCallback() {

                @Override
                public void onCoreInitFinished() {

                }

                @Override
                public void onViewInitFinished(boolean b) {
                    Log.d("TBS", b ? "SUCCESS" : "FAILURE");
                }
            };
        }
        QbSdk.initX5Environment(context, callback);
    }


    public static void open(@NonNull Activity activity,  String filePath, int requestCode) {
        Intent intent = new Intent(activity, OfficeActivity.class);
        intent.putExtra(FILEPATH, filePath);
        activity.startActivityForResult(intent, requestCode);
    }
}