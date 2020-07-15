package com.gas.app.learn.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import com.gas.app.R
import kotlin.math.min


class ArcView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {
    private var mArcRect = RectF()
    private var mRadius: Float = 0F//总半径 = 0f

    //绘制圆弧背景
    private val mBgArcPaint = Paint()
    private var mBgArcColor = Color.RED
    private var mArcWidth = dipToPx(10F).toFloat()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measureView(widthMeasureSpec, dipToPx(200f))
        val height = measureView(heightMeasureSpec, dipToPx(200f))
        //以最小值为正方形的长
        val defaultSize = width.coerceAtMost(height)
        setMeasuredDimension(defaultSize, defaultSize)
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
        //求最小值作为实际值
        val minSize = min(w - paddingLeft - paddingRight,
                h - paddingTop - paddingBottom)
        mRadius = (minSize shr 1.toFloat().toInt()).toFloat()
        mArcRect.top = h.toFloat() / 2 - mRadius + mArcWidth
        mArcRect.left = w.toFloat() / 2 - mRadius + mArcWidth
        mArcRect.bottom = h.toFloat() / 2 + mRadius - mArcWidth
        mArcRect.right = w.toFloat() / 2 + mRadius - mArcWidth
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        //背景圆弧
        canvas.save()
        canvas.rotate(45F,mRadius,mRadius)
        canvas.drawArc(mArcRect, 0F, 360F, false, mBgArcPaint)
        canvas.drawRect(mArcRect, mBgArcPaint)
        canvas.restore()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    private fun dipToPx(dip: Float): Int {
        val density = context.applicationContext.resources.displayMetrics.density
        return (dip * density + 0.5f * if (dip >= 0) 1 else -1).toInt()
    }


    companion object {
        private const val CIRCLE_DEGREE = 360
        private const val RIGHT_ANGLE_DEGREE = 90
        private const val MAX_PROGRESS = 100
    }

    init {
        mBgArcPaint.apply {
            isAntiAlias = true
            color = mBgArcColor
            style = Paint.Style.STROKE
            strokeWidth = mArcWidth
            strokeCap = Paint.Cap.ROUND
        }
    }
}