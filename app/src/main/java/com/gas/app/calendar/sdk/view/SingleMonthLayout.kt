package com.gas.app.calendar.sdk.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.View.OnLongClickListener
import com.gas.app.calendar.sdk.data.CalendarDayModel
import com.gas.app.calendar.sdk.data.CalendarMonthModel
import com.gas.app.calendar.sdk.utils.CalendarSelectListener
import org.joda.time.LocalDate
import kotlin.math.min
import kotlin.math.roundToInt


class SingleMonthLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr), View.OnClickListener, OnLongClickListener {

    companion object {
        private const val DEFAULT_HEIGHT = 240F
        private const val ROW_COUNT = 6
        private const val COLUMN_COUNT = 7
    }

    private var mContentWidth = 0
    private var mContentHeight = 0
    private var mItemHeight = 0
    private var mItemWidth = 0
    private var mTextBaseLine = 0F
    private var mCalendarMonthModel: CalendarMonthModel? = null
    private var mX = 0f //点击的x、y坐标
    private var mY = 0f
    private var isClick = true

    private val mTextPaint = Paint()
    private val mTextSize = dpToPx(14F).toFloat()
    private var disableTextColor = Color.parseColor("#CACACA")
    private var selectTextColor = Color.parseColor("#4C3F32")
    private var normalTextColor = Color.parseColor("#4C3F32")

    private val mBgPaint = Paint()
    private var mBgRadius = dpToPx(17F)
    private var bgColor = Color.parseColor("#EDC78F")

    private var mCalendarSelectListener: CalendarSelectListener? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = measureView(heightMeasureSpec, dpToPx(DEFAULT_HEIGHT))
        setMeasuredDimension(widthMeasureSpec, height)
    }

    private fun measureView(measureSpec: Int, defaultSize: Int): Int {
        var result = defaultSize
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = min(result, specSize)
        }
        return result
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mContentWidth = w - paddingLeft - paddingRight
        mContentHeight = h - paddingTop - paddingBottom
        mItemHeight = mContentHeight / ROW_COUNT
        mItemWidth = mContentWidth / COLUMN_COUNT
        mTextBaseLine = mTextPaint.fontMetrics.let { metrics ->
            mItemHeight / 2 - metrics.descent + (metrics.bottom - metrics.top) / 2
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var d = 0
        mCalendarMonthModel?.dayList?.let { items ->
            for (i in 0 until ROW_COUNT) {
                for (j in 0 until COLUMN_COUNT) {
                    val calendar: CalendarDayModel = items[d]
                    draw(canvas, calendar, i, j, d)
                    ++d
                }
            }
        }
    }

    private fun draw(canvas: Canvas, calendar: CalendarDayModel, i: Int, j: Int, d: Int) {
        val x: Int = j * mItemWidth + paddingLeft
        val y = i * mItemHeight
        drawCell(canvas, calendar, x, y)
    }

    private fun drawCell(canvas: Canvas, calendar: CalendarDayModel, x: Int, y: Int) {
        val cx = (x + mItemWidth / 2)
        val cy = (y + mItemHeight / 2)
        val baselineY: Float = mTextBaseLine + y - dpToPx(1F)
        if (calendar.isSelected) {
            canvas.drawCircle(cx.toFloat(), cy.toFloat(), mBgRadius.toFloat(), mBgPaint)
            mTextPaint.color = selectTextColor
            canvas.drawText(calendar.dayOfMonth.toString(), cx.toFloat(), baselineY, mTextPaint)
        } else if (!calendar.isEnabled) {
            mTextPaint.color = disableTextColor
            canvas.drawText(calendar.dayOfMonth.toString(), cx.toFloat(), baselineY, mTextPaint)
        } else {
            mTextPaint.color = normalTextColor
            canvas.drawText(calendar.dayOfMonth.toString(), cx.toFloat(), baselineY, mTextPaint)
        }
    }

    fun setMonthModel(model: CalendarMonthModel) {
        mCalendarMonthModel = model
        invalidate()
    }

    fun updateTimeStamp(timeStamp:Long) {
        mCalendarMonthModel?.let {model->
            model.updateStamp(timeStamp)
            invalidate()
        }
    }

    fun setSelectDay(localDate: LocalDate? = null) {
        if (localDate == null) {
            mCalendarMonthModel?.dayList?.forEach { model ->
                if (model.isEnabled) {
                    model.isSelected = false
                }
            }
        } else {
            mCalendarMonthModel?.dayList?.forEach { model ->
                if (model.isEnabled) {
                    model.isSelected = model.localDate == localDate
                }
            }
        }
        invalidate()
    }

    fun setCalendarSelectListener(listener: CalendarSelectListener) {
        mCalendarSelectListener = listener
    }

    override fun onClick(v: View) {
        if (!isClick) {
            return
        }
        val calendar = getIndex() ?: return
        if (!calendar.isEnabled) {
            return
        }
        mCalendarSelectListener?.onCalendarSelect(calendar, true)
    }

    override fun onLongClick(v: View): Boolean {
        return false
    }

    private fun getIndex(): CalendarDayModel? {
        mCalendarMonthModel?.dayList?.let { items ->
            if (mItemWidth == 0 || mItemHeight == 0) {
                return null
            }
            var indexX = (mX - paddingLeft).toInt() / mItemWidth
            if (indexX >= 7) {
                indexX = 6
            }
            val indexY = mY.toInt() / mItemHeight
            val position = indexY * 7 + indexX // 选择项
            return if (position >= 0 && position < items.size) items[position] else null
        }
        return null

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.pointerCount > 1) return false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mX = event.x
                mY = event.y
                isClick = true
            }
            MotionEvent.ACTION_MOVE -> {
                val mDY: Float
                if (isClick) {
                    mDY = event.y - mY
                    isClick = Math.abs(mDY) <= 50
                }
            }
            MotionEvent.ACTION_UP -> {
                mX = event.x
                mY = event.y
            }
        }
        return super.onTouchEvent(event)
    }

    init {
        mTextPaint.apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            isFakeBoldText = true
            textSize = mTextSize
        }

        mBgPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeWidth = 1F
            color = bgColor
        }
        setOnClickListener(this)
        setOnLongClickListener(this)
    }

    private fun dpToPx(dp: Float): Int {
        val displayMetrics = context.resources.displayMetrics
        return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

}