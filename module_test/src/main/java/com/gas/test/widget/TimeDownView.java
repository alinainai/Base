package com.gas.test.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gas.test.R;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;


/**
 * ================================================
 * desc: 抢购倒计时布局
 * <p>
 * created by author ljx
 * Date  2020-03-04
 * email 569932357@qq.com
 * <p>
 * ================================================
 */
public class TimeDownView extends LinearLayout {

    private static final int MESSAGE_UPDATE = 0xF1;
    private static final long DELAY_INTERNAL = 1000L;
    private static final String DEFAULT_TIME = "00";
    private static final String DEFAULT_TIME_SINGLE = "0";
    private static final String DAY_UNIT = "天";

    /**
     * 为了减少计算，传入的计时时间戳要先 /1000 操作。
     */
    private static final long DEFAULT_SECOND_MILLISECOND = 1L;
    private static final long DEFAULT_MINUTE_MILLISECOND = 60 * DEFAULT_SECOND_MILLISECOND;
    private static final long DEFAULT_HOUR_MILLISECOND = 60 * DEFAULT_MINUTE_MILLISECOND;
    private static final long DEFAULT_DAY_MILLISECOND = 24 * DEFAULT_HOUR_MILLISECOND;

    /**
     * 计时器布局标语 默认显示为 tips
     */
    private TextView mTimeDownTitle;

    /**
     * 天数的单位展示，默认为"天"
     */
    private TextView mTimeDownDayUnit;

    /**
     * 倒计时天数
     */
    private TextView mDayCount;

    /**
     * 倒计时小时数
     */
    private TextView mHourCount;

    /**
     * 倒计时分钟数
     */
    private TextView mMinuteCount;

    /**
     * 倒计时秒数
     */
    private TextView mSecondCount;

    /**
     * 时间单位对象
     */
    private TimeOption mTimeOption;

    /**
     * 开始计时的时间戳
     */
    private long mTimeDownStamp;

    /**
     * 进行计时的 Handler
     */
    private TimeDownHandler mHandler;

    //格式化 如：5 -> 05 （时分秒需要）
    private DecimalFormat mDf = new DecimalFormat(DEFAULT_TIME);

    public TimeDownView(Context context) {
        super(context);
        init(null);
    }

