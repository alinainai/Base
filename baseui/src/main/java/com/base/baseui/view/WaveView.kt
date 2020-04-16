package com.base.baseui.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.Keep
import com.base.baseui.R
import kotlin.math.min


/**
 * ================================================
 * desc: View生命周期
 *
 * https://www.jianshu.com/p/08e6dab7886e
 *
 * created by author ljx
 * Date  2020/4/9
 * email lijiaxing@360.cn
 *
 * ================================================
 */
class WaveView : View {


    enum class WaveStyle(val style: Int) {
        STROKE(0),
        FILL(1)
    }

    private val defaultCenterColor: Int = Color.parseColor("#66FFE6")
    private val defaultCenterRadius = 4F
    private val defaultMaxRadius =14F
    private val defaultWaveIntervalTime = 500
    private val defaultWaveDuration = 1500
    private val defaultWaveWidth = 1.0F

    /**
     * 中心圆的颜色
     */
    private var centerColor: Int

    /**
     * 水波纹的颜色
     */
    private var waveColor: Int

    /**
     * 中心圆的半径
     */
    private var centerRadius: Int
    /**
     * 波纹最大半径
     */
    private var maxRadius: Int
    /**
     * 波纹产生间隔
     */
    private var waveIntervalTime :Int
    /**
     * 单个波纹持续时长
     */
    private var waveDuration :Int
    /**
     * 单个波纹圆环宽度
     */
    private var waveWidth :Int

    private var waveStyle:Int
    /**
     * 是否在产生波纹
     */
    private var running: Boolean = false
    /**
     * 波纹集合
     */
    private var waveList = mutableListOf<Wave>()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {

        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.WaveView, defStyleAttr, 0)
        waveColor = typedArray.getColor(R.styleable.WaveView_waveColor, defaultCenterColor)
        centerColor = typedArray.getColor(R.styleable.WaveView_waveCenterColor, defaultCenterColor)
        centerRadius = typedArray.getDimension(R.styleable.WaveView_waveCenterRadius, defaultCenterRadius).toInt()
        maxRadius = typedArray.getDimension(R.styleable.WaveView_waveMaxRadius, defaultMaxRadius).toInt()
        waveWidth = typedArray.getDimension(R.styleable.WaveView_waveWidth, defaultWaveWidth).toInt()
        waveIntervalTime = typedArray.getInt(R.styleable.WaveView_waveIntervalTime, defaultWaveIntervalTime)
        waveDuration = typedArray.getInt(R.styleable.WaveView_waveDuration, defaultWaveDuration)
        waveStyle = typedArray.getInt(R.styleable.WaveView_waveStyle, WaveStyle.STROKE.style)
        paint.color = centerColor
        typedArray.recycle()

    }

    fun setWaveStart(waveStart: Boolean) {
        if (waveStart) {
            if (!running) {
                running = true
                waveList.add(Wave())
            }
        } else {
            running = false
            waveList.forEach {
                it.cancelAnimation()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val radius = (min(w-paddingStart-paddingEnd, h-paddingTop-paddingBottom) / 2.0f).toInt()
        if (radius < maxRadius) {
            maxRadius = radius
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCenterCircle(canvas)
        drawWave(canvas)
    }

    private fun drawCenterCircle(canvas: Canvas){
        paint.reset()
        paint.color=centerColor
        paint.alpha = 255
        paint.style = Paint.Style.FILL
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), centerRadius.toFloat(), paint)
    }

    private fun drawWave(canvas: Canvas){
        paint.reset()
        paint.color=waveColor
        paint.strokeWidth = waveWidth.toFloat()
        paint.style = if(waveStyle==WaveStyle.STROKE.style) Paint.Style.STROKE else Paint.Style.FILL
        waveList.forEach {
            paint.alpha = it.getAlpha()
            canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), it.getCurrentRadius(), paint)
        }
    }




    private inner class Wave {

        private var hasCreateNewWave = false

        private val createWaveAnimation = ObjectAnimator.ofFloat(this, "percent", 0f, 1.0f)
                .apply {
                    this.interpolator = LinearInterpolator()
                    this.duration = waveDuration.toLong()
                    this.start()
                    this.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            if (running) {
                                waveList.remove(this@Wave)
                            }
                        }
                    })
                }

        var percent: Float = 0f
            @Keep
            set(value) {
                field = value
                if (running && value >= waveIntervalTime.toFloat() / waveDuration.toFloat() && !hasCreateNewWave) {
                    waveList.add(Wave())
                    hasCreateNewWave = true
                }
                invalidate()
            }

        fun cancelAnimation() {
            createWaveAnimation.cancel()
        }

        fun getAlpha(): Int {
            return (255 * (1 - percent)).toInt()
        }

        fun getCurrentRadius(): Float {
            return centerRadius + percent * (maxRadius - centerRadius)
        }
    }

    override fun onDetachedFromWindow() {
        running = false
        waveList.forEach {
            it.cancelAnimation()
        }
        super.onDetachedFromWindow()
    }
}