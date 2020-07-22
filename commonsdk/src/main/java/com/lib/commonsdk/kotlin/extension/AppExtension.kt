package com.lib.commonsdk.kotlin.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.lib.commonsdk.utils.AppUtils

fun String.toClipboard(context: Context): Boolean {
    return try {
        //获取剪贴板管理器：
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
        val mClipData = ClipData.newPlainText("Label", this)
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun String.toClipboard(): Boolean {
    return toClipboard(AppUtils.getApp().applicationContext)
}