    public TimeDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TimeDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        this.setOrientation(HORIZONTAL);
        View view = View.inflate(getContext(), R.layout.test_layout_view_time_down, this);
        mDayCount = view.findViewById(R.id.time_down_day);
        mHourCount = view.findViewById(R.id.time_down_hour);
        mMinuteCount = view.findViewById(R.id.time_down_minute);
        mSecondCount = view.findViewById(R.id.time_down_second);
        mTimeDownTitle = view.findViewById(R.id.time_down_title);
        mTimeDownDayUnit = view.findViewById(R.id.time_down_day_unit);
        mTimeOption = new TimeOption();
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TimeDownView);
        String titleText = a.getString(R.styleable.TimeDownView_timeTitleText);

        if (TextUtils.isEmpty(titleText)) {
            mTimeDownTitle.setVisibility(GONE);
        } else {
            mTimeDownTitle.setVisibility(VISIBLE);
            mTimeDownTitle.setText(titleText);
        }

        String unitText = a.getString(R.styleable.TimeDownView_timeDayUnit);
        mTimeDownDayUnit.setText(TextUtils.isEmpty(unitText) ? DAY_UNIT : unitText);

        if (a.hasValue(R.styleable.TimeDownView_timeZoneBackground)) {
            Drawable background = a.getDrawable(R.styleable.TimeDownView_timeZoneBackground);
            if (null != background) {
                mDayCount.setBackground(background);
                mHourCount.setBackground(background);
                mMinuteCount.setBackground(background);
                mSecondCount.setBackground(background);
            }
        }

        if (a.hasValue(R.styleable.TimeDownView_timeZoneTextColor)) {

            int titleTextColor = a.getColor(R.styleable.TimeDownView_timeZoneTextColor, Color.parseColor("#FFFFFFFF"));

            mDayCount.setTextColor(titleTextColor);
            mHourCount.setTextColor(titleTextColor);
            mMinuteCount.setTextColor(titleTextColor);
            mSecondCount.setTextColor(titleTextColor);
            mTimeDownTitle.setTextColor(titleTextColor);
            mTimeDownDayUnit.setTextColor(titleTextColor);

        }

        if (a.hasValue(R.styleable.TimeDownView_timeZoneTextSize)) {

            float textSize = a.getDimension(R.styleable.TimeDownView_timeZoneTextSize, 16);
            mDayCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            mHourCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            mMinuteCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            mSecondCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            mTimeDownTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            mTimeDownDayUnit.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        }
        a.recycle();
        setDefaultTime();
    }

    /**
     * 设置倒计时时间戳，并开始倒计时操作
     * 此处减小计算量 对传入的时间戳做处理，获取到秒位
     *
     * @param timeDownStamp 传入时间戳间隔
     */
    public void setTimeDown(long timeDownStamp) {

        if (timeDownStamp >= 1000) {
            this.mTimeDownStamp = timeDownStamp / 1000;
        } else if (timeDownStamp >= 500) {
            this.mTimeDownStamp = 1;
        } else {
            this.mTimeDownStamp = 0;
        }
        updateTimeStamp();
    }

    /**
     * 更新UI
     */
    private void updateTimeStamp() {

        if (mTimeDownStamp >= 1) {
            long currentStamp = mTimeDownStamp;
            //天数
            long dayCount = currentStamp / DEFAULT_DAY_MILLISECOND;
            mTimeOption.setOptionDay(String.valueOf(dayCount));
            currentStamp = currentStamp % DEFAULT_DAY_MILLISECOND;
            //小时数
            long hourCount = currentStamp / DEFAULT_HOUR_MILLISECOND;
            mTimeOption.setOptionHour(mDf.format(hourCount));
            currentStamp = currentStamp % DEFAULT_HOUR_MILLISECOND;
            //分钟数
            long minuteCount = currentStamp / DEFAULT_MINUTE_MILLISECOND;
            mTimeOption.setOptionMinute(mDf.format(minuteCount));
            currentStamp = currentStamp % DEFAULT_MINUTE_MILLISECOND;
            //秒数
            long secondCount = currentStamp / DEFAULT_SECOND_MILLISECOND;
            mTimeOption.setOptionSecond(mDf.format(secondCount));

            //更新UI
            updateTimeView(mTimeOption);
            //时间戳自减一
            mTimeDownStamp--;
            //开始倒计时
            sendDownTimeMsg();

        } else {

            //如果出现0和负数，直接重置
            setDefaultTime();
            stopDownTime();

        }

    }

    /**
     * 开始倒计时
     */
    private void sendDownTimeMsg() {

        if (mHandler == null) {
            mHandler = new TimeDownHandler(this);
        }
        mHandler.removeMessages(MESSAGE_UPDATE);
        mHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE, DELAY_INTERNAL);
    }

    /**
     * 结束倒计时
     */
    public void stopDownTime() {

        if (mHandler != null) {
            mHandler.removeMessages(MESSAGE_UPDATE);
            mHandler = null;
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDetachedFromWindow();
    }

    private void updateTimeView(TimeOption time) {

        if (time == null) {
            setDefaultTime();
        } else {
            mDayCount.setText(TextUtils.isEmpty(time.getOptionDay()) ? DEFAULT_TIME_SINGLE : time.getOptionDay());
            mHourCount.setText(TextUtils.isEmpty(time.getOptionHour()) ? DEFAULT_TIME : time.getOptionHour());
            mMinuteCount.setText(TextUtils.isEmpty(time.getOptionMinute()) ? DEFAULT_TIME : time.getOptionMinute());
            mSecondCount.setText(TextUtils.isEmpty(time.getOptionSecond()) ? DEFAULT_TIME : time.getOptionSecond());
        }

    }

    /**
     * 重置布局值为默认值
     */
    private void setDefaultTime() {
        mDayCount.setText(DEFAULT_TIME_SINGLE);
        mHourCount.setText(DEFAULT_TIME);
        mMinuteCount.setText(DEFAULT_TIME);
        mSecondCount.setText(DEFAULT_TIME);
    }

    /**
     * 辅助倒计时的 Handler 对象
     */
    private static class TimeDownHandler extends Handler {

        private WeakReference<TimeDownView> mRef;

        TimeDownHandler(TimeDownView timeDownView) {
            mRef = new WeakReference<>(timeDownView);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_UPDATE:
                    removeMessages(MESSAGE_UPDATE);
                    if (mRef.get() != null) {
                        mRef.get().updateTimeStamp();
                    }
                    break;
            }
        }
    }

    private class TimeOption {

        private String optionDay;
        private String optionHour;
        private String optionMinute;
        private String optionSecond;

        public String getOptionDay() {
            return optionDay;
        }

        public void setOptionDay(String optionDay) {
            this.optionDay = optionDay;
        }

        public String getOptionHour() {
            return optionHour;
        }

        public void setOptionHour(String optionHour) {
            this.optionHour = optionHour;
        }

        public String getOptionMinute() {
            return optionMinute;
        }

        public void setOptionMinute(String optionMinute) {
            this.optionMinute = optionMinute;
        }

        public String getOptionSecond() {
            return optionSecond;
        }

        public void setOptionSecond(String optionSecond) {
            this.optionSecond = optionSecond;
        }
    }

}

