package com.lib.commonsdk.kotlin

object FastClickCheck {
    private var lastClickTime: Long = 0

    //防止双点击
    @JvmStatic
    @get:Synchronized
    val isFastClick: Boolean
        get() {
            val time = System.currentTimeMillis()
            if (time - lastClickTime < 1000) return true
            lastClickTime = time
            return false
        }
}