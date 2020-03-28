package com.lib.commonsdk.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import java.lang.reflect.InvocationTargetException;

public class GasAppUtil {

    private static Application mApplication;

    public static void init(Application application){
        mApplication=application;
    }

    public static Application getApplication(){
        return mApplication==null?getApplicationByReflect():mApplication;
    }

    public static Resources getResources(){
        return getApplication().getResources();
    }

    public static String getString(@StringRes int sId){
        return getApplication().getResources().getString(sId);
    }

    public static Drawable getDrawable(@DrawableRes int sId){
        return getApplication().getResources().getDrawable(sId);
    }

    public static void toast(CharSequence text) {
        Toast.makeText(getApplication().getApplicationContext(),text, Toast.LENGTH_SHORT).show();
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
