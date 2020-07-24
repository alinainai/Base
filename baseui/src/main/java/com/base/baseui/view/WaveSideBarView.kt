package com.base.baseui.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.base.baseui.R
import com.lib.commonsdk.kotlin.extension.app.dpToPx
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/**
 *
 */
class WaveSideBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {
    private var listener: OnTouchLetterChangeListener? = null
    private var mLetters = mutableListOf<String>() // 渲染字母表

    // 当前选中的位置
    private var mChoose = -1
    private var oldChoose = 0
    private var newChoose = 0

    // 字母列表画笔
    private val mLettersPaint = Paint()

    // 提示字母画笔
    private val mTextPaint = Paint()

    // 波浪画笔
    private var mTipWavePaint = Paint()
    private var mTipWaveColor = 0
    private var mTipTextSize = 32.dpToPx()
    private var mTipBallRadius = 24.dpToPx().toInt() // 圆形半径
    private val mTipWavePath = Path()  // 波浪路径
    private val mTipBallPath = Path() // 圆形路径

    private var mTextSize = 10.dpToPx()
    private var mTextColor = 0
    private var mTextColorChoose = 0

    private var mWidth = 0
    private var mHeight = 0
    private var mItemHeight = 20.dpToPx()

    private var mCenterY = 0//中心点Y = 0 // 手指滑动的Y点作为中心点
    private var mRadius = 20.dpToPx().toInt()  // 贝塞尔曲线的分布半径
    private val mRatioAnimator: ValueAnimator by lazy {
        ValueAnimator()
    }

