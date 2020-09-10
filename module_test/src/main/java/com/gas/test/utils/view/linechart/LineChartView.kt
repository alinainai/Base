package com.gas.test.utils.view.linechart

import android.content.Context
import android.graphics.*
import android.graphics.Paint.Align
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.gas.test.R
import java.util.*
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * View for drawing line charts.
 */
class LineChartView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {
    private var mTouchSlop = 10f
    private val mLines = ArrayList<Line>()
    private var mViewPortLeft = 0f
    private var mViewPortRight = 0f
    private var mViewPortTop = 0f
    private var mViewPortBottom = 0f
    private var mViewPortMarginLeft = 0f
    private var mViewPortMarginRight = 0f
    private var mViewPortMarginTop = 0f
    private var mViewPortMarginBottom = 0f
    private var cropViewPortShader: Shader? = null
    private var cropHorValuesShader: Shader? = null
    private var cropVerValuesShader: Shader? = null
    private var mGrid = ChartGrid(context)
    private var mLastX = Int.MAX_VALUE.toFloat()
    private var mLastY = Int.MAX_VALUE.toFloat()
    private var mScaleX = 0f
    private var mScaleY = 0f
    private val mMatrix = Matrix()
    private var mMaxX = Float.MIN_VALUE
    private var mMaxY = Float.MIN_VALUE
    private var mMinX = Float.MAX_VALUE
    private var mMinY = Float.MAX_VALUE
    private var mViewPortHorFreedom = 0f
    private var mViewPortVerFreedom = 0f
    private var velocityX = 0f
    private var velocityY = 0f
    private var frictionX = 1f
    private var frictionY = 1f
    private var mFrictionForceX = 10f
    private var mFrictionForceY = 10f
    private var isMovingX = false
    private var isMovingY = false
    var pointClickRadius = 0f
    private var mListener: OnChartPointClickListener? = null

    interface OnChartPointClickListener {
        fun onPointClick(point: LinePoint, line: Line)
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        setViewPortMargins(0f, spToPx(20f), 0f, 0f)
        setVerValuesMargins(spToPx(8F).toInt(), spToPx(20F).toInt(), 0, 0)
        pointClickRadius = dpToPx(20F)
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop.toFloat()
        setViewPort(0f, 0f, 100f, 100f)
        attrs?.let {
            setAttr(attrs)
        }
    }

