package com.gas.test.utils.view.animlinechart.bean

import android.graphics.Path

data class LineInChart(var circlePoints: List<DataPoint>, var lineColor: Int = 0) {
    var path: Path = Path()
}