    // 用于过渡效果计算
    private var mRatio = 0F  // 用于绘制贝塞尔曲线的比率
    private var mPosX = 0F  // 选中字体的坐标
    private var mPosY = 0F
    private var mBallCentreX = 0F  // 圆形中心点X
    private var startDrawLetterPosY = 0F  //绘制letter的起始位置
    private var endDrawLetterPosY = 0F  //绘制letter的结束位置
    private var letterBgCircleRadius = 7.dpToPx()

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.WaveSideBarView)
            mTextColor = a.getColor(R.styleable.WaveSideBarView_sidebarTextColor, Color.parseColor("#666666"))
            mTextColorChoose = a.getColor(R.styleable.WaveSideBarView_sidebarChooseTextColor, Color.parseColor("#FFFFFF"))
            mTipWaveColor = a.getColor(R.styleable.WaveSideBarView_sidebarBackgroundColor, Color.parseColor("#CC459DF5"))
            a.recycle()
        }
        mTipWavePaint = Paint()
        mTipWavePaint.isAntiAlias = true
        mTipWavePaint.style = Paint.Style.FILL
        mTipWavePaint.color = mTipWaveColor

        mTextPaint.isAntiAlias = true
        mTextPaint.color = mTextColorChoose
        mTextPaint.style = Paint.Style.FILL
        mTextPaint.textSize = mTipTextSize
        mTextPaint.textAlign = Paint.Align.CENTER

        mLettersPaint.isAntiAlias = true
        mLettersPaint.textSize = mTextSize
        mLettersPaint.textAlign = Paint.Align.CENTER
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
        mWidth = measuredWidth
        mPosX = mWidth - 1.3f * mTextSize
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLetters(canvas)  //绘制字母列表
        drawWavePath(canvas)  //绘制波浪线
        drawBallPath(canvas)  //绘制选中字母展示的圆形图案
        drawChooseText(canvas)  //绘制选中的字体
    }

    private fun drawLetters(canvas: Canvas) {

        mLetters.let {
            startDrawLetterPosY = (mHeight - mItemHeight * it.size) / 2
            endDrawLetterPosY = startDrawLetterPosY + mItemHeight * it.size
            val fontMetrics = mLettersPaint.fontMetrics
            val baseline = abs(-fontMetrics.bottom - fontMetrics.top)
            it.forEachIndexed { i, s ->
                val posY = startDrawLetterPosY + mItemHeight / 2 + mItemHeight * i + baseline / 2
                if (i == mChoose) {
                    drawLetterSelectBg(canvas, i)
                    mLettersPaint.color = mTextColorChoose
                    canvas.drawText(s, mPosX, posY, mLettersPaint)
                    mPosY = posY
                } else {
                    mLettersPaint.color = mTextColor
                    canvas.drawText(s, mPosX, posY, mLettersPaint)
                }
            }
        }
    }

    private fun drawLetterSelectBg(canvas: Canvas, i: Int) {
        canvas.drawCircle(mPosX, startDrawLetterPosY + mItemHeight * i + mItemHeight / 2, letterBgCircleRadius, mTipWavePaint)
    }

    /**
     * 绘制波浪
     */
    private fun drawWavePath(canvas: Canvas) {
        mTipWavePath.reset()
        // 移动到起始点
        mTipWavePath.moveTo(mWidth.toFloat(), mCenterY - 3 * mRadius.toFloat())
        //计算上部控制点的Y轴位置
        val controlTopY = mCenterY - 2 * mRadius
        //计算上部结束点的坐标
        val endTopX = (mWidth - mRadius * cos(ANGLE) * mRatio).toInt()
        val endTopY = (controlTopY + mRadius * sin(ANGLE)).toInt()
        mTipWavePath.quadTo(mWidth.toFloat(), controlTopY.toFloat(), endTopX.toFloat(), endTopY.toFloat())

        //计算中心控制点的坐标
        val controlCenterX = (mWidth - 1.8f * mRadius * sin(ANGLE_R) * mRatio).toInt()
        val controlCenterY = mCenterY
        //计算下部结束点的坐标
        val controlBottomY = mCenterY + 2 * mRadius
        val endBottomY = (controlBottomY - mRadius * cos(ANGLE)).toInt()
        mTipWavePath.quadTo(controlCenterX.toFloat(), controlCenterY.toFloat(), endTopX.toFloat(), endBottomY.toFloat())
        mTipWavePath.quadTo(mWidth.toFloat(), controlBottomY.toFloat(), mWidth.toFloat(), controlBottomY + mRadius.toFloat())
        mTipWavePath.close()
        canvas.drawPath(mTipWavePath, mTipWavePaint)
    }

    private fun drawBallPath(canvas: Canvas) {
        //x轴的移动路径
        mBallCentreX = mWidth + mTipBallRadius - (2.0f * mRadius + 2.0f * mTipBallRadius) * mRatio
        mTipBallPath.reset()
        mTipBallPath.addCircle(mBallCentreX, mCenterY.toFloat(), mTipBallRadius.toFloat(), Path.Direction.CW)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTipBallPath.op(mTipWavePath, Path.Op.DIFFERENCE)
        }
        mTipBallPath.close()
        canvas.drawPath(mTipBallPath, mTipWavePaint)
    }

    private fun drawChooseText(canvas: Canvas) {
        mChoose.takeIf { it in mLetters.indices }?.let { choose ->
            // 绘制提示字符
            mRatio.takeIf { ratio -> ratio >= 0.9F }?.let {
                val target = mLetters[choose]
                val fontMetrics = mTextPaint.fontMetrics
                val baseline = abs(-fontMetrics.bottom - fontMetrics.top)
                val x = mBallCentreX
                val y = mCenterY + baseline / 2
                canvas.drawText(target, x, y, mTextPaint)
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        mLetters.size.takeIf { it > 0 }?.let {
            val y = event.y
            val x = event.x
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (x < mWidth - 2 * mRadius) {
                        return false
                    }
                    if (y < startDrawLetterPosY || y > endDrawLetterPosY) {
                        return false
                    }
                    oldChoose = mChoose
                    newChoose = ((y - startDrawLetterPosY) / mItemHeight).toInt()
                    mCenterY = handleCenterY(y.toInt())
                    startAnimator(mRatio, 1.0f)
                    listener?.onBarTouchDown()
                }
                MotionEvent.ACTION_MOVE -> {
                    oldChoose = mChoose
                    newChoose = ((y - startDrawLetterPosY) / mItemHeight).toInt()
                    mCenterY = handleCenterY(y.toInt())
                    if (oldChoose != newChoose && newChoose in mLetters.indices) {
                        mChoose = newChoose
                        listener?.onLetterChange(mLetters[newChoose])
                    }
                    invalidate()
                    listener?.onBarTouchMove()
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                    startAnimator(mRatio, 0f)
                    listener?.onBarTouchCancel()
                }
                else -> {
                }
            }
        }
        return true
    }

    private fun startAnimator(vararg value: Float) {
        mRatioAnimator.apply {
            cancel()
            setFloatValues(*value)
            addUpdateListener { value ->
                mRatio = value.animatedValue as Float
                //球弹到位的时候，并且点击的位置变了，即点击的时候显示当前选择位置
                if (mRatio == 1F && oldChoose != newChoose && newChoose in mLetters.indices) {
                    mChoose = newChoose
                    listener?.onLetterChange(mLetters[newChoose])
                }
                invalidate()
            }
            start()
        }
    }

    private fun handleCenterY(y: Int): Int {
        return when {
            y <= startDrawLetterPosY + mItemHeight / 2 -> {
                (startDrawLetterPosY + mItemHeight / 2).toInt()
            }
            y >= endDrawLetterPosY - mItemHeight / 2 -> {
                (endDrawLetterPosY - mItemHeight / 2).toInt()
            }
            else -> y
        }
    }

    fun setOnTouchLetterChangeListener(listener: OnTouchLetterChangeListener) {
        this.listener = listener
    }

    var letters: List<String>
        get() = mLetters
        set(letters) {
            mLetters.clear()
            mLetters.addAll(letters)
            invalidate()
        }

    fun setSelectChar(char: String) {
        mLetters.indexOfFirst { it == char }.takeIf { it > -1 }?.let {
            mChoose=it
            invalidate()
        }
    }

    interface OnTouchLetterChangeListener {
        fun onLetterChange(letter: String)
        fun onBarTouchDown()
        fun onBarTouchMove()
        fun onBarTouchCancel()
    }

    companion object {
        private const val TAG = "WaveSlideBarView"

        // 计算波浪贝塞尔曲线的角弧长值
        private const val ANGLE = Math.PI * 45 / 180
        private const val ANGLE_R = Math.PI * 90 / 180
    }

    init {
        init(attrs)
    }
}