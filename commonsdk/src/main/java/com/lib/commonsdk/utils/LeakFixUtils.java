package com.lib.commonsdk.utils;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

public class LeakFixUtils {


    public static void fixSoftInputLeaks(final Window window) {
        InputMethodManager imm =
                (InputMethodManager) AppUtils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        String[] leakViews = new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"};
        for (String leakView : leakViews) {
            try {
                Field leakViewField = InputMethodManager.class.getDeclaredField(leakView);
                if (!leakViewField.isAccessible()) {
                    leakViewField.setAccessible(true);
                }
                Object obj = leakViewField.get(imm);
                if (!(obj instanceof View)) continue;
                View view = (View) obj;
                if (view.getRootView() == window.getDecorView().getRootView()) {
                    leakViewField.set(imm, null);
                }
            } catch (Throwable ignore) {/**/}
        }
    }
}
