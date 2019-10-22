package com.base.baseui.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * 自动获取系统状态栏高度的 StatusLine
 */
public class StatusLine extends LinearLayout {

    private int mStatusBarHeight;
    public StatusLine(Context context) {
        this(context, null);
    }

    public StatusLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(resourceId>0) {
                mStatusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }
        }else{
            mStatusBarHeight = 0;
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                    mStatusBarHeight);
    }

}
