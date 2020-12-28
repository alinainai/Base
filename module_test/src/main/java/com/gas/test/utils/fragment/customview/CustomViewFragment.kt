package com.gas.test.utils.fragment.customview

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.test.R
import com.gas.test.utils.view.AnimPieChartView
import com.gas.test.utils.view.animlinechart.bean.DataPoint
import com.gas.test.utils.view.animlinechart.bean.LineInChart
import com.gas.test.utils.view.animlinechart.bean.XLabel
import com.gas.test.utils.view.animlinechart.callback.OnLabelClickListener
import kotlinx.android.synthetic.main.fragment_custom_view.*

class CustomViewFragment : BaseFragment<IPresenter>() {


    override fun setupFragmentComponent(appComponent: AppComponent) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_custom_view, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        val arcs = listOf(AnimPieChartView.ArcInfo(Color.parseColor("#FFFF0000"), Color.parseColor("#FFFF00FF"), 0.25F),
                AnimPieChartView.ArcInfo(Color.parseColor("#FF00FF00"), Color.parseColor("#FF00FF00"), 0.25F),
                AnimPieChartView.ArcInfo(Color.parseColor("#FF0000FF"), Color.parseColor("#FF0000FF"), 0.25F),
                AnimPieChartView.ArcInfo(Color.parseColor("#FF00FFFF"), Color.parseColor("#FF00FFFF"), 0.25F)
        )
        pieView.setData(arcs)
//        lineDefaultChar()
//        gridLineChart()
//        markedPointsChart()
//        multilineChart()
//        smoothLinesChart()
//        guardEventChart()
//        lineChart2()
//        lineChart3()
        lineChart4()
    }

