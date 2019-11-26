package com.base.baseui.utils;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;

public class GasUtil {

    public static void setIndicator(TabLayout tabLayout, int left, int right) {

        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int l = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, left, Resources.getSystem().getDisplayMetrics());
        int r = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, right, Resources.getSystem().getDisplayMetrics());

        if (llTab != null) {
            for (int i = 0; i < llTab.getChildCount(); i++) {
                View child = llTab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = l;
                params.rightMargin = r;
                child.setLayoutParams(params);
                child.invalidate();
            }
        }
    }


}
