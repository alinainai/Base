package com.base.baseui.view.verticalscroll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.base.baseui.R;


/**
 * ================================================
 * desc: 仿京东上线滚动布局
 * <p>
 * created by author ljx
 * date  2020/3/28
 * email 569932357@qq.com
 * <p>
 * ================================================
 */
public class VerticalScrollView extends LinearLayout {

    private static final int TIME_GAP = 4000;
    private static final int TIME_ANIMATION = 1000;
    private static final int DEFAULT_HEIGHT = 40;

    /**
     * 间隔时间
     */
    private int mGap;
    /**
     * 动画间隔时间
     */
    private int mAnimDuration;

    private float mTrueHeight;

    /**
     * 显示的view
     */
    private View mFirstView;
    private View mSecondView;
    /**
     * 播放的下标
     */
    private int mPosition;
    /**
     * 线程的标识
     */
    private boolean isStarted;


    private VerticalScrollAdapter mAdapter;

    public VerticalScrollView(Context context) {
        super(context);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        // 设置为垂直方向
        setOrientation(VERTICAL);
        // 获取自定义属性

        mTrueHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_HEIGHT, getResources().getDisplayMetrics());

        TypedArray array = getContext().obtainStyledAttributes(attrs,
                R.styleable.VerticalScrollView);

        mGap = array.getInteger(R.styleable.VerticalScrollView_verticalGap, TIME_GAP);
        mAnimDuration = array.getInteger(
                R.styleable.VerticalScrollView_verticalAnimDuration, TIME_ANIMATION);

        if (mGap <= mAnimDuration) {
            mGap = TIME_GAP;
            mAnimDuration = TIME_ANIMATION;
        }
        array.recycle();
    }

    /**
     * 设置数据
     */
    public <T>void setAdapter(VerticalScrollAdapter<T> adapter) {
        this.mAdapter = adapter;
        setupAdapter();
    }

    /**
     * 开启线程
     */
    public void start() {

        if (!isStarted && mAdapter != null && mAdapter.getCount() > 1) {
            isStarted = true;
            postDelayed(mRunnable, mGap);// 间隔mgap刷新一次UI
        }
    }

    /**
     * 暂停滚动
     */
    public void stop() {
        // 移除handle更新
        removeCallbacks(mRunnable);
        // 暂停线程
        isStarted = false;
    }

    /**
     * 设置数据适配
     */
    private void setupAdapter() {
        // 移除所有view
        removeAllViews();
        // 只有一条数据,不滚东
        if (mAdapter.getCount() == 1) {
            mFirstView = mAdapter.getView(this);
            mAdapter.setItem(mFirstView, mAdapter.getItem(0));
            addView(mFirstView);
        } else {
            // 多个数据
            mFirstView = mAdapter.getView(this);
            mSecondView = mAdapter.getView(this);
            mAdapter.setItem(mFirstView, mAdapter.getItem(0));
            mAdapter.setItem(mSecondView, mAdapter.getItem(1));
            // 把2个添加到此控件里
            addView(mFirstView);
            addView(mSecondView);
            mPosition = 1;
            isStarted = false;
        }
    }

    /**
     * 测量控件的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (LayoutParams.WRAP_CONTENT == getLayoutParams().height) {
            getLayoutParams().height = (int) mTrueHeight;
        } else {
            mTrueHeight = getHeight();
        }
        if (mFirstView != null) {
            mFirstView.getLayoutParams().height = (int) mTrueHeight;
        }
        if (mSecondView != null) {
            mSecondView.getLayoutParams().height = (int) mTrueHeight;
        }
    }


    /**
     * 垂直滚蛋
     */
    private void performSwitch() {
        // 属性动画控制控件滚动，y轴方向移动
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mFirstView,
                "translationY", mFirstView.getTranslationY() - mTrueHeight);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mSecondView,
                "translationY", mSecondView.getTranslationY() - mTrueHeight);
        // 动画集
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1, animator2);// 2个动画一起
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {// 动画结束
                mFirstView.setTranslationY(0);
                mSecondView.setTranslationY(0);
                View removedView = getChildAt(0);// 获得第一个子布局
                mPosition++;
                // 设置显示的布局
                mAdapter.setItem(removedView,
                        mAdapter.getItem(mPosition % mAdapter.getCount()));
                // 移除前一个view
                removeView(removedView);
                // 添加下一个view
                addView(removedView, 1);
            }
        });
        set.setDuration(mAnimDuration);// 持续时间
        set.start();// 开启动画
    }

    private AnimRunnable mRunnable = new AnimRunnable();

    private class AnimRunnable implements Runnable {
        @Override
        public void run() {
            performSwitch();
            postDelayed(this, mGap);
        }
    }

    /**
     * 销毁View的时候调用
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 停止滚动
        stop();
    }

    /**
     * 屏幕 旋转
     *
     * @param newConfig
     */
    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
