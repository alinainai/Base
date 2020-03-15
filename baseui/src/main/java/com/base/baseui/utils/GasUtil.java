package com.base.baseui.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.base.baseui.view.TitleView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class GasUtil {

    private static Application mApplication;

    public static void init(Application application){
        mApplication=application;
    }

    public static Application getApplication(){
        return mApplication==null?getApplicationByReflect():mApplication;
    }

    public static Resources getResources(){
        return getApplication().getResources();
    }

    public static String getString(@StringRes int sId){
        return getApplication().getResources().getString(sId);
    }

    public static Drawable getDrawable(@DrawableRes int sId){
        return getApplication().getResources().getDrawable(sId);
    }

    public static void toast(CharSequence text) {
        Toast.makeText(getApplication().getApplicationContext(),text, Toast.LENGTH_SHORT).show();
    }

    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }


    private static final String USER_ID = "userId";

    public static final boolean IS_SHOW_LOG = true;

    public static void setUrlTitle(TitleView titleView, String title) {


        if (!TextUtils.isEmpty(title)) {
            if (title.length() >= 7) {
                title = title.substring(0, 7) + "...";
            }
            titleView.setTitleText(title);
        }

    }

    public static void removeWebView(WebView webView) {

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

    public static void copyData(Context context, String copyData) {

        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", copyData);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    /**
     * 设置 tablayout 宽度
     *
     * @param tabLayout
     * @param margin
     */
    public static void setIndicatorWidth(@NonNull final TabLayout tabLayout, final int margin) {
        tabLayout.post(() -> {

            try {

                // 拿到tabLayout的slidingTabIndicator属性

                int marginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, Resources.getSystem().getDisplayMetrics());
                Field slidingTabIndicatorField = tabLayout.getClass().getDeclaredField("slidingTabIndicator");
                slidingTabIndicatorField.setAccessible(true);

                LinearLayout mTabStrip = (LinearLayout) slidingTabIndicatorField.get(tabLayout);

                for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                    View tabView = mTabStrip.getChildAt(i);

                    //拿到tabView的mTextView属性

                    Field textViewField = tabView.getClass().getDeclaredField("textView");

                    textViewField.setAccessible(true);

                    TextView mTextView = (TextView) textViewField.get(tabView);

                    tabView.setPadding(0, 0, 0, 0);

                    // 因为想要的效果是字多宽线就多宽，所以测量mTextView的宽度

                    int width = mTextView.getWidth();

                    if (width == 0) {
                        mTextView.measure(0, 0);
                        width = mTextView.getMeasuredWidth();
                    }

                    // 设置tab左右间距,注意这里不能使用Padding,因为源码中线的宽度是根据tabView的宽度来设置的

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();

                    params.width = width;
                    params.leftMargin = marginPx;
                    params.rightMargin = marginPx;

                    tabView.setLayoutParams(params);
                    tabView.invalidate();

                }

            } catch (NoSuchFieldException | IllegalAccessException e) {

                e.printStackTrace();

            }

        });

    }

   
    /**
     * String 保留小数
     */
    public static String strToDoubleBit(String str) {
        double data;
        try {
            data = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return str;
        }
        DecimalFormat decimalFormat = new DecimalFormat(",###.##");
        return decimalFormat.format(data);
    }

    /**
     * 如果小数点后为零则显示整数否则保留两位小数
     */

    public static String formatDouble(double d) {
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        double num = bg.doubleValue();
        if (Math.round(num) - num == 0) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }


}
