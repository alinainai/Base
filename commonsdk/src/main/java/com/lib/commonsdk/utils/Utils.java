/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lib.commonsdk.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;

import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * ================================================
 * Created by JessYan on 30/03/2018 17:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class Utils {


    public static String CHINESE_LANG = "zh";
    public static String CHINA = "CN";
    public static String TAIWAN = "TW";
    public static String HONGKONG = "HK";
    public static String MACAU = "MO";
    public static String ITALY = "IT";
    public static String ITALY_LANG = "it";
    public static String GERMANY = "DE";
    public static String GERMANY_LANG = "de";
    public static String KOREA_LANG = "ko";
    public static String ENGLISH_LANG = "en";
    public static String currSysLang;


    private Utils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 使用 {@link ARouter} 根据 {@code path} 跳转到对应的页面, 这个方法因为没有使用 {@link Activity}跳转
     * 所以 {@link ARouter} 会自动给 {@link android.content.Intent} 加上 Intent.FLAG_ACTIVITY_NEW_TASK
     * 如果不想自动加上这个 Flag 请使用 {@link ARouter#getInstance()#navigation(Context, String)} 并传入 {@link Activity}
     *
     * @param path {@code path}
     */
    public static void navigation(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    /**
     * 使用 {@link ARouter} 根据 {@code path} 跳转到对应的页面, 如果参数 {@code context} 传入的不是 {@link Activity}
     * {@link ARouter} 就会自动给 {@link android.content.Intent} 加上 Intent.FLAG_ACTIVITY_NEW_TASK
     * 如果不想自动加上这个 Flag 请使用 {@link Activity} 作为 {@code context} 传入
     *
     * @param context
     * @param path
     */
    public static void navigation(Context context, String path) {
        ARouter.getInstance().build(path).navigation(context);
    }

    public static void navigation(Context context, String path, NavCallback callback) {
        ARouter.getInstance().build(path).navigation(context, callback);
    }


    /**
     * String 保留小数
     */
    public static String strToDoubleBit(String str) {
        double data;
        try {
            data = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return str;
        }
        DecimalFormat decimalFormat = new DecimalFormat(",###.##");
        return decimalFormat.format(data);
    }

    /**
     * 如果小数点后为零则显示整数否则保留两位小数
     */

    public static String formatDouble(double d) {
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        double num = bg.doubleValue();
        if (Math.round(num) - num == 0) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }

    public static void copyData(Context context, String copyData) {

        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", copyData);
        // 将ClipData内容放到系统剪贴板里。
        if (cm != null) {
            cm.setPrimaryClip(mClipData);
        }

    }


    public static boolean isChinese() {
        boolean isCH = false;
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals(CHINESE_LANG) && locale.getCountry().equals(CHINA)) {
            isCH = true;
        }
        return isCH;
    }

    /**
     * 检测地图应用是否安装
     *
     * @param context
     * @param packagename
     * @return
     */
    public static boolean checkMapAppsIsExist(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getApplicationContext().getPackageManager().getPackageInfo(packagename, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    public static File getExternalFilesDir(Context context){
        return  getExternalFilesDir(context,null);
    }

    public static File getExternalFilesDir(Context context, String fileName){
        return context.getApplicationContext().getExternalFilesDir(fileName);
    }

}
