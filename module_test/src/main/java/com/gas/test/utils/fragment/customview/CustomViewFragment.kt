package com.gas.test.utils.fragment.customview

import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.os.Bundle
import android.util.SparseArray
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.test.R
import com.gas.test.utils.view.AnimationPercentPieView
import com.gas.test.utils.view.linechart.Line
import com.gas.test.utils.view.linechart.LinePoint
import com.lib.commonsdk.kotlin.extension.app.debug
import kotlinx.android.synthetic.main.fragment_custom_view.*
import java.util.*

class CustomViewFragment : BaseFragment<IPresenter>() {



    override fun setupFragmentComponent(appComponent: AppComponent) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_custom_view, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        val arcs = listOf(AnimationPercentPieView.ArcInfo(Color.parseColor("#FFFF0000"),Color.parseColor("#FFFF00FF"),0.25F),
                AnimationPercentPieView.ArcInfo(Color.parseColor("#FF00FF00"),Color.parseColor("#FF00FF00"),0.25F),
                AnimationPercentPieView.ArcInfo(Color.parseColor("#FF0000FF"),Color.parseColor("#FF0000FF"),0.25F),
                AnimationPercentPieView.ArcInfo(Color.parseColor("#FF00FFFF"),Color.parseColor("#FF00FFFF"),0.25F)
        )
        pieView.setData(arcs)
//        lineDefaultChar()
//        gridLineChart()
//        markedPointsChart()
//        multilineChart()
//        smoothLinesChart()
        guardEventChart()
    }

    private fun lineDefaultChar(){
        val line = Line(activity)
        var i = 0
        while (i < 300) {
            line.addPoint(LinePoint(activity as Context, i.toFloat(), (Math.random() * 50 + 20).toFloat()))
            i += 10
        }
        lineChart.addLine(line)
    }

    private fun gridLineChart(){
        lineChart.let{chart->
            chart.addLine(generateLine(0, 12 * 50, 10, 10, 90))

            chart.setViewPort(0F, 0F, 100F, 100F)
            chart.setGridSize(10, 3, 20, 3)
            chart.setBackgroundColor(-0x1000000)

            val main = Paint()
            main.color = -0x77022f8c
            main.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f,
                    resources.displayMetrics)
            val sub = Paint()
            sub.color = 0x44ffbb33
            sub.strokeWidth = 0f
            chart.setHorizontalGridStyle(main, sub)
            chart.setVerticalGridStyle(main, sub)
            chart.setVerValuesMarginsDP(8, 30, 8, 8)
            chart.setHorValuesMarginsDP(0, 8, 8, 0)
            chart.setViewPortMarginsDP(30F, 30F, 8F, 8F)

            val horValPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            horValPaint.color = -0x1
            horValPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics)
            val verValPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            verValPaint.color = -0x1
            verValPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10f, resources.displayMetrics)
            chart.setMainValuesStyle(horValPaint, verValPaint)

            val horValues = SparseArray<String>()
            val c: Calendar = GregorianCalendar()
            for (i in 0..11) {
                c[Calendar.MONTH] = i
                horValues.put(i * 50, c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US))
            }

            chart.setHorValuesText(horValues)

            val verValues = SparseArray<String>()
            var i = 10
            while (i <= 150) {
                verValues.put(i, i.toString() + "K")
                i += 10
            }
            chart.setVerValuesText(verValues)
        }

    }

    private fun generateLine(startX: Int, endX: Int, step: Int, minY: Int, maxY: Int): Line? {
        val line = Line(activity)
        var i = startX
        while (i <= endX) {
            line.addPoint(LinePoint(activity as Context, i.toFloat(), (Math.random() * (maxY - minY) + minY).toFloat()))
            i += step
        }
        return line
    }

    private fun markedPointsChart(){
        lineChart.let{chart->
            chart.setViewPort(0F, 0F, 100F, 160F)
            chart.setGridSize(10, 0, 20, 0)
            // default line
            // default line
            val line = generateLine(-100, 360, 10, 50, 90)
            for (point in line!!.points) {
                point.isVisible = true
                point.type = LinePoint.Type.SQUARE
            }
            chart.addLine(line)
            // fat orange line with under fill
            // fat orange line with under fill
            val line2 = generateLine(-100, 360, 20, 20, 50)!!.setColor(-0x7800)
                    .setFilled(true)
                    .setFilledColor(0x44ff8800)
                    .setStrokeWidth(4F)
            for (point in line2.points) {
                point.isVisible = true
                point.type = LinePoint.Type.CIRCLE
                point.strokePaint.color = -0x7800
            }
            chart.addLine(line2)
            //
            //
            val line3 = generateLine(-100, 360, 5, 100, 150)!!.setColor(-0xff669a)
            for (point in line3.points) {
                point.isVisible = true
                point.type = LinePoint.Type.TRIANGLE
                point.strokePaint.color = -0xff669a
            }
            chart.addLine(line3)

            chart.setOnPointClickListener { point, line ->
                for (p in line.points) {
                    p.radius = 5F
                    p.isTextVisible = false
                }
                point.radius = 10F
                point.isTextVisible = true
                point.text = java.lang.String.valueOf(point.y)
                point.textPaint.color = line.paint.color
            }
        }

    }

    private fun multilineChart(){
        lineChart.let { chart->

            chart.setViewPort(0F, 0F, 100F, 150F)
            chart.setGridSize(10, 0, 20, 0)
            // default line
            // default line
            chart.addLine(generateLine(-100, 360, 10, 50, 90))
            // fat orange line with under fill
            // fat orange line with under fill
            chart.addLine(generateLine(-100, 360, 20, 20, 50)!!.setColor(-0x7800)
                    .setFilled(true)
                    .setFilledColor(0x44ff8800)
                    .setStrokeWidth(4F))
            // dashed line
            // dashed line
            chart.addLine(generateLine(-100, 360, 5, 90, 140)!!.setColor(-0x996700)
                    .setStrokeWidth(0F)
                    .setPathEffect(DashPathEffect(floatArrayOf(3f, 3f), 0F)))
        }
    }

    private fun smoothLinesChart(){
        lineChart.let { chart->
            chart.setViewPort(0F, 0F, 100F, 160F)
            chart.setGridSize(10, 0, 20, 0)
            chart.addLine(generateLine(0, 360, 5, 10, 50)!!.setFilled(true).smoothLine(4))
            chart.addLine(generateLine(0, 360, 5, 60, 90)!!.setColor(-0x7800).smoothLine(1))
            chart.addLine(generateLine(0, 360, 5, 100, 150)!!.setColor(-0x996700).smoothLine(8))
        }
    }

    private fun guardEventChart(){
        lineChart.let { chart->
            chart.setViewPort(0F, 0F, 100F, 160F)
            chart.setGridSize(10, 0, 20, 0)
            chart.setHorValuesMargins(16,16,16,16)
            val line =generateLine(0, 100, 5, 10, 150)!!.setColor(-0x996700).setFilled(false).smoothLine(8)
            val line2 =generateLine(0, 100, 5, 30, 150)!!.setColor(-0x7800).setFilled(false).smoothLine(8)
            for (point in line.points) {
                point.isVisible = true
                point.type = LinePoint.Type.CIRCLE
                point.strokePaint.color = -0x996700
            }
            for (point in line2.points) {
                point.isVisible = true
                point.type = LinePoint.Type.CIRCLE
                point.strokePaint.color = -0x7800
            }
            chart.addLine(line)
            chart.addLine(line2)
            chart.setOnPointClickListener { point, _ ->
                debug("point=${point}")
                for (p in line.points) {
                    p.radius = 5F
                    p.isTextVisible = false
                }
                for (p in line2.points) {
                    p.radius = 5F
                    p.isTextVisible = false
                }
                point.radius = 10F
                point.isTextVisible = true
                point.text = java.lang.String.valueOf(point.y)
                point.textPaint.color = line.paint.color
            }

        }
    }

    override fun setData(data: Any?) {
    }

}