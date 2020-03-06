package com.gas.test.widget.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.gas.test.R;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

/**
 * ================================================
 * desc: 倒计时pop布局
 * <p>
 * created by author ljx
 * Date  2020-03-04
 * email 569932357@qq.com
 * <p>
 * ================================================
 */
public class TimeDownPromptBanner extends FrameLayout {


    /**
     * -1不显示倒计时且不再倒计时后消失
     */
    public final static long DEFAULT_SHOW_DURATION = -1;

    /**
     * 倒计时单位
     */
    public final static String TIME_DOWN_WRAP_UNIT = "%ds";

    public final static String DEFAULT_BANNER_TAG = "banner";


    /**
     * banner显示消息
     */
    public final static int SHOW_ACTION = 0x02;

    /**
     * banner隐藏消息
     */
    public final static int HIDE_ACTION = 0x03;


    /**
     * 更新倒计时 UI 消息
     */
    public final static int SHOW_TIME_COUNT_ACTION = 0x04;


    /**
     * 关闭剩余倒计时时长 单位s
     */
    private long mCloseDuration = DEFAULT_SHOW_DURATION;

    /**
     * 关闭剩余倒计时计时 单位s
     */
    private long mCloseTimeDown;


    /**
     * 辅助完成倒计时的 handler 对象
     */
    private TimeDownHandler mHandler;

    /**
     * banner 的图标
     */
    private ImageView mBannerIcon;

    /**
     * 主标题
     */
    private TextView mBannerTitle;

    /**
     * 副标题
     */
    private TextView mBannerSubTitle;

    /**
     * 倒计时UI
     */
    private TextView mBannerTimeDown;

    /**
     * 关闭按钮
     */
    private ImageView mBannerClose;


    /**
     * 根布局背景
     */
    private View mBannerBg;

    /**
     * banner的显示配置信息
     *
     * {@link BannerConfig}
     */
    private BannerConfig mConfig;


    public TimeDownPromptBanner(Context context) {
        super(context);
        init();
    }


