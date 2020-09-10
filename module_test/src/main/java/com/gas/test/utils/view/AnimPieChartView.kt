package com.gas.test.utils.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.ViewCompat
import com.gas.test.R
import kotlin.math.ceil


class AnimPieChartView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_WIDTH = 200
        const val DEFAULT_HEIGHT = 200
        const val CIRCLE_DEGREE = 360F
        const val MAX_PROGRESS = 100F
        const val ANIMATION_DURATION = 800L
    }

    var arcInfoList = mutableListOf<ArcInfo>()  //扇形位置等数据集合
    private var centerX = 0 //中心坐标
    private var centerY = 0
    private var radius = 0f //半径
    private val mRectF: RectF //弧形外接矩形
    private val mBgInnerRectF: RectF //innerBg外接矩形
    private var circleWidth = dipToPx(30F).toFloat() //圆圈的宽度
    private var bgInnerCircleWidth = dipToPx(15F).toFloat() //圆圈的宽度
    private val mArcPaint: Paint  //扇形画笔
    private val mCirclePaint: Paint //圆圈画笔
    private var mAnimator = ValueAnimator()
    private var mDrawAngle = 0F

    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnimPieChartView)
        circleWidth = typedArray.getDimension(R.styleable.AnimPieChartView_pieCircleWidth, circleWidth)
        bgInnerCircleWidth = typedArray.getDimension(R.styleable.AnimPieChartView_bgInnerCircleWidth, bgInnerCircleWidth)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measureWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeightSize = MeasureSpec.getSize(heightMeasureSpec)
        val measureWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val measureHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (measureWidthMode == MeasureSpec.AT_MOST
                && measureHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_WIDTH, DEFAULT_HEIGHT)
        } else if (measureWidthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_WIDTH, measureHeightSize)
        } else if (measureHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(measureWidthSize, DEFAULT_HEIGHT)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = measuredWidth / 2
        centerY = measuredHeight / 2
        //设置半径为宽高最小值的1/4
        radius = measuredWidth.coerceAtMost(measuredHeight) / 2.toFloat()
        //设置扇形外接矩形
        mRectF.apply {
            left = centerX - radius + circleWidth / 2 + 1
            top = centerY - radius + circleWidth / 2 + 1
            right = centerX + radius - circleWidth / 2 - 1
            bottom = centerY + radius - circleWidth / 2 - 1
        }
        mBgInnerRectF.apply {
            left = centerX - radius + circleWidth + bgInnerCircleWidth / 2 + 1
            top = centerY - radius + circleWidth + bgInnerCircleWidth / 2 + 1
            right = centerX + radius - circleWidth - bgInnerCircleWidth / 2 - 1
            bottom = centerY + radius - circleWidth - bgInnerCircleWidth / 2 - 1
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawArc(canvas)
        drawBgInnerArc(canvas) //中心背景圆弧
    }

    private fun drawBgInnerArc(canvas: Canvas) {
        mCirclePaint.shader = LinearGradient(mBgInnerRectF.left,
                mBgInnerRectF.top,
                mBgInnerRectF.left,
                mBgInnerRectF.bottom,
                Color.GRAY,
                Color.GRAY,
                Shader.TileMode.MIRROR)
        canvas.drawArc(mBgInnerRectF, 0F, CIRCLE_DEGREE, false, mCirclePaint)
    }

    private fun calculateArc() {
        if (arcInfoList.isEmpty()) {
            return
        }
        var startAngle = 0 //每段扇形开始度数
        var angle: Float //每段扇形所占度数
        arcInfoList.forEachIndexed { i, arc ->
            //获取百分比在360中所占度数
            angle = if (i == arcInfoList.lastIndex) { //保证所有度数加起来等于360
                CIRCLE_DEGREE - startAngle.toFloat()
            } else {
                ceil(arc.percent * CIRCLE_DEGREE.toDouble()).toFloat()
            }
            arc.startAngle = startAngle.toFloat()
            arc.percentAngle = angle
            //下一段扇形开始的度数为之前扇形所占度数总和
            startAngle += angle.toInt()
        }
    }

    private fun drawArc(canvas: Canvas) {
        canvas.save()
        canvas.rotate(-90F, centerX.toFloat(), centerY.toFloat())
        if (arcInfoList.isNotEmpty()) {
            arcInfoList.forEach { arc ->
                var angle = arc.percentAngle
                //+0.1是为了让每个扇形之间没有间隙
                if (angle != 0f) {
                    angle += 0.1f
                }
                mArcPaint.shader = LinearGradient(mRectF.right,
                        mRectF.top,
                        mRectF.left,
                        mRectF.top,
                        arc.startColor,
                        arc.endColor,
                        Shader.TileMode.MIRROR)
                if (arc.startAngle + angle > mDrawAngle) {
                    angle = mDrawAngle - arc.startAngle
                    if(angle<=0){
                        angle=0F
                    }
                }
                canvas.drawArc(mRectF, arc.startAngle, angle, false, mArcPaint)
            }
        }
        canvas.restore()
    }

    fun setData(list: List<ArcInfo>) {
        arcInfoList.clear()
        arcInfoList.addAll(list)
        calculateArc()
        startAnimator()
    }

    private fun startAnimator() {
        mAnimator.apply {
            cancel()
            setFloatValues(0F, 360F)
            duration = ANIMATION_DURATION
            interpolator=AccelerateDecelerateInterpolator()
            addUpdateListener { animation ->
                mDrawAngle = animation.animatedValue as Float
                postInvalidateOnAnimation()
            }
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAnimator.cancel()
    }

    private fun dipToPx(dip: Float): Int {
        val density = context.applicationContext.resources.displayMetrics.density
        return (dip * density + 0.5f * if (dip >= 0) 1 else -1).toInt()
    }

    init {
        attrs?.let {
            initAttrs(it)
        }
        mArcPaint = Paint().apply {
            strokeWidth = circleWidth
            isAntiAlias = true
            style = Paint.Style.STROKE
        }
        mCirclePaint = Paint().apply {
            strokeWidth = bgInnerCircleWidth
            isAntiAlias = true
            style = Paint.Style.STROKE
        }
        mRectF = RectF()
        mBgInnerRectF = RectF()
    }

    data class ArcInfo(val startColor: Int,
                       val endColor: Int,
                       val percent: Float) {
        var startAngle: Float = 0F
        var percentAngle: Float = 0F
    }

}