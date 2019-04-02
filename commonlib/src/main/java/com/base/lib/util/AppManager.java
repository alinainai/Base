package com.base.lib.util;

import android.app.Activity;


/**
 * ================================================
 * 用于管理所有 {@link Activity}, 和在前台的 {@link Activity}
 * ================================================
 */
public final class AppManager {
    protected final String TAG = this.getClass().getSimpleName();

    private static volatile AppManager sAppManager;


    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (sAppManager == null) {
            synchronized (AppManager.class) {
                if (sAppManager == null) {
                    sAppManager = new AppManager();
                }
            }
        }
        return sAppManager;
    }


}
