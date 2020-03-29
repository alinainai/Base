package com.lib.commonsdk.utils;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FastClickUtils {

    private static long lastClickTime;

    //防止双点击
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) return true;
        lastClickTime = time;
        return false;
    }
}
