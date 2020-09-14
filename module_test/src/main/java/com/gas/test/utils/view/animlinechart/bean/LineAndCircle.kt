package com.gas.test.utils.view.animlinechart.bean

import android.graphics.Path


data class LineAndCircle(var lineColor: Int = 0,
                         var path: Path = Path(),
                         var circlePoints: MutableList<CirclePoint> = mutableListOf())
