package com.gas.app.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * ToastUtils 工具类
 */
public class ToastUtil {

    private ToastUtil() {
        throw new AssertionError();
    }

    public static void show(Context context, CharSequence text) {
       Toast.makeText(context.getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }



}
