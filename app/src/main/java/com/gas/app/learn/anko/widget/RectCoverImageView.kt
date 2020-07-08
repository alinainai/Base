package ai.ling.luka.app.widget

//import android.content.Context
//import android.widget.ImageView

/**
 * 根据图片上识别出来的物体坐标绘制拉伸锚点。
 */
//class RectCoverImageView(ctx: Context) : ImageView(ctx) {

//    private val paint = Paint().apply {
//        style = Paint.Style.STROKE
//        strokeWidth = dip(3).toFloat()
//        color = Colors.color("#FFCF08")
//    }
//
//    private var initialTouchX = 0
//    private var initialTouchY = 0
//    private var lastTouchX = 0
//    private var lastTouchY = 0
//
//    private val invalidateAnchorIndex = -1
//    // 当前触摸的锚点索引
//    // 顺时针方向：
//    // 左上: 0   右上: 1
//    // 坐下: 3   右下: 2
//    private var touchedAnchorIndex = invalidateAnchorIndex
//    private var leftTopAnchorIndex = 0
//    private var rightTopAnchorIndex = 1
//    private var rightBottomAnchorIndex = 2
//    private var leftBottomAnchorIndex = 3
//
//    var anchorRadius = dip(8).toFloat()
//
//    private val matrixValues = FloatArray(9)
//    private var pointScaleFactor = 1.0f
//    private var pointTranslateX = 0
//    private var pointTranslateY = 0
//
//    // 手指拖动锚点的移动距离
//    private var finalLeftTopDx = 0
//    private var finalLeftTopDy = 0
//    private var finalRightTopDx = 0
//    private var finalRightTopDy = 0
//    private var finalRightBottomDx = 0
//    private var finalRightBottomDy = 0
//    private var finalLeftBottomDx = 0
//    private var finalLeftBottomDy = 0
//
//    private var leftTop = Point()
//    private var rightTop = Point()
//    private var rightBottom = Point()
//    private var leftBottom = Point()
//
//    private val imgWidth by lazy {
//        resources.displayMetrics.widthPixels
//    }
//
//    private val screenHeight by lazy {
//        resources.displayMetrics.heightPixels
//    }
//    var points: IntArray = intArrayOf()
//        set(value) {
//            if (value.size != 8) {
//                throw IllegalArgumentException("")
//            }
//            field = value
//            if (shouldTranslatePoints()) {
//                translatePointsToViewCoordinate()
//            } else {
//                mapPointsToCorner(field)
//            }
//            reset()
//            LogUtils.logI("input points: ${field.toList()}")
//            if (isValidatePoint()) {
//                postInvalidate()
//            }
//        }
//
//    private val translatedPoint = IntArray(8)
//    private val bitmapSize = IntArray(2)
//
//    // 重制移动数据
//    private fun reset() {
//        finalLeftTopDx = 0
//        finalLeftTopDy = 0
//        finalRightTopDx = 0
//        finalRightTopDy = 0
//        finalRightBottomDx = 0
//        finalRightBottomDy = 0
//        finalLeftBottomDx = 0
//        finalLeftBottomDy = 0
//    }
//
//    fun setBitmap(bitmapResource: Bitmap) {
//        this@RectCoverImageView.imageBitmap = bitmapResource
//        bitmapSize[0] = bitmapResource.width
//        bitmapSize[1] = bitmapResource.height
//    }
//
//    // 获取当前锚点位置对应的图片上的坐标
//    fun getImagePoints() = translatePointsToOriginImageCoordinate(
//        intArrayOf(
//            translatedPoint[0] + finalLeftTopDx,
//            translatedPoint[1] + finalLeftTopDy,
//            translatedPoint[2] + finalRightTopDx,
//            translatedPoint[3] + finalRightTopDy,
//            translatedPoint[4] + finalRightBottomDx,
//            translatedPoint[5] + finalRightBottomDy,
//            translatedPoint[6] + finalLeftBottomDx,
//            translatedPoint[7] + finalLeftBottomDy
//        )
//    )
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        drawable?.let {
//            imageMatrix.getValues(matrixValues)
//            // 获取图片的变换数据
//            val newScaleFactor = Math.min(width.toFloat().div(bitmapSize[0]), height.toFloat().div(bitmapSize[1]))
//            val newPointTranslateX = matrixValues[Matrix.MTRANS_X].toInt()
//            val newPointTranslateY = matrixValues[Matrix.MTRANS_Y].toInt()
//            if (newScaleFactor != pointScaleFactor || newPointTranslateX != pointTranslateX || newPointTranslateY != pointTranslateY) {
//                pointScaleFactor = newScaleFactor
//                pointTranslateX = newPointTranslateX
//                pointTranslateY = newPointTranslateY
//                LogUtils.logI("scaleFactor: $pointScaleFactor, pointTranslateX: $pointTranslateX pointTranslateY: $pointTranslateY")
//                translatePointsToViewCoordinate()
//            }
//        }
//        if (isValidatePoint()) {
//            drawableAnchor(canvas)
//            drawRec(canvas)
//        }
//    }
//
//    /**
//     * x坐标的开始边界（左边界）
//     */
//    fun getStartXPosition(): Int {
//        return 1
//    }
//
//    /**
//     * x坐标的结束边界（右边界）
//     */
//    fun getEndXPosition(): Int {
//        return imgWidth - 1
//    }
//
//    /**
//     * y坐标的开始边界（上边界）
//     */
//    fun getStartYPosition(): Int {
//        return dip(40)
//    }
//
//    /**
//     * y坐标的结束边界（下边界）
//     */
//    fun getEndYPosition(): Int {
//        return screenHeight - dip(20)
//    }
//
//    // 把锚点的坐标从控件坐标转到图片坐标上
//    private fun translatePointsToOriginImageCoordinate(viewPoints: IntArray): IntArray {
//        val scale = 1.0f.div(pointScaleFactor)
//        for (index in 0 until viewPoints.size) {
//            if (index % 2 == 0) {
//                viewPoints[index] = (viewPoints[index] - pointTranslateX).times(scale).toInt()
//            } else {
//                viewPoints[index] = (viewPoints[index] - pointTranslateY).times(scale).toInt()
//            }
//        }
//        LogUtils.logI("output points: ${viewPoints.toList()}")
//        return viewPoints
//    }
//
//    // 把锚点坐标从图片坐标转换到控件坐标上
//    private fun translatePointsToViewCoordinate() {
//        if (!shouldTranslatePoints()) {
//            return
//        }
//        for (index in 0 until points.size) {
//            if (index % 2 == 0) {
//                translatedPoint[index] = points[index].times(pointScaleFactor).toInt() + pointTranslateX
//            } else {
//                translatedPoint[index] = points[index].times(pointScaleFactor).toInt() + pointTranslateY
//            }
//        }
//        mapPointsToCorner(translatedPoint)
//    }
//
//    private fun mapPointsToCorner(points: IntArray) {
//        leftTop.x = points[0]
//        leftTop.y = points[1]
//        rightTop.x = points[2]
//        rightTop.y = points[3]
//        rightBottom.x = points[4]
//        rightBottom.y = points[5]
//        leftBottom.x = points[6]
//        leftBottom.y = points[7]
//    }
//
//    private fun shouldTranslatePoints() = bitmapSize[0] > 0 && bitmapSize[1] > 0
//
//    fun translateX() = drawable?.let {
//        width.div(2.0f) - it.bounds.centerX()
//    }
//
//    fun translateY() = drawable?.let {
//        height.div(2.0f) - it.bounds.centerY()
//    }
//
//    private fun drawRec(canvas: Canvas) {
//        canvas.drawLine(
//            leftTop.x.toFloat() + anchorRadius,
//            leftTop.y.toFloat(),
//            rightTop.x.toFloat() - anchorRadius,
//            rightTop.y.toFloat(),
//            paint
//        )
//        canvas.drawLine(
//            rightTop.x.toFloat(),
//            rightTop.y.toFloat() + anchorRadius,
//            rightBottom.x.toFloat(),
//            rightBottom.y.toFloat() - anchorRadius,
//            paint
//        )
//        canvas.drawLine(
//            rightBottom.x.toFloat() - anchorRadius,
//            rightBottom.y.toFloat(),
//            leftBottom.x.toFloat() + anchorRadius,
//            leftBottom.y.toFloat(),
//            paint
//        )
//        canvas.drawLine(
//            leftBottom.x.toFloat(),
//            leftBottom.y.toFloat() - anchorRadius,
//            leftTop.x.toFloat(),
//            leftTop.y.toFloat() + anchorRadius,
//            paint
//        )
//    }
//
//    private fun drawableAnchor(canvas: Canvas) {
//        canvas.drawCircle(leftTop.x.toFloat(), leftTop.y.toFloat(), anchorRadius, paint)
//        canvas.drawCircle(rightTop.x.toFloat(), rightTop.y.toFloat(), anchorRadius, paint)
//        canvas.drawCircle(rightBottom.x.toFloat(), rightBottom.y.toFloat(), anchorRadius, paint)
//        canvas.drawCircle(leftBottom.x.toFloat(), leftBottom.y.toFloat(), anchorRadius, paint)
//    }
//
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                initialTouchX = event.x.toInt()
//                initialTouchY = event.y.toInt()
//                lastTouchX = event.x.toInt()
//                lastTouchY = event.y.toInt()
//                if (isAnchorTouched(Point(event.x.toInt(), event.y.toInt()))) {
//                    LogUtils.logI("anchor touched.")
//                    return true
//                } else {
//                    LogUtils.logI("touched outside of anchor.")
//                    return false
//                }
//            }
//            MotionEvent.ACTION_MOVE -> {
//                if (touchedAnchorIndex != invalidateAnchorIndex) {
//                    val dx = event.x - lastTouchX
//                    val dy = event.y - lastTouchY
//                    when (touchedAnchorIndex) {
//                        leftTopAnchorIndex -> {
//                            if (isValidLeftTopAnchor(leftTop.x + dx.toInt(), leftTop.y + dy.toInt())) {
//                                leftTop.x += dx.toInt()
//                                leftTop.y += dy.toInt()
//                                finalLeftTopDx += dx.toInt()
//                                finalLeftTopDy += dy.toInt()
//                            }
//
//                            LogUtils.logI("finalLeftTopDx: $finalLeftTopDx, finalLeftTopDy: $finalLeftTopDy")
//                        }
//                        rightTopAnchorIndex -> {
//                            if (isValidRightTopAnchor(rightTop.x + dx.toInt(), rightTop.y + dy.toInt())) {
//                                rightTop.x += dx.toInt()
//                                rightTop.y += dy.toInt()
//                                finalRightTopDx += dx.toInt()
//                                finalRightTopDy += dy.toInt()
//                            }
//
//                            LogUtils.logI("finalRightTopDx: $finalRightTopDx, finalRightTopDy: $finalRightTopDy")
//                        }
//                        rightBottomAnchorIndex -> {
//                            if (isValidRightBottomAnchor(rightBottom.x + dx.toInt(), rightBottom.y + dy.toInt())) {
//                                rightBottom.x += dx.toInt()
//                                rightBottom.y += dy.toInt()
//                                finalRightBottomDx += dx.toInt()
//                                finalRightBottomDy += dy.toInt()
//                            }
//
//                            LogUtils.logI("finalRightBottomDx: $finalRightBottomDx, finalRightBottomDy: $finalRightBottomDy")
//                        }
//                        leftBottomAnchorIndex -> {
//                            if (isValidLeftBottomAnchor(leftBottom.x + dx.toInt(), leftBottom.y + dy.toInt())) {
//                                leftBottom.x += dx.toInt()
//                                leftBottom.y += dy.toInt()
//                                finalLeftBottomDx += dx.toInt()
//                                finalLeftBottomDy += dy.toInt()
//                            }
//
//                            LogUtils.logI("finalLeftBottomDx: $finalLeftBottomDx, finalLeftBottomDy: $finalLeftBottomDy")
//                            LogUtils.logI("leftBottom.x: ${leftBottom.x}, leftBottom.y: ${leftBottom.y}")
//                        }
//                    }
//                    invalidate()
//                    lastTouchX = event.x.toInt()
//                    lastTouchY = event.y.toInt()
//                }
//            }
//            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//                touchedAnchorIndex = invalidateAnchorIndex
//            }
//        }
//        return touchedAnchorIndex != invalidateAnchorIndex
//    }
//
//    /* 是否是有效的左上角锚点? 如果不是的话,请直接舍弃,因为锚点的有效性是有两个坐标同时决定的. */
//    private fun isValidLeftTopAnchor(x: Int, y: Int): Boolean {
//        println("paint ==== ${x >= getStartXPosition()}   ${ x < rightTop.x}  ${ y >= getStartYPosition()}   ${ y < leftBottom.y}")
//        println("paint ==== $x>=${getStartXPosition()}    $x<${ rightTop.x}  $y$ >= ${getStartYPosition()}   $y<${leftBottom.y}")
//        return (x >= getStartXPosition() && x < rightTop.x) &&
//            (y >= getStartYPosition() && y < leftBottom.y) &&
//            !isPointOnLineRight(rightTop, leftBottom, Point(x, y))
//    }
//
//    /* 是否是有效的右上角锚点? 如果不是的话,请直接舍弃,因为锚点的有效性是有两个坐标同时决定的. */
//    private fun isValidRightTopAnchor(x: Int, y: Int): Boolean {
//        return (x > leftTop.x && x <= getEndXPosition()) &&
//            (y >= getStartYPosition() && y < rightBottom.y) &&
//            !isPointOnLineLeft(leftTop, rightBottom, Point(x, y))
//    }
//
//    /* 是否是有效的左下角锚点? 如果不是的话,请直接舍弃,因为锚点的有效性是有两个坐标同时决定的. */
//    private fun isValidLeftBottomAnchor(x: Int, y: Int): Boolean {
//        return (x >= getStartXPosition() && x < rightBottom.x) &&
//            (y > leftTop.y && y <= getEndYPosition()) &&
//            !isPointOnLineRight(leftTop, rightBottom, Point(x, y))
//    }
//
//    /* 是否是有效的右下角锚点? 如果不是的话,请直接舍弃,因为锚点的有效性是有两个坐标同时决定的. */
//    private fun isValidRightBottomAnchor(x: Int, y: Int): Boolean {
//        return (x > leftBottom.x && x <= getEndXPosition()) &&
//            (y > rightTop.y && y <= getEndYPosition()) &&
//            !isPointOnLineRight(leftBottom, rightTop, Point(x, y))
//    }
//
//    /* 参考: https://stackoverflow.com/a/3461533 */
//    private fun isPointOnLineLeft(linePointA: Point, linePointB: Point, point: Point): Boolean {
//        val a = linePointA
//        val b = linePointB
//        val c = point
//
//        return ((b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)) > 0
//    }
//
//    /* 参考: https://stackoverflow.com/a/3461533 */
//    private fun isPointOnLineRight(linePointA: Point, linePointB: Point, point: Point): Boolean {
//        val a = linePointA
//        val b = linePointB
//        val c = point
//
//        return ((b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)) < 0
//    }
//
//    // 判断触摸的点是否在锚点范围内
//    private fun isAnchorTouched(touchPoint: Point): Boolean {
//        val anchorsCenters = listOf(leftTop, rightTop, rightBottom, leftBottom)
//        var isAnchorTouched = false
//        var index = 0
//        for (center in anchorsCenters) {
//            val dx = Math.abs(touchPoint.x - center.x)
//            val dy = Math.abs(touchPoint.y - center.y)
//            val distance = Math.sqrt((dx.times(dx) + dy.times(dy)).toDouble())
//            if (distance <= anchorRadius.times(4)) { // 适当放大下触摸点锚点坐标的计算，要不然锚点太小很难触摸到
//                isAnchorTouched = true
//                touchedAnchorIndex = index
//                break
//            }
//            index++
//        }
//        return isAnchorTouched
//    }
//
//    private fun isValidatePoint() = leftTop != rightTop && leftTop != rightBottom && leftTop != leftBottom
//}