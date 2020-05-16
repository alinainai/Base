package com.lib.commonsdk.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.lib.commonsdk.constants.Constants;
import com.lib.commonsdk.utils.sp.SPStaticUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.UUID;


public class AppUtils {


    private static Application mApplication;

    public static void init(Application application) {
        mApplication = application;
    }

    public static Application getApp() {
        return mApplication == null ? getApplicationByReflect() : mApplication;
    }

    public static Resources getResources() {
        return getApp().getResources();
    }

    public static String getString(@StringRes int sId, Object... args) {
        return getApp().getResources().getString(sId, args);
    }

    public static Drawable getDrawable(@DrawableRes int sId) {
        return getApp().getResources().getDrawable(sId);
    }

    public static void toast(CharSequence text) {
        Toast.makeText(getApp().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static boolean checkMapAppsIsExist(String packagename) {
        return Utils.checkMapAppsIsExist(getApp(), packagename);
    }

    /**
     * Return the application's version name.
     *
     * @return the application's version name
     */
    public static String getAppVersionName() {
        return getAppVersionName(getApp().getPackageName());
    }

    /**
     * Return the application's version name.
     *
     * @param packageName The name of the package.
     * @return the application's version name
     */
    public static String getAppVersionName(final String packageName) {

        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's version code.
     *
     * @return the application's version code
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(getApp().getPackageName());
    }

    /**
     * Return the application's version code.
     *
     * @param packageName The name of the package.
     * @return the application's version code
     */
    public static int getAppVersionCode(final String packageName) {

        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }


    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    /**
     * 获取唯一标识符
     *
     * @return APP_GUID
     */
    public static String getAppGUID() {
        String id = SPStaticUtils.getString("APP_GUID", "");
        if (TextUtils.isEmpty(id)) {
            id = UUID.randomUUID().toString();
            SPStaticUtils.put("APP_GUID", id);
        }
        return id;
    }

    /**
     * 是否是简体中文
     * @return
     */
    public static boolean isSimplifiedChinese() {
        boolean isCH = false;
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals(Constants.CHINESE_LANG) && locale.getCountry().equals(Constants.CHINA)) {
            isCH = true;
        }
        return isCH;
    }

}
