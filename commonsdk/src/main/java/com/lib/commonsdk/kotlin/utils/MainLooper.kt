package com.lib.commonsdk.kotlin.utils

import android.os.Handler
import android.os.Looper

object MainLooper : Handler(Looper.getMainLooper()) {
    fun runOnUiThread(runnable: Runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run()
        } else {
            this.post(runnable)
        }
    }
}