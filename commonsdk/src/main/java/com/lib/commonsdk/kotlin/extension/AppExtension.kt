package com.lib.commonsdk.kotlin.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import com.lib.commonsdk.constants.Constants
import com.lib.commonsdk.utils.AppUtils
import com.lib.commonsdk.utils.Utils
import timber.log.Timber
import java.util.*

fun error(str:String){
    Timber.e(str)
}

fun copyToClipboard(str: String, context: Context = AppUtils.getApp().applicationContext): Boolean {
    return try {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText("Label", str)
        cm.setPrimaryClip(mClipData)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun toast(str: String, ctx: Context = AppUtils.getApp().applicationContext) {
    Toast.makeText(ctx.applicationContext, str, Toast.LENGTH_SHORT).show()
}

//是否安装app
fun checkAppInstall(pkgName: String?): Boolean {
    return Utils.checkMapAppsIsExist(AppUtils.getApp(), pkgName)
}

//是否是简体中文
fun isMainLandLanguage(): Boolean {
    var isCH = false
    val locale = Locale.getDefault()
    if (locale.language == Constants.CHINESE_LANG && locale.country == Constants.CHINA) {
        isCH = true
    }
    return isCH
}


fun getAppVersionName(packageName: String = AppUtils.getApp().packageName): String? {
    return try {
        val pm = AppUtils.getApp().packageManager
        val pi = pm.getPackageInfo(packageName, 0)
        pi?.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }
}

fun getAppVersionCode(packageName: String = AppUtils.getApp().packageName): Long {
    return try {
        val pm = AppUtils.getApp().packageManager
        val pi = pm.getPackageInfo(packageName, 0)
        pi?.versionCode?.toLong() ?: -1
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        -1
    }
}


/**
 * 给一个Drawable设置大小, 用这个方法设置之后Drawable是正方形的。
 */
var <T : Drawable> T.boundSize: Int
    get() = bounds.width()
    set(v) {
        setBounds(0, 0, v, v)
    }

/**
 * 发现好多地方需要 tryCatch 以保证代码安全性
 * 但是我们又不关心它的运行结果，所以单独写了一个方法
 * 将需要运行的代码块用 tryCatch 包裹起来
 */
fun <T : Any> T.runInTryCatch(block: () -> Unit) {
    try {
        block()
    } catch (e: Throwable) {
        e.printStackTrace()
    }

}