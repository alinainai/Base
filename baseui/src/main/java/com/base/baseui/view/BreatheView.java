package com.base.baseui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.base.baseui.R;

/**
 * author：JianFeng
 * date：17/4/26:下午2:30
 * description：呼吸灯效果的View
 * 原理:在指定时间内,递减透明色,递增半径
 */

public class BreatheView extends View implements ValueAnimator.AnimatorUpdateListener {

    private static final String TAG = "BreatheView";
    /**
     * 扩散圆圈颜色
     */
    private int mColor = getResources().getColor(R.color.public_white);
    /**
     * 圆圈中心颜色
     */
    private int mCoreColor = getResources().getColor(R.color.public_bg_default);

    /**
     * 中心圆半径
     */
    private float mCoreRadius = 30f;

    /**
     * 整个绘制面的最大宽度
     */
    private float mMaxWidth = 40f;

    /***
     * 初始透明度
     */
    private int color = 255;

    /**
     * 是否正在扩散中
     */
    private boolean mIsDiffuse = false;
    private Paint mPaint;
    private float mFraction;//变化因子
    private ValueAnimator mAnimator;
    private static final long HEART_BEAT_RATE = 2000;//每隔多久闪烁一次
    private Handler mHandler;
    //圆圈中心坐标
    private float circleX ;
    private float circleY ;


    public BreatheView(Context context) {
        this(context, null);
    }

    public BreatheView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BreatheView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mAnimator = ValueAnimator.ofFloat(0, 1f).setDuration(2000);
        mAnimator.addUpdateListener(this);
        if (null == mHandler) {
            mHandler = new Handler();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        circleX = w /2 ;
        circleY = h /2 ;
    }

    /***
     * 设置中心圆半径
     *
     * @param radius
     */
    public void setCoreRadius(float radius) {
        mCoreRadius = radius;
    }

    /***
     * 设置闪烁圆圈最大半径
     *
     * @param width
     */
    public void setMaxWidth(float width) {
        mMaxWidth = width;
    }

    public void setCoordinate(float x, float y) {
        circleX = x;
        circleY = y;
    }


    @Override
    public void invalidate() {
        if (hasWindowFocus()) {
            super.invalidate();
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            invalidate();
        }
    }

    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            start();
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    public void onConnected() {
        mHandler.removeCallbacks(heartBeatRunnable);
        mHandler.post(heartBeatRunnable);
    }

    public void onDestroy() {
        mIsDiffuse = false;
        mHandler.removeCallbacks(heartBeatRunnable);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsDiffuse) {
            //绘制扩散圆
            mPaint.setColor(mColor);
            mPaint.setAlpha((int) (color - color * mFraction));
            canvas.drawCircle(circleX, circleY, mCoreRadius + mMaxWidth * mFraction, mPaint);
            // 绘制中心圆
            mPaint.setAlpha(255);
            mPaint.setColor(mCoreColor);
            canvas.drawCircle(circleX, circleY, mCoreRadius, mPaint);

        }
        invalidate();

    }

    public void start() {
        mIsDiffuse = true;
        mAnimator.start();
        Log.e(TAG, "闪烁开始");
        invalidate();
    }


    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mFraction = (float) valueAnimator.getAnimatedValue();
        Log.e(TAG, "mFraction:" + mFraction);
    }
}