    private fun setAttr(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.LineChartView)
        var color = 0
        var dim = 0f
        mGrid.stepVer = a.getInt(R.styleable.LineChartView_horizontal_grid_step, mGrid.stepVer)
        mGrid.stepHor = a.getInt(R.styleable.LineChartView_vertical_grid_step, mGrid.stepHor)
        mGrid.horSubLinesCount = a.getInt(R.styleable.LineChartView_horizontal_grid_sublines_count,
                mGrid.horSubLinesCount)
        mGrid.verSubLinesCount = a.getInt(R.styleable.LineChartView_vertical_grid_sublines_count,
                mGrid.verSubLinesCount)
        mGrid.verMainLinesEnabled = a.getBoolean(R.styleable.LineChartView_vertical_grid, mGrid.verMainLinesEnabled)
        mGrid.verSubLinesEnabled = a.getBoolean(R.styleable.LineChartView_vertical_grid_sublines,
                mGrid.verSubLinesEnabled)
        mGrid.horMainLinesEnabled = a.getBoolean(R.styleable.LineChartView_horizontal_grid, mGrid.horMainLinesEnabled)
        mGrid.horSubLinesEnabled = a.getBoolean(R.styleable.LineChartView_horizontal_grid_sublines,
                mGrid.horSubLinesEnabled)
        color = a.getColor(R.styleable.LineChartView_horizontal_grid_color, mGrid.mainHorLinesPaint.color)
        mGrid.mainHorLinesPaint.color = color
        color = a.getColor(R.styleable.LineChartView_vertical_grid_color, mGrid.mainVerLinesPaint.color)
        mGrid.mainVerLinesPaint.color = color
        color = a.getColor(R.styleable.LineChartView_horizontal_grid_sublines_color, mGrid.subHorLinesPaint.color)
        mGrid.subHorLinesPaint.color = color
        color = a.getColor(R.styleable.LineChartView_vertical_grid_sublines_color, mGrid.subVerLinesPaint.color)
        mGrid.subVerLinesPaint.color = color
        // grid thikness
        dim = a.getDimension(R.styleable.LineChartView_horizontal_grid_thikness,
                mGrid.mainHorLinesPaint.strokeWidth)
        mGrid.mainHorLinesPaint.strokeWidth = dim
        dim = a.getDimension(R.styleable.LineChartView_vertical_grid_thikness, mGrid.mainVerLinesPaint.strokeWidth)
        mGrid.mainVerLinesPaint.strokeWidth = dim
        dim = a.getDimension(R.styleable.LineChartView_horizontal_grid_sublines_thikness,
                mGrid.subHorLinesPaint.strokeWidth)
        mGrid.subHorLinesPaint.strokeWidth = dim
        dim = a.getDimension(R.styleable.LineChartView_vertical_grid_sublines_thikness,
                mGrid.subVerLinesPaint.strokeWidth)
        mGrid.subVerLinesPaint.strokeWidth = dim
        // values color
        color = a.getColor(R.styleable.LineChartView_horizontal_values_color, mGrid.mainHorValuesPaint.color)
        mGrid.mainHorValuesPaint.color = color
        color = a.getColor(R.styleable.LineChartView_vertical_values_color, mGrid.mainVerValuesPaint.color)
        mGrid.mainVerValuesPaint.color = color
        dim = a.getDimension(R.styleable.LineChartView_horizontal_values_size, mGrid.mainHorValuesPaint.textSize)
        mGrid.mainHorValuesPaint.textSize = dim
        dim = a.getDimension(R.styleable.LineChartView_vertical_values_size, mGrid.mainVerValuesPaint.textSize)
        mGrid.mainVerValuesPaint.textSize = dim
        mGrid.horMainValuesEnabled = a.getBoolean(R.styleable.LineChartView_horizontal_values,
                mGrid.horMainValuesEnabled)
        mGrid.verMainValuesEnabled = a.getBoolean(R.styleable.LineChartView_vertical_values, mGrid.verMainValuesEnabled)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Log.i("", "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        scaleCorrection()
    }

