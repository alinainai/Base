package com.gas.app.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;

import com.airbnb.lottie.LottieAnimationView;
import com.base.baseui.view.TitleView;

import java.util.Locale;

public class AppMoudleUtil {

    public static boolean isChineseMainLand() {
        boolean isCH = false;
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals(SysConfig.CHINESE_LANG) && locale.getCountry().equals(SysConfig.CHINA)) {
            isCH = true;
        }
        return isCH;
    }

    public static boolean isChineseTradition() {
        boolean isCH = false;
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals(SysConfig.CHINESE_LANG) && !locale.getCountry().equals(SysConfig.CHINA)) {
            isCH = true;
        }
        return isCH;
    }

    public static boolean isChineseLanguage() {
        boolean isCH = false;
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals(SysConfig.CHINESE_LANG)) {
            isCH = true;
        }
        return isCH;
    }

    public static void setUrlTitle(TitleView titleView,String title){


        if (!TextUtils.isEmpty(title)) {
            if (title.length() >= 7) {
                title = title.substring(0, 7) + "...";
            }
            titleView.setTitleText(title);
        }else {
            titleView.setTitleText("Title");
        }

    }

    public static void removeWebView(WebView webView){

        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再destory()
            try {
                ViewParent parent = webView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(webView);
                }
                // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                webView.clearHistory();
                webView.getSettings().setJavaScriptEnabled(false);
                webView.removeAllViews();
                webView.destroy();
                webView = null;
            } catch (Exception ex) {

            }
        }
    }

    public static void copyData(Context context,String copyData){

        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", copyData);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    public static void stopLottieAnimation(LottieAnimationView view){

        if(view!=null){
            if(view.isAnimating())
                view.cancelAnimation();
            view.setProgress(0);
        }

    }
    public static void startLottieAnimation(LottieAnimationView view){
        if(view!=null){
            if(!view.isAnimating())
                view.postDelayed(view::playAnimation,150);
        }
    }

}
