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
import com.gas.test.utils.view.animlinechart.bean.DataPoint
import com.gas.test.utils.view.animlinechart.bean.LineInChart
import com.gas.test.utils.view.animlinechart.bean.XLabel
import com.gas.test.utils.view.animlinechart.callback.OnLabelClickListener
import com.lib.commonsdk.kotlin.extension.app.debug
import org.jetbrains.anko.collections.forEachWithIndex
import kotlin.math.abs


class AnimLineChartView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    companion object {
        val ANIMATOR_MIN_AND_MAX = intArrayOf(0, 100)
        const val DURATION = 800L
        const val SCROLL_DURATION = 500
        const val DATA_CLICK_OFFSET = 20
        val INIT_X_LABELS = listOf(XLabel("0", ""),
                XLabel("1", ""),
                XLabel("2", ""),
                XLabel("3", ""),
                XLabel("4", ""))
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
    private var gridLineColor = Color.parseColor("#F5F6F8")
    private var gridVerTextSize = dp2Px(10F)
    private var gridLineWidth = dp2Px(0.5F)
    private val gridOutLineWidth = dp2Px(0.5F) //矩形刻度线宽

    private val linePaint = Paint()
    private var lineStrokeWidth = 8f
    private var innerCircleRadius = lineStrokeWidth

    private val titlePaint = Paint()
    private var titleColor = Color.parseColor("#A1AAB4")
    private var titleTextSize = dp2Px(12F)

    private val circlePaint = Paint()
    private var leftMargin = 0f

    private var tagCircles = mutableListOf<DataPoint>()
    private var dataLines = mutableListOf<LineInChart>()
    private var validDataLines = mutableListOf<LineInChart>()
    private var xLabels = mutableListOf<XLabel>().apply {
        addAll(INIT_X_LABELS)
    }
    private var animatorLineAndCircleList = mutableListOf<LineInChart>()

    //animator
    var showAnimation = false
    private var animationEnd = false
    private val animator: ValueAnimator = ValueAnimator.ofFloat(ANIMATOR_MIN_AND_MAX[0].toFloat(), ANIMATOR_MIN_AND_MAX[1].toFloat()).apply {
        duration = DURATION
        interpolator = LinearInterpolator()
    }

    //平滑曲线
    private var besselCalculator = com.gas.test.utils.view.animlinechart.BesselCalculator()

    //圆圈点击
    var circleClickIndex = intArrayOf(-1, -1)
    private var mDetector: GestureDetectorCompat
    private var tagPaddingVer = dp2Px(6F)
    private var tagPaddingHor = dp2Px(7F)
    private var tagMargin = dp2Px(5F)
    var tagCornerRadius = dp2Px(2f)
    var tagBorderWidth = 1f
    private var tagTextSize = dp2Px(12F)
    private var tagTextColor = Color.parseColor("#6D7075")
    private var tagBgColor = Color.parseColor("#ECECEC")

