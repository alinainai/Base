package com.base.baseui.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.base.baseui.view.TitleView;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;

public class ViewUtils {

    public static String getNoSpaceText(EditText et) {
        if(et==null)
            return "";
        return (et.getText() != null ? et.getText().toString().trim().replaceAll(" ", "") : "");
    }

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



}