//    private fun lineDefaultChar() {
//        val line = Line(activity)
//        var i = 0
//        while (i < 300) {
//            line.addPoint(LinePoint(activity as Context, i.toFloat(), (Math.random() * 50 + 20).toFloat()))
//            i += 10
//        }
//        lineChart.addLine(line)
//    }
//
//    private fun gridLineChart() {
//        lineChart.let { chart ->
//            chart.addLine(generateLine(0, 12 * 50, 10, 10, 90))
//
//            chart.setViewPort(0F, 0F, 100F, 100F)
//            chart.setGridSize(10, 3, 20, 3)
//            chart.setBackgroundColor(-0x1000000)
//
//            val main = Paint()
//            main.color = -0x77022f8c
//            main.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f,
//                    resources.displayMetrics)
//            val sub = Paint()
//            sub.color = 0x44ffbb33
//            sub.strokeWidth = 0f
//            chart.setHorizontalGridStyle(main, sub)
//            chart.setVerticalGridStyle(main, sub)
//            chart.setVerValuesMargins(8, 30, 8, 8)
//            chart.setHorValuesMargins(0, 8, 8, 0)
//            chart.setViewPortMargins(30F, 30F, 8F, 8F)
//
//            val horValPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//            horValPaint.color = -0x1
//            horValPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics)
//            val verValPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//            verValPaint.color = -0x1
//            verValPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10f, resources.displayMetrics)
//            chart.setMainValuesStyle(horValPaint, verValPaint)
//
//            val horValues = SparseArray<String>()
//            val c: Calendar = GregorianCalendar()
//            for (i in 0..11) {
//                c[Calendar.MONTH] = i
//                horValues.put(i * 50, c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US))
//            }
//
//            chart.setHorValuesText(horValues)
//
//            val verValues = SparseArray<String>()
//            var i = 10
//            while (i <= 150) {
//                verValues.put(i, i.toString() + "K")
//                i += 10
//            }
//            chart.setVerValuesText(verValues)
//        }
//
//    }
//
//    private fun generateLine(startX: Int, endX: Int, step: Int, minY: Int, maxY: Int): Line {
//        val line = Line(activity)
//        var i = startX
//        while (i <= endX) {
//            line.addPoint(LinePoint(activity as Context, i.toFloat(), (Math.random() * (maxY - minY) + minY).toFloat()))
//            i += step
//        }
//        return line
//    }
//
//    private fun markedPointsChart() {
//        lineChart.let { chart ->
//            chart.setViewPort(0F, 0F, 100F, 160F)
//            chart.setGridSize(10, 0, 20, 0)
//            // default line
//            // default line
//            val line = generateLine(-100, 360, 10, 50, 90)
//            for (point in line!!.points) {
//                point.isVisible = true
//                point.type = LinePoint.Type.SQUARE
//            }
//            chart.addLine(line)
//            // fat orange line with under fill
//            // fat orange line with under fill
//            val line2 = generateLine(-100, 360, 20, 20, 50)!!.setColor(-0x7800)
//                    .setFilled(true)
//                    .setFilledColor(0x44ff8800)
//                    .setStrokeWidth(4F)
//            for (point in line2.points) {
//                point.isVisible = true
//                point.type = LinePoint.Type.CIRCLE
//                point.strokePaint.color = -0x7800
//            }
//            chart.addLine(line2)
//            //
//            //
//            val line3 = generateLine(-100, 360, 5, 100, 150)!!.setColor(-0xff669a)
//            for (point in line3.points) {
//                point.isVisible = true
//                point.type = LinePoint.Type.TRIANGLE
//                point.strokePaint.color = -0xff669a
//            }
//            chart.addLine(line3)
//
//            chart.setOnPointClickListener(object : LineChartView.OnChartPointClickListener {
//                override fun onPointClick(point: LinePoint, line: Line) {
////                    for (p in line.points) {
////                        p.radius = 5F
////                        p.isTextVisible = false
////                    }
////                    point.radius = 10F
////                    point.isTextVisible = true
////                    point.text = java.lang.String.valueOf(point.y)
////                    point.textPaint.color = line.paint.color
//                }
//
//            })
//
//        }
//
//    }
//
//    private fun multilineChart() {
//        lineChart.let { chart ->
//
//            chart.setViewPort(0F, 0F, 100F, 150F)
//            chart.setGridSize(10, 0, 20, 0)
//            // default line
//            // default line
//            chart.addLine(generateLine(-100, 360, 10, 50, 90))
//            // fat orange line with under fill
//            // fat orange line with under fill
//            chart.addLine(generateLine(-100, 360, 20, 20, 50)!!.setColor(-0x7800)
//                    .setFilled(true)
//                    .setFilledColor(0x44ff8800)
//                    .setStrokeWidth(4F))
//            // dashed line
//            // dashed line
//            chart.addLine(generateLine(-100, 360, 5, 90, 140)!!.setColor(-0x996700)
//                    .setStrokeWidth(0F)
//                    .setPathEffect(DashPathEffect(floatArrayOf(3f, 3f), 0F)))
//        }
//    }
//
//    private fun smoothLinesChart() {
//        lineChart.let { chart ->
//            chart.setViewPort(0F, 0F, 100F, 160F)
//            chart.setGridSize(10, 0, 20, 0)
//            chart.addLine(generateLine(0, 360, 5, 10, 50)!!.setFilled(true).smoothLine(4))
//            chart.addLine(generateLine(0, 360, 5, 60, 90)!!.setColor(-0x7800).smoothLine(1))
//            chart.addLine(generateLine(0, 360, 5, 100, 150)!!.setColor(-0x996700).smoothLine(8))
//        }
//    }
//
//    private fun guardEventChart() {
//        lineChart.let { chart ->
//            chart.setViewPort(0F, 0F, 100F, 160F)
//            chart.setGridSize(10, 0, 20, 0)
//            chart.setHorValuesMargins(16, 16, 16, 16)
//            val line1 = generateLine(0, 100, 5, 10, 150)!!.setColor(-0x996700).setFilled(false)
//            val line2 = generateLine(0, 100, 5, 30, 150)!!.setColor(-0x7800).setFilled(false)
//            for (point in line1.points) {
//                point.isVisible = true
//                point.type = LinePoint.Type.CIRCLE
//                point.strokePaint.color = -0x996700
//            }
//            for (point in line2.points) {
//                point.isVisible = true
//                point.type = LinePoint.Type.CIRCLE
//                point.strokePaint.color = -0x7800
//            }
//            chart.addLine(line1)
//            chart.addLine(line2)
//            chart.setOnPointClickListener(object : LineChartView.OnChartPointClickListener {
//                override fun onPointClick(point: LinePoint, line: Line) {
//                    debug("point=${point}")
//                    for (p in line1.points) {
//                        p.radius = 5F
//                        p.isTextVisible = false
//                    }
//                    for (p in line2.points) {
//                        p.radius = 5F
//                        p.isTextVisible = false
//                    }
//                    point.radius = 10F
//                    point.isTextVisible = true
//                    point.text = java.lang.String.valueOf(point.y)
//                    point.textPaint.color = line.paint.color
//                }
//
//            })
//
//
//        }
//    }
//
//    private fun lineChart2() {
//        val map = mutableMapOf(Pair("1", 30), Pair("2", 15), Pair("3", 40), Pair("4", 40), Pair("5", 45), Pair("6", 35), Pair("7", 70))
//        val xValue = mutableListOf("1", "2", "3", "4", "5", "6", "7")
//        val yValue = mutableListOf(0, 10, 20, 30, 40, 50, 60, 70, 80)
//        lineChart2.setValue(map, xValue, yValue)
//    }
//
//    private fun lineChart3() {
//        lineChart3.let { line ->
//            line.clearDatas()
//                    .setHorizontalOpen(false) //---是否左右开放,无坐标轴
//                    .setShowHorGraduation(true) //---在setHorizontalOpen(false)的前提下,设置是否按照setDensity(int)显示刻度线
//                    .setShowTagRectBack(true) //---设置是否显示数字标签的背景,默认true
//                    .setShowAllTag(false) //----设置是否显示全部的数字标签,默认为false
//                    .setCoordinateRectLineWidth(2F) //---设置刻度矩形的线宽
//                    .setShowTitleRect(false) //--是否显示底部标题的矩形,默认为false
//                    .setLineSmoothness(0.4F)
//                    .setAllowScroll(false)
//                    .setOnShowTagCallBack(object : OnShowTagCallBack {
//                        override fun onShow(num: Float): String {
//                            return if (num < 30) {
//                                "不及格"
//                            } else num.toString() + ""
//                        }
//                    })
//                    .setShowAnimation(false) //设置绘制时是否显示动画
//                    .setDensity(5) //设置刻度密度
//                    .setAllowClickShowTag(true) //设置是否允许点击节点显示当前线的tag
//                    .setCoordinateTextSize(30) //设置刻度文字的大小
//                    .setTagTextSize(40) //设置数字标签的字体大小(px)
//                    .setTitles(arrayOf("语文111", "数学", "英语", "物理", "化学", "ss", "ss")) //底部标题,需与折线数据长度一致
//                    .addData(LineData(floatArrayOf(20.5f, 50f, 0f, 70.9f, 90f, 70f, -100f), -0xd34f8e, -0xe792bb)) //需与title长度一致
//                    .addData(LineData(floatArrayOf(30f, 80f, 50f, 80.5f, 70.8f, 60f, 100f), -0x753a8))
//                    .setOnTitleClickListener(object : LineChart.OnTitleClickListener {
//                        override fun onClick(linechart: LineChart?, title: String?, index: Int) {
//                            Toast.makeText(activity!!, title
//                                    ?: "title==null", Toast.LENGTH_SHORT).show()
//                        }
//                    })
//                    .commit()
//        }
//
//
//    }

    private fun lineChart4() {
        lineChart4.apply {
            setLineSmoothness(0.4F)
            allowScroll = false
            min = 0F
            max = getMaxValueToValid(2100).toFloat()
            density = 7
            showAnimation = true
            drawXLabelLine = false
            drawValueLabelRule={value->value%2!=0}
            val title = listOf(
                    XLabel("1", "1"),
                    XLabel("0.5", ""),
                    XLabel("1", "1"),
                    XLabel("1.5", ""),
                    XLabel("2", "2"),
                    XLabel("2.5", ""),
                    XLabel("3", "3"),
                    XLabel("3.5", ""),
                    XLabel("4", "4"),
                    XLabel("4.5", ""),
                    XLabel("5", "5"),
                    XLabel("5.5", ""),
                    XLabel("6", "6"),
                    XLabel("6.5", ""),
                    XLabel("7", "7"),
                    XLabel("7.5", "")
            )
            setTitles(title) //底部标题,需与折线数据长度一致
            onLabelClickListener = object : OnLabelClickListener {
                override fun onClick(title: String, index: Int) {
                    Toast.makeText(activity!!, title, Toast.LENGTH_SHORT).show()
                }
            }

            val linePoint1 = listOf(
                    DataPoint("1", 50f),
                    DataPoint("2", 0f),
                    DataPoint("3", 70.9f),
                    DataPoint("4", 90f),
                    DataPoint("5", 70f),
                    DataPoint("6", 5F),
                    DataPoint("7", 80f)
            )

            val linePoint2 = listOf(
                    DataPoint("1", 1180f),
                    DataPoint("2", 50f),
                    DataPoint("3", 80.5f),
                    DataPoint("4", 70.8f),
                    DataPoint("5", 60f),
                    DataPoint("6", 85f),
                    DataPoint("7", 95f)
            )

            valueToTag = { value -> "${value.toInt()}PCS" }
            setLineSmoothness(0.38F)
            addData(LineInChart(linePoint1, Color.parseColor("#6DD400"))) //需与title长度一致
            addData(LineInChart(linePoint2, Color.parseColor("#2177FF")))
            commit()
        }
    }

    override fun setData(data: Any?) {
    }

    private fun getMaxValueToValid(max: Int): Int {
        val remain = max % 35
        if (remain == 0)
            return max
        val scale = max / 35 + 1
        return 35 * scale
    }

}