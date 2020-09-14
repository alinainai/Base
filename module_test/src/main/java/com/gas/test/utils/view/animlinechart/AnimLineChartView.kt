package com.gas.test.utils.view.animlinechart

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Scroller
import androidx.core.view.GestureDetectorCompat
import com.gas.test.R
import com.gas.test.utils.view.animlinechart.bean.CirclePoint
import com.gas.test.utils.view.animlinechart.bean.LineAndCircle
import com.gas.test.utils.view.animlinechart.bean.LineData
import com.gas.test.utils.view.animlinechart.bean.TitleClickRegionData
import com.gas.test.utils.view.animlinechart.callback.OnTitleClickListener
import com.lib.commonsdk.kotlin.extension.app.debug
import java.util.*


class AnimLineChartView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    companion object {
        val ANIMATOR_MIN_AND_MAX = intArrayOf(0, 100)
        const val DURATION = 3000
        const val SCROLL_DURATION = 500
        const val DATA_CLICK_OFFSET = 20
    }

    private var widthInit = 0
    private var heightInit = 0
    private var availableTop = 0f
    private var availableLeft = 0f
    private var availableRight = 0f
    private var availableBottom = 0f
    private var availableHeight = 0f
    private var availableWidth = 0f

    private val gridLinePaint = Paint()
    private var gridLineColor = -0x777778
    private var gridVerTextSize = 30
    private var gridLineWidth = 1f
    private val gridOutLineWidth = 0f //矩形刻度线宽

    private val linePaint = Paint()
    private var lineStrokeWidth = 8f
    private var innerCircleRadius = lineStrokeWidth

    private val titlePaint = Paint()
    private var titleColor = -0xaaaaab
    private var titleTextSize = 35

    private val circlePaint = Paint()
    private var tagTextSize = 30
    private var leftMargin = 0f

    private var min = 0f
    private var max = 100f
    private var density = 5

    private var titles = arrayOf("", "", "", "", "", "")
    var list = mutableListOf<LineData>()
    var titleRegionData = mutableListOf<TitleClickRegionData>()
    var tagCircles = mutableListOf<CirclePoint>()
    var dataLines = mutableListOf<LineAndCircle>()

    //animator
    var isShowAnimation = false
    private var animationEnd = false
    private val animator: ValueAnimator = ValueAnimator.ofFloat(ANIMATOR_MIN_AND_MAX[0].toFloat(), ANIMATOR_MIN_AND_MAX[1].toFloat()).apply {
        duration = DURATION.toLong()
        interpolator = LinearInterpolator()
    }

    //圆圈点击
    var circleClickIndex = intArrayOf(-1, -1)
    var tagCornerRadius = 5f
    var tagBorderWidth = 5f

    private var mDetector: GestureDetectorCompat
    private var tagpadding = 0
    private var tagMargin = 0f

    //滑动
    private var peerWidth = 0f
    private var isAllowScroll = false
    private val maxColumn = 6
    private var factRectWidth = 0f
    private var factRight = 0f
    private var factRectRight = 0f

    //平滑曲线
    private var besselCalculator = BesselCalculator()

    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnimLineChartView)
        typedArray.recycle()
    }

    private fun getCircleRadius(innerCircleRadius: Float): Float {
        return lineStrokeWidth + innerCircleRadius
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        widthInit = w
        heightInit = h
        setAvaiable()
        computeLines()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun setAvaiable() {
        if (width <= 0 || height <= 0 || titles.size == 0) {
            return
        }
        scrollTo(0, 0)
        tagpadding = gridVerTextSize
        tagMargin = getCircleRadius(innerCircleRadius) + gridVerTextSize / 3
        val leftWidth = getLeftWidth()
        availableLeft = leftWidth
        availableTop = tagMargin + getCircleRadius(innerCircleRadius) + gridVerTextSize + tagpadding + 10 //10为上方空隙,可为0,getCircleRadius为drawTag中三角形高度
        val rightPadding = Math.max(titlePaint.measureText(titles[titles.size - 1]) / 2,
                Math.max(circlePaint.measureText("$max.0") / 2 + tagpadding, circlePaint.measureText("$min.0")) / 2 + tagpadding)
        availableRight = width - rightPadding
        availableBottom = (height - titleTextSize * 2.5).toFloat()
        availableHeight = availableBottom - availableTop
        availableWidth = availableRight - availableLeft
        val titleCount = titles.size
        if (titleCount == 1) {
            peerWidth = availableWidth / 2
            factRectWidth = availableWidth
            factRectRight = availableLeft + factRectWidth
            factRight = factRectRight + rightPadding
        } else {
            if (!isAllowScroll) {
                peerWidth = availableWidth / (titleCount - 1)
                factRectWidth = availableWidth
                factRight = availableRight + rightPadding
                factRectRight = availableLeft + factRectWidth
            } else {
                var counmeCunt = if (maxColumn < titleCount - 1) maxColumn else titleCount - 1
                peerWidth = availableWidth / counmeCunt
                //避免title文字相互遮挡
                val maxTwoTitleLength = getMaxTwoTitleLenth()
                while (maxTwoTitleLength > peerWidth && peerWidth > 0) {
                    counmeCunt -= 1
                    peerWidth = availableWidth / counmeCunt
                }
                factRectWidth = peerWidth * (titleCount - 1)
                factRectRight = availableLeft + factRectWidth
                factRight = factRectRight + rightPadding
            }
        }
    }

    private fun getMaxTwoTitleLenth(): Float {
        val count = titles.size
        if (count == 0) {
            return 0f
        }
        var result = 0f
        for (i in 0 until count - 1) {
            val temp = titlePaint.measureText(titles[i]) / 2 + titlePaint.measureText(titles[i + 1]) / 2
            if (temp > result) {
                result = temp
            }
        }
        return result
    }

    private fun computeLines() {
        if (width <= 0 || height <= 0) {
            return
        }
        dataLines.clear()
        //circle click
        val dataCount = list.size
        if (dataCount <= 0) return
        val titleCount = titles.size
        val points: MutableList<Point> = ArrayList()
        //1列的情况
        if (titleCount == 1) {
            for (i in 0 until dataCount) {
                val data: LineData = list[i]
                val lineColor = data.lineColor
                val nums = data.nums
                val tagString = data.nums
                val numsCount = nums.size
                require(numsCount == titles.size) { "the data num's lengh must be " + titles.size + "!" }
                val circlePoints: MutableList<CirclePoint> = ArrayList()
                circlePoints.clear()
                val currentX = availableLeft + peerWidth
                var trueNum = nums[0]
                if (trueNum >= max) trueNum = max
                if (trueNum <= min) trueNum = min
                val currentY = availableBottom - (trueNum - min) * (availableBottom - availableTop) / (max - min)
                points.add(Point(currentX, currentY))
                //外圆
                circlePoints.add(CirclePoint(tagString[0], currentX, currentY))
                dataLines.add(LineAndCircle(lineColor, Path(), circlePoints))
            }
            return
        }

        //>=2列的情况
        for (i in 0 until dataCount) {
            val path = Path()
            val data: LineData = list[i]
            val lineColor = data.lineColor
            val tagBorderColor = data.tagBorderColor
            val nums = data.nums
            val numsCount = nums.size
            require(numsCount == titles.size) { "the data num's lengh must be " + titles.size + "!" }
            points.clear()
            val circlePoints: MutableList<CirclePoint> = ArrayList()
            circlePoints.clear()
            for (j in 0 until numsCount) {
                val currentX = availableLeft + j * peerWidth
                var trueNum = nums[j]
                if (trueNum >= max) trueNum = max
                if (trueNum <= min) trueNum = min
                val currentY = availableBottom - (trueNum - min) * (availableBottom - availableTop) / (max - min)
                points.add(Point(currentX, currentY))
                //外圆
                circlePoints.add(CirclePoint(currentX, currentY, trueNum, lineStrokeWidth + innerCircleRadius + DATA_CLICK_OFFSET))
            }
            //贝塞尔曲线
            val besselPoints = besselCalculator.computeBesselPoints(points)
            var j = 0
            while (j < besselPoints.size) {
                if (j == 0) {
                    path.moveTo(besselPoints[j].x, besselPoints[j].y)
                } else path.cubicTo(besselPoints[j - 2].x, besselPoints[j - 2].y, besselPoints[j - 1].x, besselPoints[j - 1].y, besselPoints[j].x, besselPoints[j].y)
                j = j + 3
            }
            dataLines.add(LineAndCircle(lineColor, path, circlePoints))
        }
    }

    var isFirst = true

    override fun onDraw(canvas: Canvas?) {
        if (width <= 0 || height <= 0 || titles.size == 0) {
            return
        }
        super.onDraw(canvas)
        if (isFirst) {
            isFirst = false
            if (isShowAnimation) {
                initAnimation()
                animator.start()
            } else {
                animatorLineAndCircleList.clear()
                animatorLineAndCircleList.addAll(dataLines)
            }
        }
        tagCircles.clear()
        drawCoordinate(canvas!!) //绘制刻度
        drawLineAndPoints(canvas) //绘制折线
        drawTagWithBack(canvas) //标签
    }

    // 绘制折线
    private fun drawLineAndPoints(canvas: Canvas) {
        val lineSize = animatorLineAndCircleList.size
        for (i in 0 until lineSize) {
            val lineAndCircle: LineAndCircle = animatorLineAndCircleList[i]
            linePaint.color = lineAndCircle.lineColor
            if (titles.size == 1) {
                drawCircleRing(i, lineAndCircle.circlePoints, lineAndCircle.lineColor, canvas)
            } else {
                if (!isShowAnimation) {
                    canvas.drawPath(lineAndCircle.path, linePaint)
                    //drawShadow(canvas, lineAndCircle);
                    drawCircleRing(i, lineAndCircle.circlePoints, lineAndCircle.lineColor, canvas)
                } else {
                    canvas.drawPath(lineAndCircle.path, linePaint)
                    if (animationEnd) {
                        //drawShadow(canvas, lineAndCircle);
                        drawCircleRing(i, lineAndCircle.circlePoints, lineAndCircle.lineColor, canvas)
                    }
                }
            }
        }
    }

    // 绘制点
    private fun drawCircleRing(lineIndex: Int, list: List<CirclePoint>, lineColor: Int, canvas: Canvas) {
        val isDrawTag = lineIndex == circleClickIndex[0]
        val circlrCount = list.size
        for (i in 0 until circlrCount) {
            val point = list[i]
            circlePaint.color = lineColor
            val outRadius = getCircleRadius(innerCircleRadius)
            val innerRadius = innerCircleRadius
            circlePaint.alpha = 255
            canvas.drawCircle(point.x, point.y, outRadius, circlePaint)

            if (circleClickIndex[1] == i && isDrawTag) {
                point.color = lineColor
                tagCircles.add(point)
            }
            if (!(circleClickIndex[1] == i && isDrawTag)) {
                circlePaint.alpha = 255
                circlePaint.color = -0x1
                canvas.drawCircle(point.x, point.y, innerRadius, circlePaint)
            }
        }
    }

    //标签--带背景
    private fun drawTagWithBack(canvas: Canvas) {
        val tagCircleCount: Int = tagCircles.size
        if (tagCircleCount == 0) {
            return
        }
        val sanjiaoHeight = getCircleRadius(innerCircleRadius)
        for (point in tagCircles) {
            val currentX = point.x
            val currentY = point.y
            val tagString = point.value.toString()
            if (TextUtils.isEmpty(tagString)) {
                continue
            }
            val numTrueWidth = circlePaint.measureText(tagString)
            var measureContentString: String = tagString
            if (tagString.length < 3) {
                measureContentString = "000"
            }
            val numMeasureWidth = circlePaint.measureText(measureContentString)
            val tagWidth = numMeasureWidth + 2 * tagpadding
            val tagRectHeight = tagTextSize + 2 * (tagpadding / 2).toFloat()
            circlePaint.alpha = 255
            //tag矩形
            circlePaint.color = point.color
            val tagLeft = currentX - tagWidth / 2
            val tagRight = tagLeft + tagWidth
            val tagRectBottom = currentY - tagMargin - sanjiaoHeight
            val tagRectTop = tagRectBottom - tagRectHeight
            val path = Path()
            path.moveTo((tagLeft + tagRight) / 2, tagRectTop) //1
            path.lineTo(tagRight - tagCornerRadius, tagRectTop) //2
            path.quadTo(tagRight, tagRectTop, tagRight, tagRectTop + tagCornerRadius) //3
            path.lineTo(tagRight, tagRectBottom - tagCornerRadius) //4
            path.quadTo(tagRight, tagRectBottom, tagRight - tagCornerRadius, tagRectBottom) //5
            path.lineTo(tagRight - tagWidth / 3 + tagCornerRadius, tagRectBottom) //6
            path.quadTo(tagRight - tagWidth / 3, tagRectBottom, tagRight - tagWidth / 3 - tagCornerRadius / 2, tagRectBottom + tagCornerRadius / 2) //7
            path.lineTo(currentX, currentY - tagMargin) //8
            path.lineTo(tagLeft + tagWidth / 3 + tagCornerRadius / 2, tagRectBottom + tagCornerRadius / 2) //9
            path.quadTo(tagLeft + tagWidth / 3, tagRectBottom, tagLeft + tagWidth / 3 - tagCornerRadius / 2, tagRectBottom) //10
            path.lineTo(tagLeft + tagCornerRadius, tagRectBottom) //11
            path.quadTo(tagLeft, tagRectBottom, tagLeft, tagRectBottom - tagCornerRadius) //12
            path.lineTo(tagLeft, tagRectTop + tagCornerRadius) //13
            path.quadTo(tagLeft, tagRectTop, tagLeft + tagCornerRadius, tagRectTop)
            path.close()
            canvas.drawPath(path, circlePaint)
            circlePaint.style = Paint.Style.STROKE
            circlePaint.strokeWidth = tagBorderWidth
            circlePaint.color = point.color
            canvas.drawPath(path, circlePaint)

            //num
            circlePaint.style = Paint.Style.FILL
            circlePaint.alpha = 255
            circlePaint.color = -0x1
            canvas.drawText(tagString, currentX - numTrueWidth / 2, tagRectBottom - tagpadding / 2 - (circlePaint.descent() - circlePaint.ascent() - circlePaint.textSize).toInt(), circlePaint)
        }
    }

    // 绘制刻度,包括:网格线,数字标尺,底部title
    private fun drawCoordinate(canvas: Canvas) {
        val peerCoordinateHeight = availableHeight / density
        gridLinePaint.style = Paint.Style.FILL
        gridLinePaint.strokeWidth = gridOutLineWidth
        canvas.drawLine(availableLeft, availableTop, factRectRight, availableTop, gridLinePaint) //上
        canvas.drawLine(availableLeft, availableBottom, factRectRight, availableBottom, gridLinePaint) //下
        canvas.drawLine(availableLeft, availableTop, availableLeft, availableBottom, gridLinePaint) //左
        canvas.drawLine(factRectRight, availableTop, factRectRight, availableBottom, gridLinePaint) //右

        //最大 最小刻度
        val graduationTextMaxY = availableTop + peerCoordinateHeight * 0
        val currentGraduationMAx: String = max.toInt().toString()
        val currentGraduationMaxTextWidth = gridLinePaint.measureText(currentGraduationMAx)
        canvas.drawText(currentGraduationMAx,
                availableLeft - currentGraduationMaxTextWidth - leftMargin, graduationTextMaxY + gridVerTextSize / 2,
                gridLinePaint)
        val graduationTextMinY = availableTop + peerCoordinateHeight * density
        val currentGraduationMin: String = min.toInt().toString()
        val currentGraduationMinTextWidth = gridLinePaint.measureText(currentGraduationMin)
        canvas.drawText(currentGraduationMin,
                availableLeft - currentGraduationMinTextWidth - leftMargin, graduationTextMinY + gridVerTextSize / 2,
                gridLinePaint)
        gridLinePaint.strokeWidth = gridLineWidth
        //canvas.drawRect(availableLeft, availableTop, factRectRight, availableBottom, coordinatePaint);
        //横向line
        for (i in 0 until density) {
            val currentY = availableTop + i * peerCoordinateHeight
            canvas.drawLine(availableLeft, currentY, factRectRight, currentY, gridLinePaint)
        }
        //数字刻度 不包含最大最小
        val totalDiff = max - min
        val peerDiff = totalDiff / density
        gridLinePaint.style = Paint.Style.FILL
        for (i in 1 until density) {
            val graduationTextY = availableTop + peerCoordinateHeight * i
            val currentGraduation: String = (max - i * peerDiff).toInt().toString()
            val currentGraduationTextWidth = gridLinePaint.measureText(currentGraduation)
            canvas.drawText(currentGraduation,
                    availableLeft - currentGraduationTextWidth - leftMargin, graduationTextY + gridVerTextSize / 2,
                    gridLinePaint)
        }


        //竖向line
        var verLineNum = titles.size - 2
        if (titles.size == 1) {
            verLineNum = 1
        }
        if (verLineNum > 0) {
            for (i in 1..verLineNum) {
                val currentX = availableLeft + i * peerWidth
                canvas.drawLine(currentX, availableTop, currentX, availableBottom, gridLinePaint)
            }
        }

        //title
        val offset = titleTextSize / 2.toFloat()
        titleRegionData.clear()
        val titleCount = titles.size
        val rectPadding = Math.min(8, titleTextSize / 2).toFloat()
        if (titleCount == 1) {
            val currentTitleWidth = titlePaint.measureText(titles[0])
            val titleCenterX = availableLeft + peerWidth
            val currentX = titleCenterX - currentTitleWidth / 2
            val currentY = height - rectPadding //  availableBottom + titleTextSize + getCircleRadius(innerCircleRadius);

            canvas.drawText(titles[0], currentX, currentY, titlePaint)
            val region = Region(
                    (currentX - offset).toInt(),
                    (currentY - titleTextSize).toInt(),
                    (currentX + currentTitleWidth + offset).toInt(),
                    (currentY + 2 * offset).toInt()
            )
            titleRegionData.add(TitleClickRegionData(region, 0, titles[0]))
            return
        }
        for (i in 0 until titleCount) {
            val currentTitleWidth = titlePaint.measureText(titles[i])
            val titleCenterX = availableLeft + i * peerWidth
            val currentX = titleCenterX - currentTitleWidth / 2
            val currentY = height - rectPadding // availableBottom + titleTextSize + getCircleRadius(innerCircleRadius);
            canvas.drawText(titles[i], currentX, currentY, titlePaint)
            //add region
            val region = Region(
                    (currentX - offset).toInt(),
                    (currentY - titleTextSize).toInt(),
                    (currentX + currentTitleWidth + offset).toInt(),
                    (currentY + 2 * offset).toInt()
            )
            titleRegionData.add(TitleClickRegionData(region, i, titles[i]))
        }
    }

    fun setLineSmoothness(smoothness: Float) {
        besselCalculator.setSmoothness(smoothness)
    }

    fun resetSmoothness() {
        besselCalculator.setSmoothness(0.4f)
    }

    fun setMin(min: Float) {
        this.min = min
    }

    fun setMax(max: Float) {
        this.max = max
    }

    fun setMinAndMax(min: Float, max: Float) {
        this.min = min
        this.max = max
    }

    private fun dipToPx(dip: Float): Int {
        val density = context.applicationContext.resources.displayMetrics.density
        return (dip * density + 0.5f * if (dip >= 0) 1 else -1).toInt()
    }

    fun setDensity(density: Int) {
        this.density = density
    }

    fun setTitles(titles: Array<String>) {
        this.titles = titles
    }

    fun addData(data: LineData) {
        list.add(data)
    }

    fun clearData() {
        list.clear()
        titles = arrayOf("", "", "", "", "")
    }

    fun setAllowScroll(allowScroll: Boolean) {
        isAllowScroll = allowScroll
    }

    fun startAnimation() {
        isShowAnimation = true
        initAnimation()
        if (dataLines.size == 0) {
            invalidate()
        }
        if (animator != null) {
            animator.cancel()
            animator.start()
        }
    }

    private var animatorLineAndCircleList: MutableList<LineAndCircle> = ArrayList()

    private fun initAnimation() {
        animationEnd = false
        animatorLineAndCircleList.clear()
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                animationEnd = false
            }

            override fun onAnimationCancel(animation: Animator) {
                animationEnd = false
            }

            override fun onAnimationEnd(animation: Animator) {
                animationEnd = true
            }
        })
        animator.removeAllUpdateListeners()
        for (peer in dataLines) {
            val lineAndCircle = LineAndCircle()
            lineAndCircle.path.moveTo(peer.circlePoints[0].x, peer.circlePoints[0].y)
            val start = floatArrayOf(0.0f)
            val pathMeasure = PathMeasure(peer.path, false)
            animator.addUpdateListener(AnimatorUpdateListener { animation ->
                val animatorValue = animation.animatedValue as Float / (ANIMATOR_MIN_AND_MAX[1] - ANIMATOR_MIN_AND_MAX[0]) * pathMeasure.length
                //硬件加速 你妈逼
                pathMeasure.getSegment(start[0], animatorValue, lineAndCircle.path, false)
                start[0] = animatorValue
                invalidate()
            })
            lineAndCircle.lineColor = peer.lineColor
            lineAndCircle.circlePoints.addAll(peer.circlePoints)
            animatorLineAndCircleList.add(lineAndCircle)
        }
    }

    fun commit() {
        setPaint()
        circleClickIndex = intArrayOf(-1, -1)
        setAvaiable()
        computeLines()
        if (titles.size == 1) {
            showDataLine()
            return
        }
        if (isShowAnimation) {
            startAnimation()
        } else {
            showDataLine()
        }
    }

    private fun setPaint() {
        gridLinePaint.color = gridLineColor
        gridLinePaint.textSize = gridVerTextSize.toFloat()
        gridLinePaint.strokeWidth = gridLineWidth
        titlePaint.color = titleColor
        titlePaint.textSize = titleTextSize.toFloat()
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = lineStrokeWidth
        circlePaint.textSize = tagTextSize.toFloat()
        innerCircleRadius = lineStrokeWidth
    }

    private fun getLeftWidth(): Float {
        val graduationTextWidth = measureGraduationTextWidth()
        leftMargin = Math.max(graduationTextWidth / 4, getCircleRadius(innerCircleRadius))
        val availableLeftmargin = graduationTextWidth + leftMargin
        if (titles[0] == null) {
            titles[0] = ""
        }
        return Math.max(availableLeftmargin, titlePaint.measureText(titles[0]) / 2)
    }

    private fun measureGraduationTextWidth(): Float {
        return gridLinePaint.measureText(max.toString()).coerceAtLeast(gridLinePaint.measureText(min.toString()))
    }

    private fun showDataLine() {
        animatorLineAndCircleList.clear()
        animatorLineAndCircleList.addAll(dataLines)
        invalidate()
    }

    var isNested = false

    fun requestDisallowIntercept(intercept: Boolean) {
        if (!isNested) {
            parent.requestDisallowInterceptTouchEvent(intercept)
        } else {
            var view = parent
            while (view != null) {
                view.requestDisallowInterceptTouchEvent(intercept)
                view = view.parent
            }
        }
    }

    val scroller: Scroller by lazy {
        Scroller(context, DecelerateInterpolator(2f))
    }

    private val touchSlop: Int by lazy {
        ViewConfiguration.get(context).scaledTouchSlop
    }

    init {
        attrs?.let {
            initAttrs(it)
        }

        setLayerType(LAYER_TYPE_SOFTWARE, null)
        isClickable = true

        gridLinePaint.isAntiAlias = true
        linePaint.isAntiAlias = true
        titlePaint.isAntiAlias = true

        circlePaint.isAntiAlias = true
        circlePaint.style = Paint.Style.FILL
        circlePaint.textSize = tagTextSize.toFloat()
        mDetector = GestureDetectorCompat(context, MyGestureListener())

    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, 0)
            postInvalidate()
        }
    }

    var lastX = 0f
    var lastY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val viewGroup = parent as ViewGroup
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
                val currentX = event.x
                val currentY = event.y
                val distanceX = currentX - lastX
                val distanceY = currentY - lastY
                debug("move--$isScrolling")
                debug("distanceX$distanceX--distanceY$distanceY")
                if (!isScrolling) {
                    if (Math.abs(distanceX) < Math.abs(distanceY)) {
                        requestDisallowIntercept(false)
                        return false //竖向滑动
                    }
                    val offset = width + scrollX - factRight.toInt()
                    if (scrollX == 0 && distanceX > 0 || offset == 0 && distanceX < 0) {
                        requestDisallowIntercept(false)
                        return false //横向滑动
                    }
                }
                requestDisallowIntercept(true)
                lastX = currentX
                lastY = currentY
            }
            MotionEvent.ACTION_MOVE -> {
                val currentX = event.x
                val currentY = event.y
                val distanceX = currentX - lastX
                val distanceY = currentY - lastY
                debug("move--$isScrolling")
                debug("distanceX$distanceX--distanceY$distanceY")
                if (!isScrolling) {
                    if (Math.abs(distanceX) < Math.abs(distanceY)) {
                        requestDisallowIntercept(false)
                        return false
                    }
                    val offset = width + scrollX - factRight.toInt()
                    if (scrollX == 0 && distanceX > 0 || offset == 0 && distanceX < 0) {
                        requestDisallowIntercept(false)
                        return false
                    }
                }
                requestDisallowIntercept(true)
                lastX = currentX
                lastY = currentY
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isScrolling = false
                lastX = 0f
                lastY = 0f
                //                viewGroup.requestDisallowInterceptTouchEvent(false);
                requestDisallowIntercept(false)
            }
        }
        return if (mDetector!!.onTouchEvent(event)) true else {
            if (event.action == MotionEvent.ACTION_UP) {
                isScrolling = false
                if (scrollX < 0) { //头部滑动超出边界,回退
                    scroller.startScroll(scrollX, 0, 0 - scrollX, 0, SCROLL_DURATION)
                    invalidate()
                    return true
                } else {
                    //尾部滑动超出边界,回退
                    val offset = width + scrollX - factRight.toInt()
                    if (offset > 0) {
                        scroller.startScroll(scrollX, 0, -offset, 0, SCROLL_DURATION)
                        invalidate()
                        return true
                    }
                }
            }
            false
        }
    }

    private var onTitleClickListener: OnTitleClickListener? = null

    fun setOnTitleClickListener(onTitleClickListener: OnTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener
    }

    fun getPressTitleIndex(x: Int, y: Int): Int {
        var xNew = x
        val size: Int = titleRegionData.size
        if (size < 1) {
            return -1
        }
        xNew += scrollX
        for (i in 0 until size) {
            val region: Region = titleRegionData[i].region
            if (region.contains(xNew, y)) {
                return i
            }
        }
        return -1
    }

    fun getPressCircleIndex(x: Int, y: Int): IntArray {
        var xNew = x
        xNew += scrollX
        var index = intArrayOf(-1, -1)
        val lineCount = animatorLineAndCircleList.size
        for (i in 0 until lineCount) {
            val dataline: LineAndCircle = animatorLineAndCircleList[i]
            val circleCount = dataline.circlePoints.size
            for (j in 0 until circleCount) {
                val circlePoint = dataline.circlePoints[j]
                if (circlePoint.clickRegion.contains(xNew, y)) {
                    index = intArrayOf(i, j)
                    return index
                }
            }
        }
        return index
    }

    var isScrolling = false

    inner class MyGestureListener : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val x = e.x.toInt()
            val y = e.y.toInt()
            val pressTitleIndex: Int = getPressTitleIndex(x, y)
            if (pressTitleIndex != -1) {
                onTitleClickListener?.onClick(titleRegionData[pressTitleIndex].title
                        ?: "", pressTitleIndex)
                return true
            }
            val pressCircleIndex: IntArray = getPressCircleIndex(x, y)
            if (pressCircleIndex[0] != -1 && pressCircleIndex[1] != -1) {
                circleClickIndex = pressCircleIndex
                invalidate()
                return true
            } else {
                circleClickIndex = intArrayOf(-1, -1)
                invalidate()
            }
            return super.onSingleTapUp(e)
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            if (!isAllowScroll) {
                return false
            }
            isScrolling = true
            requestDisallowIntercept(true)
            if (distanceX < 0) {
                if (getScrollX() + distanceX <= 0) {
                    scrollTo(0, 0)
                } else scrollBy(distanceX.toInt(), 0)
            }
            val offset: Int = width + getScrollX() - factRight as Int
            if (distanceX > 0) {
                if (offset + distanceX >= 0) {
                    scrollTo(factRight as Int - width, 0)
                } else scrollBy(distanceX.toInt(), 0)
            }
            return false
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (!isAllowScroll) {
                return false
            }
            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                if (Math.abs(velocityX) < 1000) {
                    return true
                }
                var distanceX = (Math.abs(velocityX) / 1000 * 150).toInt()
                if (velocityX < 0) { //左滑-->滑动至尾部
                    val leftWidth: Int = factRight as Int - Math.abs(getScrollX()) - width
                    if (Math.abs(distanceX) > leftWidth) {
                        distanceX = leftWidth
                    }
                } else { //右滑-->滑动至头部
                    if (Math.abs(distanceX) > Math.abs(getScrollX())) {
                        distanceX = Math.abs(getScrollX())
                    }
                    distanceX = -distanceX
                }
                if (getScrollX() < 0) { //头部超出边界
                    distanceX = -getScrollX()
                }
                scroller.startScroll(getScrollX(), 0, distanceX, 0, SCROLL_DURATION)
                invalidate()
                return true
            }
            return false
        }
    }

    data class Point(var x: Float = 0f, var y: Float = 0f)

}