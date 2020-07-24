package com.lib.commonsdk.kotlin.extension.app

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lib.commonsdk.constants.Constants
import com.lib.commonsdk.kotlin.utils.AppUtils
import com.lib.commonsdk.utils.sp.SPStaticUtils
import timber.log.Timber
import java.io.File
import java.util.*

fun error(str: String) {
    Timber.e(str)
}

val app: Context = AppUtils.app.applicationContext

fun getResources(): Resources {
    return app.resources
}


fun getString(@StringRes sId: Int, vararg args: Any?): String {
    return app.resources.getString(sId, *args)
}

fun getDrawable(@DrawableRes sId: Int): Drawable {
    return app.resources.getDrawable(sId)
}

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

//是否是简体中文
fun isMainLandLanguage(): Boolean {
    var isCH = false
    val locale = Locale.getDefault()
    if (locale.language == Constants.CHINESE_LANG && locale.country == Constants.CHINA) {
        isCH = true
    }
    return isCH
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
    return id
}
