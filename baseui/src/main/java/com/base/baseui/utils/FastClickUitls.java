package com.base.baseui.utils;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FastClickUitls {

    private static long lastClickTime;

    //防止双点击
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) return true;
        lastClickTime = time;
        return false;
    }
}
