package com.base.baseui.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.base.baseui.R
import com.lib.commonsdk.kotlin.extension.dpToPx

/**
 *
 */
class WaveSideBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {
    private var listener: OnTouchLetterChangeListener? = null

    // 渲染字母表
    private var mLetters: List<String>? = null

    // 当前选中的位置
    private var mChoose = -1
    private var oldChoose = 0
    private var newChoose = 0

    // 字母列表画笔
    private val mLettersPaint = Paint()

    // 提示字母画笔
    private val mTextPaint = Paint()

    // 波浪画笔
    private var mWavePaint = Paint()
    private var mTextSize = 0f
    private var mLargeTextSize = 0f
    private var mTextColor = 0
    private var mWaveColor = 0
    private var mTextColorChoose = 0
    private var mWidth = 0
    private var mHeight = 0
    private var mItemHeight = 0
    private var mPadding = 0

    // 波浪路径
    private val mWavePath = Path()

    // 圆形路径
    private val mBallPath = Path()

    // 手指滑动的Y点作为中心点
    private var mCenterY = 0//中心点Y = 0

    // 贝塞尔曲线的分布半径
    private var mRadius = 0

    // 圆形半径
    private var mBallRadius = 0

    // 用于过渡效果计算
    var mRatioAnimator: ValueAnimator? = null

    // 用于绘制贝塞尔曲线的比率
    private var mRatio = 0f

    // 选中字体的坐标
    private var mPosX = 0f
    private var mPosY = 0f

    // 圆形中心点X
    private var mBallCentreX = 0f

