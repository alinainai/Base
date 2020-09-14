package com.gas.test.utils.view.animlinechart.bean

import android.graphics.Region

data class CirclePoint(
        var x: Float = 0f,
        var y: Float = 0f,
        var value:Float,
        var clickOffset: Float = 0F
) {

   var color: Int = 0

    val clickRegion = Region().apply {
        set((x - clickOffset).toInt(),
                (y - clickOffset).toInt(),
                (x + clickOffset).toInt(),
                (y + clickOffset).toInt())
    }

}