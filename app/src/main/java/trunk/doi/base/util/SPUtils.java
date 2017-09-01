package trunk.doi.base.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 */
public class SPUtils {

    public static  final String APP_NAME ="APP_NAME";


    public static void saveString(Context ctx, String name, String value) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(APP_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static void saveString(Context ctx, String name, String value, String fileName) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(fileName, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static String loadString(Context ctx, String key) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(APP_NAME, 0);
        String result = sp.getString(key, "");
        return result;
    }

    public static String loadString(Context ctx, String key, String fileName) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(fileName, 0);
        String result = sp.getString(key, "");
        return result;
    }

    public static void saveBoolean(Context ctx, String name, boolean value) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(APP_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static void saveBoolean(Context ctx, String name, boolean value, String fileName) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(fileName, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static boolean loadBoolean(Context ctx, String name, boolean defaultvalue) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(APP_NAME, 0);
        boolean result = sp.getBoolean(name, defaultvalue);
        return result;
    }

    public static boolean loadBoolean(Context ctx, String name, boolean defaultvalue, String fileName) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(fileName, 0);
        boolean result = sp.getBoolean(name, defaultvalue);
        return result;
    }
    public static void saveLong(Context ctx, String name, long value) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(APP_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(name, value);
        editor.commit();
    }

    public static void saveLong(Context ctx, String name, long value, String fileName) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(fileName, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(name, value);
        editor.commit();
    }

    public static long loadLong(Context ctx, String name, long defaultvalue) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(APP_NAME, 0);
        long result = sp.getLong(name, defaultvalue);
        return result;
    }

    public static long loadLong(Context ctx, String name, long defaultvalue, String fileName) {
        SharedPreferences sp = ctx.getApplicationContext().getSharedPreferences(fileName, 0);
        long result = sp.getLong(name, defaultvalue);
        return result;
    }

    public static void clearValue(Context context, String key) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(APP_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }
    public static void clearValue(Context context, String key, String fileName) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(fileName, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void clearAll(Context context) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(APP_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
