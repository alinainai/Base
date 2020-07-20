package com.base.baseui.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

/**
 * 自动获取系统状态栏高度的 StatusLine
 */
class StatusLine @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    private var mStatusBarHeight = 0
    private fun init() {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (resourceId > 0) {
                mStatusBarHeight = resources.getDimensionPixelSize(resourceId)
            }
        } else {
            mStatusBarHeight = 0
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec),
                mStatusBarHeight)
    }

    init {
        init()
    }
}