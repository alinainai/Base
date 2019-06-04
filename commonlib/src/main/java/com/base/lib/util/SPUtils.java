package com.base.lib.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Objects;

/**
 * 保存类
 */
@SuppressWarnings({"unused", "WeakerAccess", "JavaDoc"})
public class SPUtils {

    private static final String APP_NAME = "GAS_SHARED_PREFER";

    private SPUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static void saveString(Context ctx, @NonNull String name, @NonNull String value) {
        saveString(ctx, name, value, APP_NAME);
    }

    public static void saveString(Context ctx, String name, String value, String fileName) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(fileName, 0);
        SharedPreferences.Editor editor = sp.edit();
        if (!TextUtils.isEmpty(value))
            value = EncryptUtil.encrptValue(value);
        editor.putString(EncryptUtil.encrptKey(name), value);
        editor.apply();
    }

    public static String loadString(Context ctx, String key) {
        return loadString(ctx, key, APP_NAME);
    }

    public static String loadString(Context ctx, String key, String fileName) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(fileName, 0);
        String result = sp.getString(EncryptUtil.encrptKey(key), "");
        if (!TextUtils.isEmpty(result)) {
            return EncryptUtil.decddeValue(Objects.requireNonNull(result));
        }
        return result;
    }

    public static void saveBoolean(Context ctx, String name, boolean value) {
        saveBoolean(ctx, name, value, APP_NAME);
    }

    public static void saveBoolean(Context ctx, String name, boolean value, String fileName) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(fileName, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    public static boolean loadBoolean(Context ctx, String name, boolean defaultvalue) {
        return loadBoolean(ctx, name, defaultvalue, APP_NAME);
    }

    public static boolean loadBoolean(Context ctx, String name, boolean defaultvalue, String fileName) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(fileName, 0);
        return sp.getBoolean(name, defaultvalue);
    }

    public static void saveLong(Context ctx, String name, long value) {
        saveLong(ctx, name, value, APP_NAME);
    }

    public static void saveLong(Context ctx, String name, long value, String fileName) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(fileName, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(name, value);
        editor.apply();
    }

    public static long loadLong(Context ctx, String name, long defaultvalue) {
        return loadLong(ctx, name, defaultvalue, APP_NAME);
    }

    public static long loadLong(Context ctx, String name, long defaultvalue, String fileName) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(fileName, 0);
        return sp.getLong(name, defaultvalue);
    }

    /**
     * 默认清空 APP_NAME 下面的  key 数据。
     *
     * @param context
     * @param key
     */
    public static void clearValue(Context context, String key) {
        clearValue(context, key, APP_NAME);
    }

    public static void clearValue(Context context, String key, String fileName) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(fileName, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清空 APP_NAME 下所有的
     *
     * @param context
     */
    public static void clearAll(Context context) {
        clearAll(context, APP_NAME);
    }

    /**
     * 清空 APP_NAME 下所有的
     *
     * @param context
     */
    public static void clearAll(Context context, String fileName) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(fileName, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }


}