    private var peerWidth = 0f
    private val maxColumn = 6
    private var factRectWidth = 0f
    private var factRight = 0f
    private var factRectRight = 0f
    var allowScroll = false //滑动
    var isFirst = true
    var onLabelClickListener: OnLabelClickListener? = null
    var isScrolling = false
    var min = 0f
    var max = 100f
    var density = 5

    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnimLineChartView)
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        widthInit = w
        heightInit = h
        setAvailable()
        computeLines()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        if (width <= 0 || height <= 0 || xLabels.isEmpty()) {
            return
        }
        super.onDraw(canvas)
        if (isFirst) {
            isFirst = false
            if (showAnimation) {
                initAnimation()
                animator.start()
            } else {
                animatorLineAndCircleList.clear()
                animatorLineAndCircleList.addAll(validDataLines)
            }
        }
        tagCircles.clear()
        canvas?.let {
            drawCoordinate(it) //绘制刻度
            drawLineAndPoints(it) //绘制折线
            drawTagWithBack(it) //标签
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
        val currentGraduationMaxTextWidth = titlePaint.measureText(currentGraduationMAx)
        canvas.drawText(currentGraduationMAx,
                availableLeft - currentGraduationMaxTextWidth - leftMargin,
                graduationTextMaxY + gridVerTextSize / 2,
                titlePaint)
        val graduationTextMinY = availableTop + peerCoordinateHeight * density
        val currentGraduationMin: String = min.toInt().toString()
        val currentGraduationMinTextWidth = titlePaint.measureText(currentGraduationMin)
        canvas.drawText(currentGraduationMin,
                availableLeft - currentGraduationMinTextWidth - leftMargin,
                graduationTextMinY + gridVerTextSize / 2,
                titlePaint)
        gridLinePaint.strokeWidth = gridLineWidth

        //横向line
        for (i in 0 until density) {
            val currentY = availableTop + i * peerCoordinateHeight
            canvas.drawLine(availableLeft, currentY, factRectRight, currentY, gridLinePaint)
        }

        //数字刻度 不包含最大最小
        val peerDiff = (max - min) / density
        titlePaint.style = Paint.Style.FILL
        for (i in 1 until density) {
            val graduationTextY = availableTop + peerCoordinateHeight * i
            val currentGraduation: String = (max - i * peerDiff).toInt().toString()
            val currentGraduationTextWidth = titlePaint.measureText(currentGraduation)
            canvas.drawText(currentGraduation,
                    availableLeft - currentGraduationTextWidth - leftMargin,
                    graduationTextY + gridVerTextSize / 2,
                    titlePaint)
        }

        //竖向line
        var verLineNum = xLabels.size - 2
        if (xLabels.size == 1) {
            verLineNum = 1
        }
        if (verLineNum > 0) {
            for (i in 1..verLineNum) {
                val currentX = availableLeft + i * peerWidth
                canvas.drawLine(currentX, availableTop, currentX, availableBottom, gridLinePaint)
            }
        }

        //xLabel
        val offset = titleTextSize / 2.toFloat()
        val titleCount = xLabels.size
        val rectPadding = 8F.coerceAtMost(titleTextSize / 2F)
        if (titleCount == 1) {
            val currentTitleWidth = titlePaint.measureText(xLabels[0].title)
            val titleCenterX = availableLeft + peerWidth
            val currentX = titleCenterX - currentTitleWidth / 2
            val currentY = height - rectPadding //  availableBottom + titleTextSize + getCircleRadius(innerCircleRadius);

            canvas.drawText(xLabels[0].title, currentX, currentY, titlePaint)
            val region = Region(
                    (currentX - offset).toInt(),
                    (currentY - titleTextSize).toInt(),
                    (currentX + currentTitleWidth + offset).toInt(),
                    (currentY + 2 * offset).toInt()
            )
            xLabels[0].region = region
            return
        }
        for (i in 0 until titleCount) {
            val currentTitleWidth = titlePaint.measureText(xLabels[i].title)
            val titleCenterX = availableLeft + i * peerWidth
            val currentX = titleCenterX - currentTitleWidth / 2
            val currentY = height - rectPadding // availableBottom + titleTextSize + getCircleRadius(innerCircleRadius);
            canvas.drawText(xLabels[i].title, currentX, currentY, titlePaint)
            //add region
            val region = Region(
                    (currentX - offset).toInt(),
                    (currentY - titleTextSize).toInt(),
                    (currentX + currentTitleWidth + offset).toInt(),
                    (currentY + 2 * offset).toInt()
            )
            xLabels[i].region = region
        }
    }

    // 绘制折线
    private fun drawLineAndPoints(canvas: Canvas) {
        val lineSize = animatorLineAndCircleList.size
        for (i in 0 until lineSize) {
            val lineAndCircle = animatorLineAndCircleList[i]
            linePaint.color = lineAndCircle.lineColor
            if (xLabels.size == 1) {
                drawCircleRing(i, lineAndCircle.circlePoints, lineAndCircle.lineColor, canvas)
            } else {
                if (!showAnimation) {
                    canvas.drawPath(lineAndCircle.path, linePaint)
                    drawCircleRing(i, lineAndCircle.circlePoints, lineAndCircle.lineColor, canvas)
                } else {
                    canvas.drawPath(lineAndCircle.path, linePaint)
                    if (animationEnd) {
                        drawCircleRing(i, lineAndCircle.circlePoints, lineAndCircle.lineColor, canvas)
                    }
                }
            }
        }
    }

    // 绘制点
    private fun drawCircleRing(lineIndex: Int, list: List<DataPoint>, lineColor: Int, canvas: Canvas) {
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
        if (tagCircles.isEmpty())
            return
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
            val tagWidth = numMeasureWidth + 2 * tagPaddingHor
            val tagRectHeight = tagTextSize + 2 * (tagPaddingVer / 2)
            //tag矩形
            val tagLeft = currentX - tagWidth / 2
            val tagRectBottom = currentY - tagMargin - sanjiaoHeight
            val tagRectTop = tagRectBottom - tagRectHeight
            val tagRight = tagLeft + tagWidth
            circlePaint.alpha = 255
            circlePaint.style = Paint.Style.FILL
            circlePaint.strokeWidth = tagBorderWidth
            circlePaint.color = tagBgColor
            canvas.drawRoundRect(RectF().apply {
                set(tagLeft, tagRectTop, tagRight, tagRectBottom)
            }, tagCornerRadius, tagCornerRadius, circlePaint)

            //num
            circlePaint.style = Paint.Style.FILL
            circlePaint.color = tagTextColor
            canvas.drawText(tagString,
                    currentX - numTrueWidth / 2,
                    tagRectBottom - tagPaddingVer / 2 - (circlePaint.descent() - circlePaint.ascent() - circlePaint.textSize).toInt(),
                    circlePaint)
        }
    }

    private fun setAvailable() {
        if (width <= 0 || height <= 0 || xLabels.isEmpty()) {
            return
        }
        scrollTo(0, 0)
        availableLeft = getLeftWidth()
        availableTop = tagMargin + getCircleRadius(innerCircleRadius) + gridVerTextSize + tagPaddingVer + 10 //10为上方空隙,可为0,getCircleRadius为drawTag中三角形高度
        val rightPadding = (titlePaint.measureText(xLabels[xLabels.size - 1].title) / 2)
                .coerceAtLeast((circlePaint.measureText("$max.0") / 2 + tagPaddingHor)
                        .coerceAtLeast(circlePaint.measureText("$min.0")) / 2 + tagPaddingHor)
        availableRight = width - rightPadding
        availableBottom = (height - titleTextSize * 2.5).toFloat()
        availableHeight = availableBottom - availableTop
        availableWidth = availableRight - availableLeft
        val titleCount = xLabels.size
        when (xLabels.size) {
            1 -> {
                peerWidth = availableWidth / 2
                factRectWidth = availableWidth
                factRectRight = availableLeft + factRectWidth
                factRight = factRectRight + rightPadding
            }
            else -> {
                if (!allowScroll) {
                    peerWidth = availableWidth / (titleCount - 1)
                    factRectWidth = availableWidth
                    factRight = availableRight + rightPadding
                    factRectRight = availableLeft + factRectWidth
                } else {
                    var counmeCunt = if (maxColumn < titleCount - 1) maxColumn else titleCount - 1
                    peerWidth = availableWidth / counmeCunt
                    //避免title文字相互遮挡
                    val maxTwoTitleLength = getMaxTwoXLabelLength()
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
    }

    private fun computeLines() {
        if (width <= 0 || height <= 0 || dataLines.isEmpty()) {
            return
        }
        //circle click
        val titleCount = xLabels.size
        val points = mutableListOf<Point>()
        //1列的情况
        if (titleCount == 1) {
            dataLines.forEach { line ->
                val lineColor = line.lineColor
                val circlePoints = mutableListOf<DataPoint>()
                val currentX = availableLeft + peerWidth
                val keyPoint = line.circlePoints.firstOrNull { point ->
                    point.key == xLabels.first().key
                }
                if (keyPoint != null) {
                    var trueNum = keyPoint.value
                    trueNum = validValueInRange(trueNum)
                    val currentY = availableBottom - (trueNum - min) * (availableBottom - availableTop) / (max - min)
                    points.add(Point(currentX, currentY))
                    //外圆
                    val clickOffset = lineStrokeWidth + innerCircleRadius + DATA_CLICK_OFFSET
                    keyPoint.x = currentX
                    keyPoint.y = currentY
                    keyPoint.region = Region().apply {
                        set((keyPoint.x - clickOffset).toInt(),
                                (keyPoint.y - clickOffset).toInt(),
                                (keyPoint.x + clickOffset).toInt(),
                                (keyPoint.y + clickOffset).toInt())
                    }
                    circlePoints.add(keyPoint)
                }
                validDataLines.add(LineInChart(circlePoints, lineColor))
            }
            return
        }

        //>=2列的情况
        dataLines.forEach { line ->
            points.clear()
            val lineColor = line.lineColor
            val circlePoints = mutableListOf<DataPoint>()
            xLabels.forEachWithIndex { index, label ->
                val currentX = availableLeft + index * peerWidth
                val keyPoint = line.circlePoints.firstOrNull { point ->
                    point.key == label.key
                }
                if (keyPoint != null) {
                    val trueNum = validValueInRange(keyPoint.value)
                    val currentY = availableBottom - (trueNum - min) * (availableBottom - availableTop) / (max - min)
                    points.add(Point(currentX, currentY))
                    //外圆
                    val clickOffset = lineStrokeWidth + innerCircleRadius + DATA_CLICK_OFFSET
                    keyPoint.x = currentX
                    keyPoint.y = currentY
                    keyPoint.region = Region().apply {
                        set((keyPoint.x - clickOffset).toInt(),
                                (keyPoint.y - clickOffset).toInt(),
                                (keyPoint.x + clickOffset).toInt(),
                                (keyPoint.y + clickOffset).toInt())
                    }
                    circlePoints.add(keyPoint)
                }
            }
            val path = Path()
            if (titleCount > 1) {
                //贝塞尔曲线
                val besselPoints = besselCalculator.computeBesselPoints(points)
                var j = 0
                while (j < besselPoints.size) {
                    if (j == 0) {
                        path.moveTo(besselPoints[j].x, besselPoints[j].y)
                    } else path.cubicTo(besselPoints[j - 2].x, besselPoints[j - 2].y, besselPoints[j - 1].x, besselPoints[j - 1].y, besselPoints[j].x, besselPoints[j].y)
                    j += 3
                }
            }
            validDataLines.add(LineInChart(circlePoints, lineColor).apply { this.path = path })
        }
    }

    private fun getCircleRadius(innerCircleRadius: Float): Float {
        return lineStrokeWidth + innerCircleRadius
    }

    private fun validValueInRange(value: Float): Float {
        if (value >= max) return max
        if (value <= min) return min
        return value
    }

    private fun getMaxTwoXLabelLength(): Float {
        val count = xLabels.size
        if (count == 0) {
            return 0f
        }
        var result = 0f
        for (i in 0 until count - 1) {
            val temp = titlePaint.measureText(xLabels[i].title) / 2 + titlePaint.measureText(xLabels[i + 1].title) / 2
            if (temp > result) {
                result = temp
            }
        }
        return result
    }

    fun setLineSmoothness(smoothness: Float) {
        besselCalculator.setSmoothness(smoothness)
    }

    fun resetSmoothness() {
        besselCalculator.setSmoothness(0.4f)
    }

    fun setTitles(titles: List<XLabel>) {
        xLabels.clear()
        xLabels.addAll(titles)
    }

    fun addData(data: LineInChart) {
        dataLines.add(data)
    }

    fun clearData() {
        dataLines.clear()
    }

    private fun startAnimation() {
        showAnimation = true
        initAnimation()
        if (validDataLines.isEmpty()) {
            invalidate()
        }
        animator.cancel()
        animator.start()
    }

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
        for (peer in validDataLines) {
            val lineAndCircle = LineInChart(peer.circlePoints, peer.lineColor)
            lineAndCircle.path.moveTo(peer.circlePoints[0].x, peer.circlePoints[0].y)
            val start = floatArrayOf(0.0f)
            val pathMeasure = PathMeasure(peer.path, false)
            animator.addUpdateListener(AnimatorUpdateListener { animation ->
                val animatorValue = animation.animatedValue as Float / (ANIMATOR_MIN_AND_MAX[1] - ANIMATOR_MIN_AND_MAX[0]) * pathMeasure.length
                pathMeasure.getSegment(start[0], animatorValue, lineAndCircle.path, false)
                start[0] = animatorValue
                invalidate()
            })
            animatorLineAndCircleList.add(lineAndCircle)
        }
    }

    fun commit() {
        setPaint()
        circleClickIndex = intArrayOf(-1, -1)
        setAvailable()
        computeLines()
        if (xLabels.size == 1) {
            showDataLine()
            return
        }
        if (showAnimation) {
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
        leftMargin = (graduationTextWidth / 4).coerceAtLeast(getCircleRadius(innerCircleRadius))
        val availableLeftmargin = graduationTextWidth + leftMargin
        var xLabelLeftLength = ""
        if (xLabels.isNotEmpty()) {
            xLabelLeftLength = xLabels[0].title
        }
        return Math.max(availableLeftmargin, titlePaint.measureText(xLabelLeftLength) / 2)
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
                    if (kotlin.math.abs(distanceX) < kotlin.math.abs(distanceY)) {
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
                    if (abs(distanceX) < abs(distanceY)) {
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
                requestDisallowIntercept(false)
            }
        }
        return if (mDetector.onTouchEvent(event)) true else {
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

    fun getPressTitleIndex(x: Int, y: Int): Int {
        var xNew = x
        val size: Int = xLabels.size
        if (size < 1) {
            return -1
        }
        xNew += scrollX
        for (i in 0 until size) {
            val region: Region = xLabels[i].region
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
            val dataline = animatorLineAndCircleList[i]
            val circleCount = dataline.circlePoints.size
            for (j in 0 until circleCount) {
                val circlePoint = dataline.circlePoints[j]
                if (circlePoint.region.contains(xNew, y)) {
                    index = intArrayOf(i, j)
                    return index
                }
            }
        }
        return index
    }


    inner class MyGestureListener : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val x = e.x.toInt()
            val y = e.y.toInt()
            val pressTitleIndex: Int = getPressTitleIndex(x, y)
            if (pressTitleIndex != -1) {
                onLabelClickListener?.onClick(xLabels[pressTitleIndex].title, pressTitleIndex)
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
            if (!allowScroll) {
                return false
            }
            isScrolling = true
            requestDisallowIntercept(true)
            if (distanceX < 0) {
                if (scrollX + distanceX <= 0) {
                    scrollTo(0, 0)
                } else scrollBy(distanceX.toInt(), 0)
            }
            val offset: Int = width + scrollX - factRight.toInt()
            if (distanceX > 0) {
                if (offset + distanceX >= 0) {
                    scrollTo(factRight.toInt() - width, 0)
                } else scrollBy(distanceX.toInt(), 0)
            }
            return false
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (!allowScroll) {
                return false
            }
            if (abs(velocityX) > abs(velocityY)) {
                if (abs(velocityX) < 1000) {
                    return true
                }
                var distanceX = (abs(velocityX) / 1000 * 150).toInt()
                if (velocityX < 0) { //左滑-->滑动至尾部
                    val leftWidth: Int = factRight.toInt() - abs(scrollX) - width
                    if (abs(distanceX) > leftWidth) {
                        distanceX = leftWidth
                    }
                } else { //右滑-->滑动至头部
                    if (abs(distanceX) > abs(getScrollX())) {
                        distanceX = abs(getScrollX())
                    }
                    distanceX = -distanceX
                }
                if (scrollX < 0) { //头部超出边界
                    distanceX = -scrollX
                }
                scroller.startScroll(scrollX, 0, distanceX, 0, SCROLL_DURATION)
                invalidate()
                return true
            }
            return false
        }
    }

    data class Point(var x: Float = 0f, var y: Float = 0f)

    private fun dp2Px(dip: Float): Float {
        val density = context.applicationContext.resources.displayMetrics.density
        return dip * density + 0.5f * if (dip >= 0) 1 else -1
    }

}