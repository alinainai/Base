package com.base.lib.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.base.lib.R;

public class StatusBarManager {

    private StatusBarManager() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     *  全透明状态栏
     * @param activity Activity
     */
    public static void fullTransStatusBar(Activity activity){

        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明状态栏

            View decorView = activity.getWindow().getDecorView();
            //拓展布局到状态栏后面 | 稳定的布局，不会随系统栏的隐藏、显示而变化
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            activity.getWindow().setStatusBarColor(activity.getApplication().getResources().getColor(R.color.public_trans));

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏

            WindowManager.LayoutParams localLayoutParams =activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

        }


    }
}
