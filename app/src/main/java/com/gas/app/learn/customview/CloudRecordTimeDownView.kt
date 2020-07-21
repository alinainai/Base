package com.gas.app.learn.customview

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.gas.app.R
import java.lang.ref.WeakReference
import java.text.DecimalFormat

/**
 * @author lijiaxing@360.cn
 * @Date 创建时间 2020/1/2
 * @Description 时间倒计时器
 * @Version
 */
class CloudRecordTimeDownView : LinearLayout {
    private lateinit var mDayCount: TextView
    private lateinit var mHourCount: TextView
    private lateinit var mMinuteCount: TextView
    private lateinit var mSecondCount: TextView
    private lateinit var mTimeDownMill: TextView

    //时间单位对象
    private var mTimeOption: TimeOption? = null

    //开始计时的时间戳
    private var mTimeDownStamp: Long = 0

    //进行计时的 Handler
    private var mHandler: TimeDownHandler? = null

    //格式化 如：5 -> 05 （时分秒需要）
    private val mDf = DecimalFormat(DEFAULT_TIME)

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        this.orientation = HORIZONTAL
        val view = View.inflate(context, R.layout.layout_view_time_down, this)
        mDayCount = view.findViewById(R.id.time_down_day)
        mHourCount = view.findViewById(R.id.time_down_hour)
        mMinuteCount = view.findViewById(R.id.time_down_minute)
        mSecondCount = view.findViewById(R.id.time_down_second)
        mTimeDownMill = view.findViewById(R.id.time_down_mill)
        mTimeOption = TimeOption()

        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CloudRecordTimeDownView)
            if (a.hasValue(R.styleable.CloudRecordTimeDownView_TimeZoneBackground)) {
                val background = a.getDrawable(R.styleable.CloudRecordTimeDownView_TimeZoneBackground)
                if (null != background) {
                    mDayCount.background = background
                    mHourCount.background = background
                    mMinuteCount.background = background
                    mSecondCount.background = background
                }
            }
            if (a.hasValue(R.styleable.CloudRecordTimeDownView_TimeZoneTextColor)) {
                val titleTextColor = a.getColor(R.styleable.CloudRecordTimeDownView_TimeZoneTextColor, Color.parseColor("#FFFFFFFF"))
                mDayCount.setTextColor(titleTextColor)
                mHourCount.setTextColor(titleTextColor)
                mMinuteCount.setTextColor(titleTextColor)
                mSecondCount.setTextColor(titleTextColor)
            }
            if (a.hasValue(R.styleable.CloudRecordTimeDownView_TimeZoneTextSize)) {
                val textSize = a.getDimension(R.styleable.CloudRecordTimeDownView_TimeZoneTextSize, 16f)
                mDayCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                mHourCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                mMinuteCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                mSecondCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            }
            a.recycle()
        }
        setDefaultTime()
    }

    /**
     * 设置倒计时时间戳并开始倒计时操作，
     * 此处减小计算量 对传入的时间戳做处理，获取到秒位
     *
     * @param timeDownStamp 倒计时时间戳
     */
    fun setTimeDownStamp(timeDownStamp: Long) {
        mTimeDownStamp = when {
            timeDownStamp >= 100 -> timeDownStamp / 100
            timeDownStamp >= 50 -> 1
            else -> 0
        }
        handleTimeStamp()
    }

    private fun handleTimeStamp(){
        var currentStamp = mTimeDownStamp
        //天数
        val dayCount = currentStamp / DEFAULT_DAY_MILLISECOND
        mTimeOption!!.optionDay = dayCount.toString()
        currentStamp %= DEFAULT_DAY_MILLISECOND
        //小时数
        val hourCount = currentStamp / DEFAULT_HOUR_MILLISECOND
        mTimeOption!!.optionHour = mDf.format(hourCount)
        currentStamp %= DEFAULT_HOUR_MILLISECOND
        //分钟数
        val minuteCount = currentStamp / DEFAULT_MINUTE_MILLISECOND
        mTimeOption!!.optionMinute = mDf.format(minuteCount)
        currentStamp %= DEFAULT_MINUTE_MILLISECOND
        //秒数
        val secondCount = currentStamp / DEFAULT_SECOND_MILLISECOND
        mTimeOption!!.optionSecond = mDf.format(secondCount)
        currentStamp %= DEFAULT_SECOND_MILLISECOND
        //秒数后面
        val secondMillCount = currentStamp / DEFAULT_SECOND_MILLISECOND_MILL
        mTimeOption!!.optionSecondMill = secondMillCount.toString()
        //更新UI
        updateTimeView(mTimeOption)
    }

    fun startTimeDown() {
        if (mTimeDownStamp >= 1) {
            handleTimeStamp()
            //时间戳自减一
            mTimeDownStamp--
            //开始倒计时
            startDownTime()
        } else {
            //如果出现0和负数，直接重置
            setDefaultTime()
            stopDownTime()
        }
    }

    /**
     * 开始倒计时
     */
    private fun startDownTime() {
        if (mHandler == null) {
            mHandler = TimeDownHandler(this)
        }
        mHandler!!.removeMessages(MESSAGE_UPDATE)
        mHandler!!.sendEmptyMessageDelayed(MESSAGE_UPDATE, DELAY_INTERNAL)
    }

    /**
     * 结束倒计时
     */
    fun stopDownTime() {
        if (mHandler != null) {
            mHandler!!.removeMessages(MESSAGE_UPDATE)
            mHandler = null
        }
    }

    override fun onDetachedFromWindow() {
        if (null != mHandler) {
            mHandler!!.removeCallbacksAndMessages(null)
            mHandler = null
        }
        super.onDetachedFromWindow()
    }

    private fun updateTimeView(time: TimeOption?) {
        if (time == null) {
            setDefaultTime()
        } else {
            time.apply {
                mDayCount.text = if (TextUtils.isEmpty(optionDay)) DEFAULT_TIME_SINGLE else optionDay
                mHourCount.text = if (TextUtils.isEmpty(optionHour)) DEFAULT_TIME else optionHour
                mMinuteCount.text = if (TextUtils.isEmpty(optionMinute)) DEFAULT_TIME else optionMinute
                mSecondCount.text = if (TextUtils.isEmpty(optionSecond)) DEFAULT_TIME else optionSecond
                mTimeDownMill.text = if (TextUtils.isEmpty(optionSecondMill)) DEFAULT_TIME else optionSecondMill
            }
        }
    }

    private fun setDefaultTime() {
        mDayCount.text = DEFAULT_TIME_SINGLE
        mHourCount.text = DEFAULT_TIME
        mMinuteCount.text = DEFAULT_TIME
        mSecondCount.text = DEFAULT_TIME
    }

    private class TimeDownHandler(cloudRecordTimeDownView: CloudRecordTimeDownView?) : Handler() {
        private val mRef: WeakReference<CloudRecordTimeDownView?> = WeakReference(cloudRecordTimeDownView)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MESSAGE_UPDATE -> {
                    removeMessages(MESSAGE_UPDATE)
                    if (mRef.get() != null) {
                        mRef.get()!!.startTimeDown()
                    }
                }
            }
        }

    }

    private class TimeOption {
        var optionDay: String? = null
        var optionHour: String? = null
        var optionMinute: String? = null
        var optionSecond: String? = null
        var optionSecondMill: String? = null

    }

    companion object {
        private const val MESSAGE_UPDATE = 0xF1
        private const val DELAY_INTERNAL = 100L
        private const val DEFAULT_TIME = "00"
        private const val DEFAULT_TIME_SINGLE = "0"

        //为了减少计算，传入的计时时间戳要先 /100 操作。
        private const val DEFAULT_SECOND_MILLISECOND_MILL = 1L
        private const val DEFAULT_SECOND_MILLISECOND = 10L //10*DEFAULT_SECOND_MILLISECOND_MILL
        private const val DEFAULT_MINUTE_MILLISECOND = 600L //60*10*DEFAULT_SECOND_MILLISECOND_MILL
        private const val DEFAULT_HOUR_MILLISECOND = 36000L //60*60*10*DEFAULT_SECOND_MILLISECOND_MILL
        private const val DEFAULT_DAY_MILLISECOND = 864000L //24*60*60*10*DEFAULT_SECOND_MILLISECOND_MILL
    }
}