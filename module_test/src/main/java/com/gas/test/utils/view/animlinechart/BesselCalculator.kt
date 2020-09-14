package com.gas.test.utils.view.animlinechart

import com.gas.test.utils.view.animlinechart.AnimLineChartView.Point
import java.util.*

/**
 * Created by Vinctor on 2017/4/10.
 */
class BesselCalculator {
    private var smoothness = 0.4f

    /**
     * 计算贝塞尔结点
     */
    fun computeBesselPoints(points: List<Point>): List<Point> {
        val besselPoints: MutableList<Point> = ArrayList()
        val count = points.size
        besselPoints.clear()
        for (i in 0 until count) {
            if (i == 0 || i == count - 1) {
                computeUnMonotonePoints(i, points, besselPoints)
            } else {
                val p0 = points[i - 1]
                val p1 = points[i]
                val p2 = points[i + 1]
                if ((p1.y - p0.y) * (p1.y - p2.y) >= 0) { // 极值点
                    computeUnMonotonePoints(i, points, besselPoints)
                } else {
                    computeMonotonePoints(i, points, besselPoints)
                }
            }
        }
        return besselPoints
    }

    /**
     * 计算非单调情况的贝塞尔结点
     */
    private fun computeUnMonotonePoints(i: Int, points: List<Point>, besselPoints: MutableList<Point>) {
        when (i) {
            0 -> {
                val p1 = points[0]
                val p2 = points[1]
                besselPoints.add(p1)
                besselPoints.add(Point(p1.x + (p2.x - p1.x) * smoothness, p1.y))
            }
            points.size - 1 -> {
                val p0 = points[i - 1]
                val p1 = points[i]
                besselPoints.add(Point(p1.x - (p1.x - p0.x) * smoothness, p1.y))
                besselPoints.add(p1)
            }
            else -> {
                val p0 = points[i - 1]
                val p1 = points[i]
                val p2 = points[i + 1]
                besselPoints.add(Point(p1.x - (p1.x - p0.x) * smoothness, p1.y))
                besselPoints.add(p1)
                besselPoints.add(Point(p1.x + (p2.x - p1.x) * smoothness, p1.y))
            }
        }
    }

    /**
     * 计算单调情况的贝塞尔结点
     *
     * @param i
     * @param points
     * @param besselPoints
     */
    private fun computeMonotonePoints(i: Int, points: List<Point>, besselPoints: MutableList<Point>) {
        val p0 = points[i - 1]
        val p1 = points[i]
        val p2 = points[i + 1]
        val k = (p2.y - p0.y) / (p2.x - p0.x)
        val b = p1.y - k * p1.x
        val p01 = Point()
        p01.x = p1.x - (p1.x - (p0.y - b) / k) * smoothness
        p01.y = k * p01.x + b
        besselPoints.add(p01)
        besselPoints.add(p1)
        val p11 = Point()
        p11.x = p1.x + (p2.x - p1.x) * smoothness
        p11.y = k * p11.x + b
        besselPoints.add(p11)
    }

    fun setSmoothness(smoothness: Float) {
        this.smoothness = smoothness
    }


}