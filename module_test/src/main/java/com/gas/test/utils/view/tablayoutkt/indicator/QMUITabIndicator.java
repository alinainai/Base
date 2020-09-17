package com.gas.test.utils.view.tablayoutkt.indicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

public class QMUITabIndicator {


    private int mIndicatorHeight;
    private boolean mIndicatorTop = false;
    private @Nullable Drawable mIndicatorDrawable;
    private boolean mIsIndicatorWidthFollowContent = true;
    private Rect mIndicatorRect = null;
    private Paint mIndicatorPaint = null;

    private int mFixedColorAttr = 0;
    private boolean mShouldReGetFixedColor = true;
    private int mFixedColor = 0;

    public QMUITabIndicator(int indicatorHeight, boolean indicatorTop,
                            boolean isIndicatorWidthFollowContent){
        this(indicatorHeight, indicatorTop, isIndicatorWidthFollowContent, 0);
    }

    public QMUITabIndicator(int indicatorHeight, boolean indicatorTop,
                            boolean isIndicatorWidthFollowContent, int fixedColorAttr) {
        mIndicatorHeight = indicatorHeight;
        mIndicatorTop = indicatorTop;
        mIsIndicatorWidthFollowContent = isIndicatorWidthFollowContent;
        mFixedColorAttr = fixedColorAttr;
    }

    public QMUITabIndicator(@NonNull Drawable drawable, boolean indicatorTop,
                            boolean isIndicatorWidthFollowContent){
        this(drawable, indicatorTop, isIndicatorWidthFollowContent, 0);
    }

    public QMUITabIndicator(@NonNull Drawable drawable, boolean indicatorTop,
                            boolean isIndicatorWidthFollowContent, int fixedColorAttr) {
        mIndicatorDrawable = drawable;
        mIndicatorHeight = drawable.getIntrinsicHeight();
        mIndicatorTop = indicatorTop;
        mIsIndicatorWidthFollowContent = isIndicatorWidthFollowContent;
        mFixedColorAttr = fixedColorAttr;
    }

    public boolean isIndicatorWidthFollowContent() {
        return mIsIndicatorWidthFollowContent;
    }

    public boolean isIndicatorTop() {
        return mIndicatorTop;
    }

    @Deprecated
    protected void updateInfo(int left, int width, int color){
        if (mIndicatorRect == null) {
            mIndicatorRect = new Rect(left, 0,
                    left + width, 0);
        } else {
            mIndicatorRect.left = left;
            mIndicatorRect.right = left + width;
        }
        if(mFixedColorAttr == 0){
            updateColor(color);
        }
    }

    public void updateInfo(int left, int width, int color, float offsetPercent) {
        updateInfo(left, width, color);
    }

    protected void updateColor(int color){
        if (mIndicatorDrawable != null) {
            DrawableCompat.setTint(mIndicatorDrawable, color);
        } else {
            if (mIndicatorPaint == null) {
                mIndicatorPaint = new Paint();
                mIndicatorPaint.setStyle(Paint.Style.FILL);
            }
            mIndicatorPaint.setColor(color);
        }
    }

    public void draw(@NonNull Canvas canvas, int viewTop, int viewBottom) {
        if (mIndicatorRect != null) {
            if(mFixedColorAttr != 0 && mShouldReGetFixedColor){
                mShouldReGetFixedColor = false;
                mFixedColor = mFixedColorAttr;
                updateColor(mFixedColor);
            }
            if (mIndicatorTop) {
                mIndicatorRect.top = viewTop;
                mIndicatorRect.bottom = mIndicatorRect.top + mIndicatorHeight;
            } else {
                mIndicatorRect.bottom = viewBottom;
                mIndicatorRect.top = mIndicatorRect.bottom - mIndicatorHeight;
            }
            if (mIndicatorDrawable != null) {
                mIndicatorDrawable.setBounds(mIndicatorRect);
                mIndicatorDrawable.draw(canvas);
            } else {
                canvas.drawRect(mIndicatorRect, mIndicatorPaint);
            }
        }
    }

}