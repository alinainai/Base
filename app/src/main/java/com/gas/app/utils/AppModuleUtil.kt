package com.gas.app.utils

import android.app.ActivityManager
import android.content.Context
import android.text.TextUtils
import android.view.ViewGroup
import android.webkit.WebView
import com.airbnb.lottie.LottieAnimationView
import com.base.baseui.view.TitleView
import java.text.DecimalFormat


/**
 * @return null may be returned if the specified process not found
 */
fun getProcessName(cxt: Context, pid: Int): String? {
    val am = cxt.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningApps = am.runningAppProcesses ?: return null
    for (procInfo in runningApps) {
        if (procInfo.pid == pid) {
            return procInfo.processName
        }
    }
    return null
}

object AppModuleUtil {
    @JvmStatic
    fun setUrlTitle(titleView: TitleView, title: String) {
        var title = title
        if (!TextUtils.isEmpty(title)) {
            if (title.length >= 7) {
                title = title.substring(0, 7) + "..."
            }
            titleView.titleText = title
        } else {
            titleView.titleText = "Title"
        }
    }

    @JvmStatic
    fun removeWebView(webView: WebView?) {
        var webView = webView
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再destory()
            try {
                val parent = webView.parent
                if (parent != null) {
                    (parent as ViewGroup).removeView(webView)
                }
                // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                webView.clearHistory()
                webView.settings.javaScriptEnabled = false
                webView.removeAllViews()
                webView.destroy()
                webView = null
            } catch (ex: Exception) {
            }
        }
    }



    @JvmStatic
    fun stopLottieAnimation(view: LottieAnimationView?) {
        if (view != null) {
            if (view.isAnimating) view.cancelAnimation()
            view.progress = 0f
        }
    }

    @JvmStatic
    fun startLottieAnimation(view: LottieAnimationView?) {
        if (view != null) {
            if (!view.isAnimating) view.postDelayed(Runnable { view.playAnimation() }, 150)
        }
    }

    fun formatMemorySizeB(size: Long): String? {
        //获取到的size为：1705230
        val GB = 1024 * 1024 * 1024 //定义GB的计算常量
        val MB = 1024 * 1024 //定义MB的计算常量
        val KB = 1024 //定义KB的计算常量
        val df = DecimalFormat("0.00") //格式化小数
        return when {
            size / GB >= 1 -> {
                //如果当前Byte的值大于等于1GB
                "${df.format(size / GB.toFloat())}GB"
            }
            size / MB >= 1 -> {
                //如果当前Byte的值大于等于1MB
                "${df.format(size / MB.toFloat())}MB"
            }
            size / KB >= 1 -> {
                //如果当前Byte的值大于等于1KB
                "${df.format(size / KB.toFloat())}KB"
            }
            else -> {
                size.toString() + "B"
            }
        }

    }



}