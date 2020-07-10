package com.gas.app.learn.circleprogress

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import com.gas.app.R
import kotlin.math.min


class CloudRecordCircleProgress @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {
    private var mArcRect = RectF()
    private var mRadius: Float = 0F//总半径 = 0f
    private var mSweepAngle = 0 //总角度 = 0

    //刻度
    private var mMarkPaint = Paint()
    private var mMarkCount = 20 // 刻度数
    private var mMarkColor = Color.BLACK //刻度颜色 = 0
    private var mMarkWidth = dipToPx(4F).toFloat()  //刻度长度 = 0f
    private var mMarkDistance = dipToPx(5F).toFloat()//刻度到线的间距 = 0f

    //绘制圆弧背景
    private var mBgArcPaint = Paint()
    private var mBgArcColor = 0
    private var mArcWidth = 0f

    //圆弧进度
    private var mArcPaint = Paint()
    private var mArcStartColor = 0
    private var mArcEndColor = 0
    private var mAnimator = ValueAnimator()
    private var mAnimTime: Long = 0
    private var mProgress = 0f
    private var mMarkCanvasRotate = 0
    private var mMarkDividedDegree = 0f
    private var mArcBgStartDegree = 0f
    private var mArcStartDegree = 0f
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measureView(widthMeasureSpec, dipToPx(200f))
        val height = measureView(heightMeasureSpec, dipToPx(200f))
        //以最小值为正方形的长
        val defaultSize = Math.min(width, height)
        setMeasuredDimension(defaultSize, defaultSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //求最小值作为实际值
        val minSize = min(w - paddingLeft - paddingRight,
                h - paddingTop - paddingBottom)
        mRadius = (minSize shr 1.toFloat().toInt()).toFloat()
        val mArcRadius = mRadius - mMarkWidth - mMarkDistance - mArcWidth
        mArcRect.top = h.toFloat() / 2 - mArcRadius
        mArcRect.left = w.toFloat() / 2 - mArcRadius
        mArcRect.bottom = h.toFloat() / 2 + mArcRadius
        mArcRect.right = w.toFloat() / 2 + mArcRadius
    }

    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CloudRecordCircleProgress)
        mSweepAngle = typedArray.getInt(R.styleable.CloudRecordCircleProgress_sweepAngle, 240)
        mMarkColor = typedArray.getColor(R.styleable.CloudRecordCircleProgress_markColor, Color.WHITE)
        mMarkCount = typedArray.getInteger(R.styleable.CloudRecordCircleProgress_dottedLineCount, mMarkCount)
        mMarkWidth = typedArray.getDimension(R.styleable.CloudRecordCircleProgress_markWidth, 4f)
        mMarkDistance = typedArray.getDimension(R.styleable.CloudRecordCircleProgress_lineDistance, 4f)
        mArcStartColor = typedArray.getColor(R.styleable.CloudRecordCircleProgress_arcStartColor, Color.RED)
        mArcEndColor = typedArray.getColor(R.styleable.CloudRecordCircleProgress_arcEndColor, Color.RED)
        mBgArcColor = typedArray.getColor(R.styleable.CloudRecordCircleProgress_bgArcColor, Color.RED)
        mArcWidth = typedArray.getDimension(R.styleable.CloudRecordCircleProgress_arcWidth, 15f)
        mAnimTime = typedArray.getInt(R.styleable.CloudRecordCircleProgress_animTime, 700).toLong()
        typedArray.recycle()
    }

    private fun initPaint() {
        mMarkPaint.apply {
            isAntiAlias = true
            color = mMarkColor
            style = Paint.Style.STROKE
            strokeWidth = dipToPx(1f).toFloat()
            strokeCap = Paint.Cap.ROUND
        }

        mBgArcPaint.apply {
            isAntiAlias = true
            color = mBgArcColor
            style = Paint.Style.STROKE
            setShadowLayer(8f, 0f, 6f, Color.parseColor("#CC000000"))
            strokeWidth = mArcWidth
            strokeCap = Paint.Cap.ROUND
        }

        mArcPaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = mArcWidth
            strokeCap = Paint.Cap.ROUND
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        //刻度
        drawMark(canvas)
        //背景圆弧
        drawBgArc(canvas)
        //进度
        drawProgressArc(canvas)
    }

    private fun drawMark(canvas: Canvas) {
        canvas.apply {
            save()
            translate(mRadius, mRadius)
            rotate(mMarkCanvasRotate.toFloat())
            for (i in 0 until mMarkCount) {
                drawLine(0f, mRadius, 0f, mRadius - mMarkWidth, mMarkPaint)
                rotate(mMarkDividedDegree)
            }
            restore()
        }

    }

    private fun drawBgArc(canvas: Canvas) {
        canvas.drawArc(mArcRect, mArcBgStartDegree, mSweepAngle.toFloat(), false, mBgArcPaint)
    }

    private fun drawProgressArc(canvas: Canvas) { //mSweepAngle - 50
        mArcPaint.shader = LinearGradient(
                mArcRect.left, mArcRect.top, mArcRect.right, mArcRect.top, mArcStartColor, mArcEndColor,
                Shader.TileMode.MIRROR)
        canvas.drawArc(mArcRect, mArcStartDegree, -(mSweepAngle * mProgress / MAX_PROGRESS), false, mArcPaint)
    }

    fun setProgress(@FloatRange(from = 0.0, to = 100.0) value: Float) {
        var endValue = value
        if (value >= MAX_PROGRESS) {
            endValue = MAX_PROGRESS.toFloat()
        }
        if (value <= 0) {
            endValue = 0F
        }
        startAnimator(0f, endValue, mAnimTime)
    }

    private fun startAnimator(start: Float, end: Float, animTime: Long) {
        mAnimator.apply {
            cancel()
            setFloatValues(start, end)
            duration = animTime
            addUpdateListener { animation ->
                mProgress = animation.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    fun reset() {
        startAnimator(mProgress, 0.0f, 1000L)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAnimator.cancel()
    }

    private fun dipToPx(dip: Float): Int {
        val density = context.applicationContext.resources.displayMetrics.density
        return (dip * density + 0.5f * if (dip >= 0) 1 else -1).toInt()
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

    companion object {
        private const val CIRCLE_DEGREE = 360
        private const val RIGHT_ANGLE_DEGREE = 90
        private const val MAX_PROGRESS = 100
    }

    init {
        attrs?.let {
            initAttrs(it)
        }
        initPaint()
        mMarkCanvasRotate = CIRCLE_DEGREE - mSweepAngle shr 1
        mMarkDividedDegree = mSweepAngle / (mMarkCount - 1).toFloat()
        mArcBgStartDegree = RIGHT_ANGLE_DEGREE + (CIRCLE_DEGREE - mSweepAngle shr 1).toFloat()
        mArcStartDegree = RIGHT_ANGLE_DEGREE - (CIRCLE_DEGREE - mSweepAngle shr 1).toFloat()
    }
}