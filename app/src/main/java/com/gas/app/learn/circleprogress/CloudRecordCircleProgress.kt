package com.gas.app.learn.circleprogress

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import com.gas.app.R
import kotlin.math.min

class CloudRecordCircleProgress(context: Context?, attrs: AttributeSet) : View(context, attrs) {
    private lateinit var mArcRect: RectF
    private var mRadius: Float = 0F//总半径 = 0f
    private var mSweepAngle = 0 //总角度 = 0

    //刻度
    private lateinit var mMarkPaint : Paint
    private var mMarkCount = 20 // 刻度数
    private var mMarkColor = Color.BLACK //刻度颜色 = 0
    private var mMarkWidth = dipToPx(4F).toFloat()  //刻度长度 = 0f
    private var mMarkDistance = dipToPx(5F).toFloat()//刻度到线的间距 = 0f

    //绘制圆弧背景
    private lateinit var mBgArcPaint: Paint
    private var mBgArcColor = 0
    private var mArcWidth = 0f

    //圆弧进度
    private lateinit var mArcPaint : Paint
    private var mArcStartColor = 0
    private var mArcEndColor = 0
    private lateinit var mAnimator: ValueAnimator
    private var mAnimTime //动画时间
            : Long = 0
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

    private fun init(attrs: AttributeSet) {
        mAnimator = ValueAnimator()
        mArcRect = RectF()
        initAttrs(attrs)
        initPaint()
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
        mMarkCanvasRotate = CIRCLE_DEGREE - mSweepAngle shr 1
        mMarkDividedDegree = mSweepAngle / (mMarkCount - 1).toFloat()
        mArcBgStartDegree = RIGHT_ANGLE_DEGREE + (CIRCLE_DEGREE - mSweepAngle shr 1).toFloat()
        mArcStartDegree = RIGHT_ANGLE_DEGREE - (CIRCLE_DEGREE - mSweepAngle shr 1).toFloat()
    }

    private fun initPaint() {
        mMarkPaint = Paint()
        mMarkPaint.isAntiAlias = true
        mMarkPaint.color = mMarkColor
        mMarkPaint.style = Paint.Style.STROKE
        mMarkPaint.strokeWidth = dipToPx(1f).toFloat()
        mMarkPaint.strokeCap = Paint.Cap.ROUND
        mBgArcPaint = Paint()
        mBgArcPaint.isAntiAlias = true
        mBgArcPaint.color = mBgArcColor
        mBgArcPaint.style = Paint.Style.STROKE
        mBgArcPaint.setShadowLayer(8f, 0f, 6f, Color.parseColor("#CC000000"))
        mBgArcPaint.strokeWidth = mArcWidth
        mBgArcPaint.strokeCap = Paint.Cap.ROUND
        mArcPaint = Paint()
        mArcPaint.isAntiAlias = true
        mArcPaint.style = Paint.Style.STROKE
        mArcPaint.strokeWidth = mArcWidth
        mArcPaint.strokeCap = Paint.Cap.ROUND
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
        canvas.save()
        canvas.translate(mRadius, mRadius)
        canvas.rotate(mMarkCanvasRotate.toFloat())
        for (i in 0 until mMarkCount) {
            canvas.drawLine(0f, mRadius, 0f, mRadius - mMarkWidth, mMarkPaint)
            canvas.rotate(mMarkDividedDegree)
        }
        canvas.restore()
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
        mAnimator.cancel()
        mAnimator.setFloatValues(start, end)
        mAnimator.duration = animTime
        mAnimator.addUpdateListener { animation ->
            mProgress = animation.animatedValue as Float
            invalidate()
        }
        mAnimator.start()
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
        init(attrs)
    }
}