    /**
     * Scale correction.
     */
    private fun scaleCorrection() {
        mScaleX = if (mViewPortRight - mViewPortLeft != 0f) (measuredWidth - mViewPortMarginLeft - mViewPortMarginRight) / (mViewPortRight - mViewPortLeft) else 1F
        mScaleY = if (mViewPortTop - mViewPortBottom != 0f) (measuredHeight - mViewPortMarginTop - mViewPortMarginBottom) / (mViewPortTop - mViewPortBottom) else 1F
    }

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isInEditMode) {
            return
        }
        inertionMove()
        if (cropViewPortShader == null) {
            val cropBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ALPHA_8)
            val cropCanvas = Canvas(cropBitmap)
            val cropPaint = Paint()
            cropPaint.color = -0x1
            cropPaint.style = Paint.Style.FILL
            cropCanvas.drawRect(mViewPortMarginLeft - 1,
                    mViewPortMarginTop - 1,
                    width - mViewPortMarginRight + 1,
                    height - mViewPortMarginBottom + 1,
                    cropPaint)
            cropViewPortShader = BitmapShader(cropBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        }
        drawVerticalGrid(canvas)
        drawHorizontalGrid(canvas)
        for (line in mLines) {
            drawLine(canvas, line)
        }
        drawPoints(canvas)
        drawValues(canvas)
    }

    //Draw line.
    private fun drawLine(canvas: Canvas, line: Line?) {
        var pathCopy: Path
        if (line!!.isFilled) {
            pathCopy = Path(line.filledPath)
            mMatrix.reset()
            mMatrix.setScale(mScaleX, -mScaleY)
            pathCopy.transform(mMatrix)
            mMatrix.reset()
            mMatrix.setTranslate(-mViewPortLeft * mScaleX + mViewPortMarginLeft, mViewPortTop * mScaleY
                    + mViewPortMarginTop)
            pathCopy.transform(mMatrix)
            line.filledPaint.shader = cropViewPortShader
            canvas.drawPath(pathCopy, line.filledPaint)
        }
        pathCopy = Path(line.path)
        mMatrix.reset()
        mMatrix.setScale(mScaleX, -mScaleY)
        pathCopy.transform(mMatrix)
        mMatrix.reset()
        mMatrix.setTranslate(-mViewPortLeft * mScaleX + mViewPortMarginLeft, mViewPortTop * mScaleY
                + mViewPortMarginTop)
        pathCopy.transform(mMatrix)
        line.paint.shader = cropViewPortShader
        canvas.drawPath(pathCopy, line.paint)
    }

    private fun drawPoints(canvas: Canvas) {
        for (line in mLines) {
            for (point in line.points) {
                drawPoint(canvas, point)
                drawPointText(canvas, point)
            }
        }
    }

    private fun drawPoint(canvas: Canvas, point: LinePoint) {
        if (point.isVisible) {
            val x = point.x * mScaleX - mViewPortLeft * mScaleX + mViewPortMarginLeft
            val y = point.y * -mScaleY + mViewPortTop * mScaleY + mViewPortMarginTop
            if (x + (point.radius + point.strokePaint.strokeWidth) > mViewPortMarginLeft && x - (point.radius + point.strokePaint.strokeWidth) < width - mViewPortMarginRight && y + (point.radius + point.strokePaint.strokeWidth) > mViewPortMarginTop && y - (point.radius + point.strokePaint.strokeWidth) < height - mViewPortMarginBottom) {
                point.fillPaint.shader = cropViewPortShader
                point.strokePaint.shader = cropViewPortShader
                when {
                    point.type === LinePoint.Type.CIRCLE -> {
                        canvas.drawCircle(x, y, point.radius, point.fillPaint)
                        canvas.drawCircle(x, y, point.radius, point.strokePaint)
                    }
                    point.type === LinePoint.Type.SQUARE -> {
                        canvas.drawRect(x - point.radius,
                                y - point.radius,
                                x + point.radius,
                                y + point.radius,
                                point.fillPaint)
                        canvas.drawRect(x - point.radius,
                                y - point.radius,
                                x + point.radius,
                                y + point.radius,
                                point.strokePaint)
                    }
                    point.type === LinePoint.Type.TRIANGLE -> {
                        val path = Path()
                        path.moveTo(x, y - point.radius)
                        path.lineTo(x - 0.86f * point.radius, y + 0.5f * point.radius)
                        path.lineTo(x + 0.86f * point.radius, y + 0.5f * point.radius)
                        path.close()
                        canvas.drawPath(path, point.fillPaint)
                        canvas.drawPath(path, point.strokePaint)
                    }
                }
            }
        }
    }

    private fun drawPointText(canvas: Canvas, point: LinePoint) {
        val x = point.x * mScaleX - mViewPortLeft * mScaleX + mViewPortMarginLeft
        val y = point.y * -mScaleY + mViewPortTop * mScaleY + mViewPortMarginTop
        if (point.isTextVisible) {
            point.textPaint.textAlign = Align.CENTER
            point.textPaint.shader = cropViewPortShader
            var txtX = x
            var txtY = y + (point.textPaint.textSize - point.textPaint.descent()) / 2
            if (point.textAlign and TextAlign.LEFT > 0) {
                point.textPaint.textAlign = Align.RIGHT
                txtX = x - point.radius - point.textPaint.descent()
            } else if (point.textAlign and TextAlign.RIGHT > 0) {
                point.textPaint.textAlign = Align.LEFT
                txtX = x + point.radius + point.textPaint.descent()
            }
            if (point.textAlign and TextAlign.TOP > 0) {
                txtY = y - point.radius - point.textPaint.descent()
            } else if (point.textAlign and TextAlign.BOTTOM > 0) {
                txtY = y + point.radius + point.textPaint.descent() + point.textPaint.textSize
            }
            canvas.drawText(point.text, txtX, txtY, point.textPaint)
        }
    }

    private fun drawVerticalGrid(canvas: Canvas) {
        val firstLertLineX = (mViewPortLeft.toInt() / mGrid.stepHor - 1) * mGrid.stepHor
        val subStep = mGrid.stepHor.toFloat() / (mGrid.horSubLinesCount + 1)
        mGrid.mainVerLinesPaint.shader = cropViewPortShader
        mGrid.subVerLinesPaint.shader = cropViewPortShader
        mGrid.mainHorValuesPaint.shader = cropHorValuesShader
        if (mGrid.verMainLinesEnabled) {
            var x = firstLertLineX
            while (x < mViewPortRight + mViewPortMarginRight / mScaleY) {
                canvas.drawLine((x - mViewPortLeft) * mScaleX + mViewPortMarginLeft,
                        mViewPortMarginTop,
                        (x - mViewPortLeft) * mScaleX + mViewPortMarginLeft,
                        height - mViewPortMarginBottom,
                        mGrid.mainVerLinesPaint)
                if (mGrid.verSubLinesEnabled) {
                    for (i in 1..mGrid.horSubLinesCount) {
                        canvas.drawLine((x + i * subStep - mViewPortLeft) * mScaleX + mViewPortMarginLeft,
                                mViewPortMarginTop,
                                (x + i * subStep - mViewPortLeft) * mScaleX + mViewPortMarginLeft,
                                height - mViewPortMarginBottom,
                                mGrid.subVerLinesPaint)
                    }
                }
                x += mGrid.stepHor
            }
        }
    }

    private fun drawHorizontalGrid(canvas: Canvas) {
        val firstHorLineY = mViewPortBottom.toInt() / mGrid.stepVer * mGrid.stepVer
        val subStep = mGrid.stepVer.toFloat() / (mGrid.verSubLinesCount + 1)
        mGrid.mainHorLinesPaint.shader = cropViewPortShader
        mGrid.subHorLinesPaint.shader = cropViewPortShader
        mGrid.mainVerValuesPaint.shader = cropVerValuesShader
        if (mGrid.horMainLinesEnabled) {
            var y = firstHorLineY
            while (y < mViewPortTop) {
                canvas.drawLine(mViewPortMarginLeft,
                        height - mViewPortMarginBottom - (y - mViewPortBottom) * mScaleY,
                        width - mViewPortMarginRight,
                        height - mViewPortMarginBottom - (y - mViewPortBottom) * mScaleY,
                        mGrid.mainHorLinesPaint)
                if (mGrid.horSubLinesEnabled) {
                    for (i in 1..mGrid.verSubLinesCount) {
                        canvas.drawLine(mViewPortMarginLeft,
                                height - mViewPortMarginBottom
                                        - ((y - mViewPortBottom + i * subStep)
                                        * mScaleY),
                                width - mViewPortMarginRight,
                                height - mViewPortMarginBottom
                                        - ((y - mViewPortBottom + i * subStep)
                                        * mScaleY),
                                mGrid.subHorLinesPaint)
                    }
                }
                y += mGrid.stepVer
            }
        }
    }

    private fun drawValues(canvas: Canvas) {
        drawVerticalValues(canvas)
        drawHorizontalValues(canvas)
    }

    private fun drawHorizontalValues(canvas: Canvas) {
        if (mGrid.horMainValuesEnabled) {
            if (cropHorValuesShader == null) {
                val cropHorValuesBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
                val cropCanvas = Canvas(cropHorValuesBitmap)
                val cropPaint = Paint()
                cropPaint.color = -0x1
                cropPaint.style = Paint.Style.FILL
                val descent: Float = mGrid.mainHorValuesPaint.descent()
                cropCanvas.drawRect(mGrid.horValuesMarginLeft.toFloat(),
                        mGrid.horValuesMarginTop.toFloat(),
                        width - mGrid.horValuesMarginRight.toFloat(),
                        height - mGrid.horValuesMarginBottom + descent,
                        cropPaint)
                cropHorValuesShader = BitmapShader(cropHorValuesBitmap,
                        Shader.TileMode.CLAMP,
                        Shader.TileMode.CLAMP)
            }
            val first = (((mViewPortLeft - mViewPortMarginLeft / mScaleX) / mGrid.stepHor).toInt() - 1) * mGrid.stepHor
            val last = (mViewPortRight + mViewPortMarginRight / mScaleX).toInt() + mGrid.stepHor
            // float subStep = (float) mGrid.stepHor / (mGrid.horSubLinesCount +1);
            mGrid.mainVerLinesPaint.shader = cropViewPortShader
            mGrid.subVerLinesPaint.shader = cropViewPortShader
            mGrid.mainHorValuesPaint.shader = cropHorValuesShader
            var x = first
            while (x <= last) {
                var string: String? = x.toString()
                if (mGrid.horValuesText != null) string = mGrid.horValuesText!![x]
                if (string == null) string = ""
                var txtY = height - 1 - mGrid.horValuesMarginBottom.toFloat()
                val txtX = (x - mViewPortLeft) * mScaleX + mViewPortMarginLeft
                if (mGrid.horValuesAlign and TextAlign.TOP > 0) {
                    txtY = mGrid.horValuesMarginTop + mGrid.mainHorValuesPaint.textSize
                } else if (mGrid.horValuesAlign and TextAlign.VERTICAL_CENTER > 0) {
                    txtY = (height - (mGrid.horValuesMarginTop + mGrid.horValuesMarginBottom) + mGrid.mainHorValuesPaint.textSize) / 2
                }
                if (mGrid.horValuesAlign and TextAlign.LEFT > 0) {
                    mGrid.mainHorValuesPaint.textAlign = Align.LEFT
                } else if (mGrid.horValuesAlign and TextAlign.RIGHT > 0) {
                    mGrid.mainHorValuesPaint.textAlign = Align.RIGHT
                } else if (mGrid.horValuesAlign and TextAlign.HORIZONTAL_CENTER > 0) {
                    mGrid.mainHorValuesPaint.textAlign = Align.CENTER
                }
                canvas.drawText(string, txtX, txtY, mGrid.mainHorValuesPaint)
                x += mGrid.stepHor
            }
        }
    }

    private fun drawVerticalValues(canvas: Canvas) {
        if (mGrid.verMainValuesEnabled) {
            if (cropVerValuesShader == null) {
                val cropBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
                val cropCanvas = Canvas(cropBitmap)
                val cropPaint = Paint()
                cropPaint.color = -0x1
                cropPaint.style = Paint.Style.FILL
                val descent: Float = mGrid.mainVerValuesPaint.descent()
                cropCanvas.drawRect(mGrid.verValuesMarginLeft.toFloat(),
                        mGrid.verValuesMarginTop.toFloat(),
                        width - mGrid.verValuesMarginRight.toFloat(),
                        height - mGrid.verValuesMarginBottom + descent,
                        cropPaint)
                cropVerValuesShader = BitmapShader(cropBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            }
            val firstHorLineY = ((mViewPortBottom - mViewPortMarginBottom / mScaleY).toInt() / mGrid.stepVer - 1) * mGrid.stepVer
            val last = (mViewPortTop + mViewPortMarginTop / mScaleY).toInt() + mGrid.stepVer
            // float subStep = (float) mGrid.stepVer / (mGrid.verSubLinesCount + 1);
            mGrid.mainVerValuesPaint.shader = cropVerValuesShader
            var y = firstHorLineY
            while (y < last) {
                var string: String? = y.toString()
                if (mGrid.verValuesText != null) string = mGrid.verValuesText!![y]
                if (string == null) string = ""
                var txtX = mGrid.verValuesMarginLeft
                var txtY = height - mViewPortMarginBottom - ((y - mViewPortBottom)
                        * mScaleY) - mGrid.mainVerValuesPaint.descent()
                if (mGrid.verValuesAlign and TextAlign.TOP > 0) {
                    txtY = ((height - mViewPortMarginBottom - (y - mViewPortBottom)
                            * mScaleY
                            ) + mGrid.mainVerValuesPaint.textSize
                            + mGrid.mainVerValuesPaint.descent())
                } else if (mGrid.verValuesAlign and TextAlign.VERTICAL_CENTER > 0) {
                    txtY = (height - mViewPortMarginBottom - (y - mViewPortBottom)
                            * mScaleY
                            + (mGrid.mainVerValuesPaint.textSize - mGrid.mainVerValuesPaint.descent())
                            / 2)
                }
                if (mGrid.verValuesAlign and TextAlign.LEFT > 0) {
                    mGrid.mainVerValuesPaint.textAlign = Align.LEFT
                } else if (mGrid.verValuesAlign and TextAlign.RIGHT > 0) {
                    mGrid.mainVerValuesPaint.textAlign = Align.RIGHT
                    txtX = width - mGrid.verValuesMarginRight
                } else if (mGrid.verValuesAlign and TextAlign.HORIZONTAL_CENTER > 0) {
                    mGrid.mainVerValuesPaint.textAlign = Align.CENTER
                    txtX = (width - mGrid.verValuesMarginLeft - mGrid.verValuesMarginRight) / 2
                }
                canvas.drawText(string, txtX.toFloat(), txtY, mGrid.mainVerValuesPaint)
                y += mGrid.stepVer
            }
        }
    }

    private var downX = 0f
    private var downY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = x
                mLastY = y
                downX = x
                downY = y
            }
            MotionEvent.ACTION_MOVE -> {
                velocityX = (mLastX - x) / mScaleX
                velocityY = (mLastY - y) / mScaleY
                moveViewPort(velocityX, velocityY)
                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_UP -> if (Math.abs(downX - x) < mTouchSlop && Math.abs(downY - y) < mTouchSlop) {
                if (mListener != null) findClickedVerticle(x, y)
            } else {
                frictionX = -Math.signum(velocityX) * Math.abs(mViewPortRight - mViewPortLeft) / 1000 * mFrictionForceX
                frictionY = -Math.signum(velocityY) * Math.abs(mViewPortTop - mViewPortBottom) / 1000 * mFrictionForceY
                isMovingY = true
                isMovingX = isMovingY
            }
        }
        postInvalidate()
        return true
    }

    private fun findClickedVerticle(x: Float, y: Float) {
        if (x < mViewPortMarginLeft || x > width - mViewPortMarginRight || y > height - mViewPortMarginBottom || y < mViewPortMarginTop) {
            return
        }
        val gX = (x - mViewPortMarginLeft) / mScaleX + mViewPortLeft
        val gY = (height - y - mViewPortMarginBottom) / mScaleY + mViewPortBottom
        Log.i("", "$gX,$gY")
        var point: LinePoint? = null
        var line: Line? = null
        var minR = Float.MAX_VALUE
        for (l in mLines) for (p in l.points) {
            if (gX >= p.x - pointClickRadius / mScaleX && (gX <= p.x + pointClickRadius / mScaleX) &&
                    gY >= p.y - pointClickRadius / mScaleY && gY <= p.y + pointClickRadius / mScaleY) {
                val r = sqrt((gX - p.x) * (gX - p.x) + (gY - p.y) * (gY - p.y).toDouble()).toFloat()
                if (minR > r) {
                    minR = r
                    point = p
                    line = l
                }
            }
        }
        mListener?.apply {
            point?.let {
                onPointClick(it, line!!)
            }
        }
    }

    // Move ViewPort.
    fun moveViewPort(deltaX: Float, deltaY: Float) {
        if (mViewPortLeft + deltaX > mMinX - mViewPortHorFreedom && mViewPortRight + deltaX < mMaxX + mViewPortHorFreedom) {
            mViewPortLeft += deltaX
            mViewPortRight += deltaX
        } else if (deltaX > 0) {
            mViewPortLeft += mMaxX + mViewPortHorFreedom - mViewPortRight
            mViewPortRight = mMaxX + mViewPortHorFreedom
            isMovingX = false
        } else if (deltaX < 0) {
            mViewPortRight += mMinX - mViewPortHorFreedom - mViewPortLeft
            mViewPortLeft = mMinX - mViewPortHorFreedom
            isMovingX = false
        }
        if (mViewPortBottom - deltaY > mMinY - mViewPortVerFreedom && mViewPortTop - deltaY < mMaxY + mViewPortVerFreedom) {
            mViewPortBottom -= deltaY
            mViewPortTop -= deltaY
        } else if (deltaY < 0) {
            mViewPortBottom -= mMaxY + mViewPortVerFreedom - mViewPortTop
            mViewPortTop = mMaxY + mViewPortVerFreedom
            isMovingY = false
        } else if (deltaY < 0) {
            mViewPortTop -= mMinY - mViewPortVerFreedom - mViewPortBottom
            mViewPortBottom = mMinY - mViewPortVerFreedom
            isMovingY = false
        }
    }

    private fun inertionMove() {
        if (isMovingX || isMovingY) {
            if (isMovingX) {
                val signX = Math.signum(velocityX).toInt()
                velocityX += frictionX
                if (signX == Math.signum(velocityX).toInt()) moveViewPort(velocityX, 0f) else isMovingX = false
            }
            if (isMovingY) {
                val signY = Math.signum(velocityY).toInt()
                velocityY += frictionY
                if (signY == Math.signum(velocityY).toInt()) moveViewPort(0f, velocityY) else isMovingY = false
            }
            postInvalidate()
        }
    }

    //Sets the stopping force for inertial scrolling.
    fun setFriction(fx: Float, fy: Float) {
        mFrictionForceX = abs(fx)
        mFrictionForceY = abs(fy)
    }

    //Sets the additional movement for ViewPort after limits Max and Min values of LinePoints.
    fun setViewPortFreedom(hor: Float, ver: Float) {
        mViewPortHorFreedom = hor
        mViewPortVerFreedom = ver
    }

    //Sets the vertical grid lines style using [Paint] for drawing
    fun setVerticalGridStyle(main: Paint?, sub: Paint?) {
        if (main != null) mGrid.mainVerLinesPaint = main
        if (sub != null) mGrid.subVerLinesPaint = sub
    }

    //Sets the horizontal grid lines style using [Paint] for drawing
    fun setHorizontalGridStyle(main: Paint?, sub: Paint?) {
        if (main != null) mGrid.mainHorLinesPaint = main
        if (sub != null) mGrid.subHorLinesPaint = sub
    }

    // Show or hide vertical grid lines.
    fun enableVerticalGrid(main: Boolean, sub: Boolean) {
        mGrid.verMainLinesEnabled = main
        mGrid.verSubLinesEnabled = sub
    }

    //how or hide horizontal grid lines.
    fun enableHorizontalGrid(main: Boolean, sub: Boolean) {
        mGrid.horMainLinesEnabled = main
        mGrid.horSubLinesEnabled = sub
    }

    //Sets steps between main grid lines. Grid lines staring from 0 value.
    fun setGridSize(horStep: Int, horSubLinesCount: Int, verStep: Int, verSubLinesCount: Int) {
        mGrid.stepHor = horStep
        mGrid.stepVer = verStep
        mGrid.horSubLinesCount = horSubLinesCount
        mGrid.verSubLinesCount = verSubLinesCount
    }

    //Sets [Paint] for drawing values for grid.
    fun setMainValuesStyle(hor: Paint?, ver: Paint?) {
        if (hor != null) mGrid.mainHorValuesPaint = hor
        if (ver != null) mGrid.mainVerValuesPaint = ver
    }

    //Show or hide values on grid.
    fun enableMainValues(hor: Boolean, ver: Boolean) {
        mGrid.horMainValuesEnabled = hor
        mGrid.verMainValuesEnabled = ver
    }

    //Sets margins for horizontal grid values from view sides in pixels.
    fun setHorValuesMargins(left: Int, bottom: Int, right: Int, top: Int, isDp: Boolean = true) {
        mGrid.horValuesMarginLeft = if (isDp) dpToPx(left.toFloat()).toInt() else left
        mGrid.horValuesMarginRight = if (isDp) dpToPx(right.toFloat()).toInt() else right
        mGrid.horValuesMarginTop = if (isDp) dpToPx(top.toFloat()).toInt() else top
        mGrid.horValuesMarginBottom = if (isDp) dpToPx(bottom.toFloat()).toInt() else bottom
        cropHorValuesShader = null
    }

    //Sets margins for vertical grid values from view sides in pixels.
    fun setVerValuesMargins(left: Int, bottom: Int, right: Int, top: Int, isDp: Boolean = true) {
        mGrid.verValuesMarginLeft = if (isDp) dpToPx(left.toFloat()).toInt() else left
        mGrid.verValuesMarginRight = if (isDp) dpToPx(right.toFloat()).toInt() else right
        mGrid.verValuesMarginTop = if (isDp) dpToPx(top.toFloat()).toInt() else top
        mGrid.verValuesMarginBottom = if (isDp) dpToPx(bottom.toFloat()).toInt() else bottom
        cropVerValuesShader = null
    }

    //Sets custom text for horizontal values. Use [SparseArray] for mapping grid value and text for
    //it. If no text for value, grid line will drawn without text.
    fun setHorValuesText(map: SparseArray<String>) {
        mGrid.horValuesText = map
    }

    //Sets custom text for vertical values. Use [SparseArray] for mapping grid value and text for
    //it. If no text for value, grid line will drawn without text.
    fun setVerValuesText(map: SparseArray<String>) {
        mGrid.verValuesText = map
    }

    //Removes all lines.
    fun removeAllLines() {
        while (mLines.size > 0) {
            mLines.removeAt(0)
        }
        postInvalidate()
    }

    //Adds the line for drawing.
    fun addLine(line: Line) {
        mLines.add(line)
        postInvalidate()
        for (point in line.points) {
            mMaxX = if (point.x > mMaxX) point.x else mMaxX
            mMaxY = if (point.y > mMaxY) point.y else mMaxY
            mMinX = if (point.x < mMinX) point.x else mMinX
            mMinY = if (point.y < mMinY) point.y else mMinY
        }
    }

    //Swaps the position of two lines
    fun swapLines(indexFirst: Int, indexSecond: Int): Boolean {
        var result = true
        try {
            Collections.swap(mLines, indexFirst, indexSecond)
        } catch (ie: IndexOutOfBoundsException) {
            result = false
        }
        return result
    }

    //Remove lines by name
    fun removeLinesWithName(name: String): Int {
        val toRemove: MutableList<Line?> = ArrayList()
        for (line in mLines) {
            if (line.name == name) {
                toRemove.add(line)
            }
        }
        for (r in toRemove) {
            mLines.remove(r)
        }
        postInvalidate()
        return toRemove.size
    }

    //Correct min and max values for ViewPort moving limits.
    private fun limitsCorrection() {
        mMaxX = Float.MIN_VALUE
        mMaxY = Float.MIN_VALUE
        mMinX = Float.MAX_VALUE
        mMinY = Float.MAX_VALUE
        for (line in mLines) for (point in line.points) {
            mMaxX = point.x.coerceAtLeast(mMaxX)
            mMaxY = point.y.coerceAtLeast(mMaxY)
            mMinX = point.x.coerceAtMost(mMinX)
            mMinY = point.y.coerceAtMost(mMinY)
        }
        mMaxX = mMaxX.coerceAtLeast(mViewPortRight)
        mMaxY = mMaxY.coerceAtLeast(mViewPortTop)
        mMinX = mMinX.coerceAtMost(mViewPortLeft)
        mMinY = mMinY.coerceAtMost(mViewPortBottom)
    }

    fun getLine(index: Int): Line? {
        return mLines[index]
    }

    val linesCount: Int
        get() = mLines.size

    //Sets ViewPort - part of full chart for drawing
    fun setViewPort(left: Float, bottom: Float, right: Float, top: Float) {
        mViewPortLeft = left
        mViewPortRight = right
        mViewPortTop = top
        mViewPortBottom = bottom
        limitsCorrection()
    }

    //Sets ViewPort margins from view sides in pixels.
    fun setViewPortMargins(left: Float, bottom: Float, right: Float, top: Float, isDp: Boolean = true) {
        mViewPortMarginLeft = if (isDp) dpToPx(left) else left
        mViewPortMarginRight = if (isDp) dpToPx(right) else right
        mViewPortMarginTop = if (isDp) dpToPx(top) else top
        mViewPortMarginBottom = if (isDp) dpToPx(bottom) else bottom
        cropViewPortShader = null
        scaleCorrection()
    }

    fun setOnPointClickListener(listener: OnChartPointClickListener?) {
        mListener = listener
    }

    private fun dpToPx(dip: Float): Float {
        val density = context.applicationContext.resources.displayMetrics.density
        return dip * density + 0.5f * if (dip >= 0) 1 else -1
    }

    private fun spToPx(sp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)
    }

}