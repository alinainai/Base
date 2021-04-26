package com.lib.commonsdk.extension.app

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.lib.commonsdk.kotlin.utils.AppUtils
import com.lib.commonsdk.sp.SPStaticUtils
import java.io.File
import java.util.*

val app: Context = AppUtils.app.applicationContext

fun copyToClipboard(str: String, context: Context = app): Boolean {
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

@JvmOverloads
fun toast(str: String, ctx: Context = app) {
    Toast.makeText(ctx.applicationContext, str, Toast.LENGTH_SHORT).show()
}

//是否安装app
fun checkAppInstall(pkgName: String?): Boolean {
    var packageInfo: PackageInfo? = null
    try {
        pkgName?.let {
            packageInfo = app.packageManager.getPackageInfo(it, 0)
        }
    } catch (e: java.lang.Exception) {
        packageInfo = null
        e.printStackTrace()
    }
    return packageInfo != null
}

fun getAppVersionName(packageName: String = AppUtils.app.packageName): String? {
    return try {
        val pm = AppUtils.app.packageManager
        val pi = pm.getPackageInfo(packageName, 0)
        pi?.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }
}

fun getAppVersionCode(packageName: String = AppUtils.app.packageName): Long {
    return try {
        val pm = AppUtils.app.packageManager
        val pi = pm.getPackageInfo(packageName, 0)
        pi?.versionCode?.toLong() ?: -1
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        -1
    }
}

fun fixSoftInputLeaks(window: Window) {
    val imm = app.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            ?: return
    val leakViews = arrayOf("mLastSrvView", "mCurRootView", "mServedView", "mNextServedView")
    for (leakView in leakViews) {
        try {
            val leakViewField = InputMethodManager::class.java.getDeclaredField(leakView)
            if (!leakViewField.isAccessible) {
                leakViewField.isAccessible = true
            }
            val obj = leakViewField[imm] as? View ?: continue
            if (obj.rootView === window.decorView.rootView) {
                leakViewField[imm] = null
            }
        } catch (ignore: Throwable) { /**/
        }
    }
}

fun getExternalFilesDir(fileName: String? = null): File? {
    return app.getExternalFilesDir(fileName)
}

/**
 * 获取唯一标识符
 * @return APP_GUID
 */
fun getAppGUID(): String {
    var id = SPStaticUtils.getString("APP_GUID", "")
    if (TextUtils.isEmpty(id)) {
        id = UUID.randomUUID().toString()
        SPStaticUtils.put("APP_GUID", id)
    }
    return id!!
}

fun byte2FitMemorySize(byteNum: Long): String {
    return when {
        byteNum < 0 -> {
            "shouldn't be less than zero!"
        }
        byteNum < 1024 -> {
            String.format(Locale.getDefault(), "%.0fB", byteNum.toDouble())
        }
        byteNum < 1048576 -> {
            String.format(Locale.getDefault(), "%.0fKB", byteNum.toDouble() / 1024)
        }
        byteNum < 1073741824 -> {
            String.format(Locale.getDefault(), "%.0fMB", byteNum.toDouble() / 1048576)
        }
        else -> {
            String.format(Locale.getDefault(), "%.0fGB", byteNum.toDouble() / 1073741824)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun Context.getWiFiSsid(): String {
    var ssid = "unknown id"
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val mWifiManager = (applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
        val info = mWifiManager.connectionInfo
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            info.ssid
        } else {
            info.ssid.replace("\"", "")
        }
    } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {
        val connManager = (applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val networkInfo = connManager.activeNetworkInfo
        if (networkInfo!!.isConnected) {
            if (networkInfo.extraInfo != null) {
                return networkInfo.extraInfo.replace("\"", "")
            }
        }
    }
    return ssid
}