    private fun init(attrs: AttributeSet?) {
        mLetters = listOf(*context.resources.getStringArray(R.array.waveSideBarLetters))
        mTextColor = Color.parseColor("#969696")
        mWaveColor = Color.parseColor("#be69be91")
        mTextColorChoose = context.resources.getColor(android.R.color.white)
        mPadding = context.resources.getDimensionPixelSize(R.dimen.textSize_sidebar_padding)
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.WaveSideBarView)
            mTextColor = a.getColor(R.styleable.WaveSideBarView_sidebarTextColor, mTextColor)
            mTextColorChoose = a.getColor(R.styleable.WaveSideBarView_sidebarChooseTextColor, mTextColorChoose)
            mTextSize = a.getFloat(R.styleable.WaveSideBarView_sidebarTextSize, 10.dpToPx())
            mLargeTextSize = a.getFloat(R.styleable.WaveSideBarView_sidebarLargeTextSize, 32.dpToPx())
            mWaveColor = a.getColor(R.styleable.WaveSideBarView_sidebarBackgroundColor, mWaveColor)
            mRadius = a.getInt(R.styleable.WaveSideBarView_sidebarRadius, 20.dpToPx().toInt())
            mBallRadius = a.getInt(R.styleable.WaveSideBarView_sidebarBallRadius, 24.dpToPx().toInt())
            a.recycle()
        }
        mWavePaint = Paint()
        mWavePaint.isAntiAlias = true
        mWavePaint.style = Paint.Style.FILL
        mWavePaint.color = mWaveColor
        mTextPaint.isAntiAlias = true
        mTextPaint.color = mTextColorChoose
        mTextPaint.style = Paint.Style.FILL
        mTextPaint.textSize = mLargeTextSize
        mTextPaint.textAlign = Paint.Align.CENTER
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val y = event.y
        val x = event.x
        oldChoose = mChoose
        newChoose = (y / mHeight * mLetters!!.size).toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (x < mWidth - 2 * mRadius) {
                    return false
                }
                mCenterY = y.toInt()
                startAnimator(mRatio, 1.0f)
            }
            MotionEvent.ACTION_MOVE -> {
                mCenterY = y.toInt()
                if (oldChoose != newChoose) {
                    if (newChoose >= 0 && newChoose < mLetters!!.size) {
                        mChoose = newChoose
                        if (listener != null) {
                            listener!!.onLetterChange(mLetters!![newChoose])
                        }
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                startAnimator(mRatio, 0f)
                mChoose = -1
            }
            else -> {
            }
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
        mWidth = measuredWidth
        mItemHeight = (mHeight - mPadding) / mLetters!!.size
        mPosX = mWidth - 1.6f * mTextSize
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制字母列表
        drawLetters(canvas)

        //绘制波浪
        drawWavePath(canvas)

        //绘制圆
        drawBallPath(canvas)

        //绘制选中的字体
        drawChooseText(canvas)
    }

    private fun drawLetters(canvas: Canvas) {
        val rectF = RectF()
        rectF.left = mPosX - mTextSize
        rectF.right = mPosX + mTextSize
        rectF.top = mTextSize / 2
        rectF.bottom = mHeight - mTextSize / 2
        mLettersPaint.reset()
        mLettersPaint.style = Paint.Style.FILL
        mLettersPaint.color = Color.parseColor("#F9F9F9")
        mLettersPaint.isAntiAlias = true
        canvas.drawRoundRect(rectF, mTextSize, mTextSize, mLettersPaint)
        mLettersPaint.reset()
        mLettersPaint.style = Paint.Style.STROKE
        mLettersPaint.color = mTextColor
        mLettersPaint.isAntiAlias = true
        canvas.drawRoundRect(rectF, mTextSize, mTextSize, mLettersPaint)
        for (i in mLetters!!.indices) {
            mLettersPaint.reset()
            mLettersPaint.color = mTextColor
            mLettersPaint.isAntiAlias = true
            mLettersPaint.textSize = mTextSize
            mLettersPaint.textAlign = Paint.Align.CENTER
            val fontMetrics = mLettersPaint.fontMetrics
            val baseline = Math.abs(-fontMetrics.bottom - fontMetrics.top)
            val posY = mItemHeight * i + baseline / 2 + mPadding
            if (i == mChoose) {
                mPosY = posY
            } else {
                canvas.drawText(mLetters!![i], mPosX, posY, mLettersPaint)
            }
        }
    }

    private fun drawChooseText(canvas: Canvas) {
        if (mChoose != -1) {
            // 绘制右侧选中字符
            mLettersPaint.reset()
            mLettersPaint.color = mTextColorChoose
            mLettersPaint.textSize = mTextSize
            mLettersPaint.textAlign = Paint.Align.CENTER
            canvas.drawText(mLetters!![mChoose], mPosX, mPosY, mLettersPaint)

            // 绘制提示字符
            if (mRatio >= 0.9f) {
                val target = mLetters!![mChoose]
                val fontMetrics = mTextPaint.fontMetrics
                val baseline = Math.abs(-fontMetrics.bottom - fontMetrics.top)
                val x = mBallCentreX
                val y = mCenterY + baseline / 2
                canvas.drawText(target, x, y, mTextPaint)
            }
        }
    }

    /**
     * 绘制波浪
     *
     * @param canvas
     */
    private fun drawWavePath(canvas: Canvas) {
        mWavePath.reset()
        // 移动到起始点
        mWavePath.moveTo(mWidth.toFloat(), mCenterY - 3 * mRadius.toFloat())
        //计算上部控制点的Y轴位置
        val controlTopY = mCenterY - 2 * mRadius

        //计算上部结束点的坐标
        val endTopX = (mWidth - mRadius * Math.cos(ANGLE) * mRatio).toInt()
        val endTopY = (controlTopY + mRadius * Math.sin(ANGLE)).toInt()
        mWavePath.quadTo(mWidth.toFloat(), controlTopY.toFloat(), endTopX.toFloat(), endTopY.toFloat())

        //计算中心控制点的坐标
        val controlCenterX = (mWidth - 1.8f * mRadius * Math.sin(ANGLE_R) * mRatio).toInt()
        val controlCenterY = mCenterY
        //计算下部结束点的坐标
        val controlBottomY = mCenterY + 2 * mRadius
        val endBottomY = (controlBottomY - mRadius * Math.cos(ANGLE)).toInt()
        mWavePath.quadTo(controlCenterX.toFloat(), controlCenterY.toFloat(), endTopX.toFloat(), endBottomY.toFloat())
        mWavePath.quadTo(mWidth.toFloat(), controlBottomY.toFloat(), mWidth.toFloat(), controlBottomY + mRadius.toFloat())
        mWavePath.close()
        canvas.drawPath(mWavePath, mWavePaint)
    }

    private fun drawBallPath(canvas: Canvas) {
        //x轴的移动路径
        mBallCentreX = mWidth + mBallRadius - (2.0f * mRadius + 2.0f * mBallRadius) * mRatio
        mBallPath.reset()
        mBallPath.addCircle(mBallCentreX, mCenterY.toFloat(), mBallRadius.toFloat(), Path.Direction.CW)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mBallPath.op(mWavePath, Path.Op.DIFFERENCE)
        }
        mBallPath.close()
        canvas.drawPath(mBallPath, mWavePaint)
    }

    private fun startAnimator(vararg value: Float) {
        if (mRatioAnimator == null) {
            mRatioAnimator = ValueAnimator()
        }
        mRatioAnimator!!.cancel()
        mRatioAnimator!!.setFloatValues(*value)
        mRatioAnimator!!.addUpdateListener { value ->
            mRatio = value.animatedValue as Float
            //球弹到位的时候，并且点击的位置变了，即点击的时候显示当前选择位置
            if (mRatio == 1f && oldChoose != newChoose) {
                if (newChoose >= 0 && newChoose < mLetters!!.size) {
                    mChoose = newChoose
                    if (listener != null) {
                        listener!!.onLetterChange(mLetters!![newChoose])
                    }
                }
            }
            invalidate()
        }
        mRatioAnimator!!.start()
    }

    fun setOnTouchLetterChangeListener(listener: OnTouchLetterChangeListener?) {
        this.listener = listener
    }

    var letters: List<String>?
        get() = mLetters
        set(letters) {
            mLetters = letters
            invalidate()
        }

    interface OnTouchLetterChangeListener {
        fun onLetterChange(letter: String?)
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