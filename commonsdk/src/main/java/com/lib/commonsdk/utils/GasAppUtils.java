package com.lib.commonsdk.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import java.lang.reflect.InvocationTargetException;


public class GasAppUtils {


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

    public static boolean checkMapAppsIsExist( String packagename) {
      return   Utils.checkMapAppsIsExist(getApp(),packagename);
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


}
