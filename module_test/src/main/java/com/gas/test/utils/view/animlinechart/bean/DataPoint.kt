package com.gas.test.utils.view.animlinechart.bean

import android.graphics.Region

data class DataPoint(
        var key:String,
        var value:Float
) {
    var x: Float = 0f
    var y: Float = 0f
    var color: Int = 0
    var region = Region()
}