    public TimeDownPromptBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeDownPromptBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), getLayoutResId(), this);
        mBannerBg = view.findViewById(R.id.banner_bg);
        mBannerIcon = view.findViewById(R.id.banner_icon);
        mBannerTitle = view.findViewById(R.id.banner_main_title);
        mBannerSubTitle = view.findViewById(R.id.banner_sub_title);
        mBannerTimeDown = view.findViewById(R.id.banner_time_down);
        mBannerClose = view.findViewById(R.id.banner_close);
        mHandler = new TimeDownHandler(this);
        setVisibility(View.GONE);
    }


    private void setConfig(@NotNull BannerConfig config) {

        // 设置背景资源
        if (config.getBgResId() != 0) {
            if (mBannerBg != null) {
                mBannerBg.setBackgroundResource(config.getBgResId());
            }
        }else {
            if (mBannerBg != null) {
                mBannerBg.setBackgroundResource(getDefaultBgRes());
            }
        }

        //设置图标资源
        if (config.getIconResId() != 0) {
            if (mBannerIcon != null) {
                mBannerIcon.setImageResource(config.getIconResId());
            }
        }else {
            if (mBannerIcon != null) {
                mBannerIcon.setImageResource(getDefaultIconRes());
            }
        }

        //设置主标题
        if (!TextUtils.isEmpty(config.getTitle())) {
            if (mBannerTitle != null) {
                mBannerTitle.setText(config.getTitle());
            }
        }else {
            if (mBannerTitle != null) {
                mBannerTitle.setText("");
            }
        }

        //设置主标题颜色
        if (config.getTitleColor() != 0) {
            if (mBannerTitle != null) {
                mBannerTitle.setTextColor(getResources().getColor(config.getTitleColor()));
            }
        } else {
            if (mBannerTitle != null) {
                mBannerTitle.setTextColor(getResources().getColor(getDefaultTextColor()));
            }
        }

        //设置副标题
        if (!TextUtils.isEmpty(config.getSubTitle())) {
            if (mBannerSubTitle != null) {
                mBannerSubTitle.setVisibility(VISIBLE);
                mBannerSubTitle.setText(config.getSubTitle());
            }
        } else {
            if (mBannerSubTitle != null) {
                mBannerSubTitle.setVisibility(GONE);
            }
        }

        //设置副标题颜色
        if (config.getSubTitleColor() != 0) {
            if (mBannerSubTitle != null) {
                mBannerSubTitle.setTextColor(getResources().getColor(config.getSubTitleColor()));
            }
        } else {
            if (mBannerSubTitle != null) {
                mBannerSubTitle.setTextColor(getResources().getColor(getDefaultTextColor()));
            }
        }


        //设置关闭图标资源
        if (config.getCloseResId() != 0) {
            if (mBannerClose != null) {
                mBannerClose.setImageResource(config.getCloseResId());
            }
        }else {
            if (mBannerClose != null) {
                mBannerClose.setImageResource(getDefaultCloseRes());
            }
        }

        //设置副标题点击事件
        if (mBannerSubTitle != null && config.getOnSubTitleClick() != null) {
            mBannerSubTitle.setClickable(true);
            mBannerSubTitle.setOnClickListener(v -> {
                config.getOnSubTitleClick().run();
                sendMessage(HIDE_ACTION);
            });
        } else {
            if (mBannerSubTitle != null) {
                mBannerSubTitle.setClickable(false);
            }
        }

        //设置关闭按钮点击事件
        if (mBannerClose != null && config.getOnCloseClick() != null) {
            mBannerClose.setClickable(true);
            mBannerClose.setOnClickListener(v -> {
                config.getOnCloseClick().run();
                sendMessage(HIDE_ACTION);
            });
        } else {
            if (mBannerClose != null) {
                mBannerClose.setClickable(false);
            }
        }

        //设置当前条目点击事件
        this.setOnClickListener(v -> {
            if (config.getViewOnClick() != null) {
                config.getViewOnClick().run();
            }
            sendMessage(HIDE_ACTION);
        });

        if (config.getShowDuration() == DEFAULT_SHOW_DURATION) {
            mCloseDuration = config.getShowDuration();
            if (mBannerTimeDown != null) {
                mBannerTimeDown.setVisibility(GONE);
            }
        } else {
            mCloseDuration = config.getShowDuration();
            mCloseTimeDown = mCloseDuration;
            if (mBannerTimeDown != null) {
                mBannerTimeDown.setVisibility(VISIBLE);
            }
        }

    }


    public void dismiss() {
        sendMessage(HIDE_ACTION);
    }


    /**
     * 立即显示且不带关闭倒计时
     */
    public void show(BannerConfig config) {
        showAfterDuration(config, 0);
    }

    /**
     * 延迟 duration 毫秒后展示且不带倒计时关闭
     *
     * @param duration 延迟展示时长 单位：ms
     */
    public void showAfterDuration(BannerConfig config, long duration) {
        this.mConfig = config;
        sendMessage(SHOW_ACTION, duration);
    }


    @SuppressLint("DefaultLocale")
    private void updateTimeView() {

        if (this.getVisibility() != View.GONE && mCloseTimeDown > 0) {
            mCloseTimeDown--;
            mBannerTimeDown.setText(String.format(TIME_DOWN_WRAP_UNIT, mCloseTimeDown));
            sendMessage(SHOW_TIME_COUNT_ACTION, 1000);
        } else {
            removeMessage(SHOW_TIME_COUNT_ACTION);
        }

    }


    /**
     * 显示弹窗
     */
    @SuppressLint("DefaultLocale")
    private void showView() {
        if (this.getVisibility() != VISIBLE) {
            setConfig(mConfig);
            this.clearAnimation();
            if (mCloseDuration != DEFAULT_SHOW_DURATION) {
                mCloseTimeDown = mCloseDuration;
                mBannerTimeDown.setText(String.format(TIME_DOWN_WRAP_UNIT, mCloseTimeDown));
                sendMessage(HIDE_ACTION, mCloseDuration * 1000);
                sendMessage(SHOW_TIME_COUNT_ACTION, 1000);
            }else {
                removeMessage(HIDE_ACTION);
                removeMessage(SHOW_TIME_COUNT_ACTION);
            }
            this.setVisibility(View.VISIBLE);
            this.setTag(mConfig.getTag());
            Animation a = AnimationUtils.loadAnimation(this.getContext(), getShowAnimRes());
            this.startAnimation(a);
        } else {
            if (!this.getTag().equals(mConfig.getTag())) {
                this.clearAnimation();
                Animation a = AnimationUtils.loadAnimation(this.getContext(), getHideAnimRes());
                a.setAnimationListener(new AnimationEndListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        setVisibility(View.GONE);
                        sendMessage(SHOW_ACTION);
                    }
                });
                this.startAnimation(a);
            }
        }
    }


    protected int getShowAnimRes() {
        return R.anim.slide_in_from_top;
    }

    protected int getHideAnimRes() {
        return R.anim.slide_out_to_top;
    }

    protected @LayoutRes
    int getLayoutResId() {
        return R.layout.layout_banner_timedown;
    }

    protected @ColorRes
    int getDefaultTextColor() {
        return R.color.public_white;
    }

    protected @DrawableRes
    int getDefaultBgRes() {
        return R.drawable.drawable_banner_time_down_bg;
    }

    protected @DrawableRes
    int getDefaultCloseRes() {
        return R.drawable.drawable_banner_time_down_close;
    }

    protected @DrawableRes
    int getDefaultIconRes() {
        return R.mipmap.drawable_banner_time_down_icon;
    }

    /**
     * 隐藏弹窗
     */
    private void hideView() {
        if (this.getVisibility() == View.GONE) {
            return;
        }
        this.clearAnimation();

        Animation a = AnimationUtils.loadAnimation(this.getContext(), getHideAnimRes());
        a.setAnimationListener(new AnimationEndListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(View.GONE);
            }
        });
        this.startAnimation(a);
    }


    private void removeMessage(int what) {
        if (mHandler != null) {
            mHandler.removeMessages(what);
        }

    }

    private void sendMessage(int what) {
        sendMessage(what, 0);
    }


    private void sendMessage(int what, long delayMillis) {
        if (mHandler == null) {
            mHandler = new TimeDownHandler(this);
        }
        removeMessage(what);
        if (delayMillis == 0) {
            mHandler.sendEmptyMessage(what);
        } else {
            mHandler.sendEmptyMessageDelayed(what, delayMillis);
        }

    }


    private void removeAllMessage() {
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        removeAllMessage();
        super.onDetachedFromWindow();
    }


    private static class TimeDownHandler extends Handler {

        private WeakReference<TimeDownPromptBanner> mRef;

        TimeDownHandler(TimeDownPromptBanner view) {
            mRef = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case SHOW_TIME_COUNT_ACTION:
                    removeMessages(SHOW_TIME_COUNT_ACTION);
                    if (mRef.get() != null) {
                        mRef.get().updateTimeView();
                    }
                    break;
                case SHOW_ACTION:
                    removeMessages(SHOW_ACTION);
                    if (mRef.get() != null) {
                        mRef.get().showView();
                    }
                    break;
                case HIDE_ACTION:
                    removeMessages(SHOW_TIME_COUNT_ACTION);
                    removeMessages(HIDE_ACTION);
                    if (mRef.get() != null) {
                        mRef.get().hideView();
                    }
                    break;

            }
        }
    }

    /**
     * 配置信息
     */
    public static class BannerConfig {


        /**
         * 整个条目背景资源Id
         */
        @DrawableRes
        private int mBannerBgId;

        /**
         * Icon图片的资源Id
         */
        @DrawableRes
        private int mIconResId;

        /**
         * titleColor
         */
        @ColorRes
        private int mTitleColor;

        /**
         * title
         */
        private String mTitle = "";

        /**
         * subTitleColor
         */
        @ColorRes
        private int mSubTitleColor;

        /**
         * title
         */
        private String mSubTitle = "";

        /**
         * close图片的资源Id
         */
        @DrawableRes
        private int mCloseResId;

        /**
         * 条目点击事件
         */
        private Runnable mViewOnClick;

        /**
         * 副标题点击事件
         */
        private Runnable mSubTitleOnClick;

        /**
         * 关闭按钮点击事件
         */
        private Runnable mOnCloseClick;

        /**
         * 展示时长
         */
        private long mShowDuration = DEFAULT_SHOW_DURATION;

        /**
         * 一个界面多次展示不同消息的TAG，防止多次显示
         */
        private String mTag = DEFAULT_BANNER_TAG;

        public BannerConfig setBgResId(@DrawableRes int bgResId) {
            this.mBannerBgId = bgResId;
            return this;
        }


        public BannerConfig setIconResId(int iconResId) {
            this.mIconResId = iconResId;
            return this;
        }


        public BannerConfig setTitleColor(@ColorRes int titleColorId) {
            this.mTitleColor = titleColorId;
            return this;
        }

        public BannerConfig setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public BannerConfig setSubTitleColor(@ColorRes int titleColorId) {
            this.mSubTitleColor = titleColorId;
            return this;
        }

        public BannerConfig setSubTitle(String title) {
            this.mSubTitle = title;
            return this;
        }

        public BannerConfig setCloseResId(@DrawableRes int resId) {
            this.mCloseResId = resId;
            return this;
        }


        public BannerConfig setSubTitleOnClick(Runnable actionOnClick) {
            this.mSubTitleOnClick = actionOnClick;
            return this;
        }

        public BannerConfig setOnCloseClick(Runnable onCloseClick) {
            this.mOnCloseClick = onCloseClick;
            return this;
        }

        public BannerConfig setViewOnClick(Runnable viewOnClick) {
            this.mViewOnClick = viewOnClick;
            return this;
        }

        public BannerConfig setShowDuration(@IntRange(from = 0, to = 20) int showDuration) {
            this.mShowDuration = showDuration;
            return this;
        }

        public BannerConfig setTag(String tag) {
            this.mTag = tag;
            return this;
        }

        public BannerConfig(String title) {
            this.mTitle = title;
        }

        public int getBgResId() {
            return mBannerBgId;
        }

        public int getIconResId() {
            return mIconResId;
        }


        public int getTitleColor() {
            return mTitleColor;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getSubTitle() {

            return mSubTitle;
        }

        public int getSubTitleColor() {
            return mSubTitleColor;
        }

        public int getCloseResId() {
            return mCloseResId;
        }

        public Runnable getOnSubTitleClick() {
            return mSubTitleOnClick;
        }

        public Runnable getOnCloseClick() {
            return mOnCloseClick;
        }

        public Runnable getViewOnClick() {
            return mViewOnClick;
        }

        public long getShowDuration() {
            return mShowDuration;
        }

        public String getTag() {
            return mTag;
        }
    }

    abstract class AnimationEndListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
