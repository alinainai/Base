package com.gas.test.utils.view.linechart

import android.content.Context
import android.graphics.Paint
import android.util.SparseArray
import android.util.TypedValue

/**
 * Class for holding values to draw grid by [LineChartView]
 */
internal class ChartGrid(context: Context) {
    @JvmField
    var stepHor = 10
    @JvmField
    var stepVer = 10
    @JvmField
    var horSubLinesCount = 0
    @JvmField
    var verSubLinesCount = 0
    @JvmField
    var horMainLinesEnabled = true
    @JvmField
    var horSubLinesEnabled = true
    @JvmField
    var verMainLinesEnabled = true
    @JvmField
    var verSubLinesEnabled = true
    @JvmField
    var verMainValuesEnabled = true
    @JvmField
    var horMainValuesEnabled = true
    @JvmField
    var mainHorLinesPaint = Paint()
    @JvmField
    var mainVerLinesPaint = Paint()
    @JvmField
    var subHorLinesPaint = Paint()
    @JvmField
    var subVerLinesPaint = Paint()
    @JvmField
    var mainVerValuesPaint = Paint()
    @JvmField
    var mainHorValuesPaint = Paint()
    @JvmField
    var horValuesMarginBottom = 0
    @JvmField
    var horValuesMarginTop = 0
    @JvmField
    var horValuesMarginLeft = 0
    @JvmField
    var horValuesMarginRight = 0
    @JvmField
    var verValuesMarginBottom = 20
    @JvmField
    var verValuesMarginTop = 0
    @JvmField
    var verValuesMarginLeft = 10
    @JvmField
    var verValuesMarginRight = 10
    @JvmField
    var horValuesAlign = TextAlign.HORIZONTAL_CENTER or TextAlign.BOTTOM
    @JvmField
    var verValuesAlign = TextAlign.LEFT or TextAlign.BOTTOM
    @JvmField
    var horValuesText: SparseArray<String>? = null
    @JvmField
    var verValuesText: SparseArray<String>? = null

    init {
        mainVerLinesPaint.color = -0x55777778
        subVerLinesPaint.color = 0x44888888
        mainHorLinesPaint.color = -0x55777778
        subHorLinesPaint.color = 0x44888888
        mainVerValuesPaint.color = -0xbbbbbc
        mainVerValuesPaint.isAntiAlias = true
        mainVerValuesPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10f, context.resources.displayMetrics)
        mainHorValuesPaint.color = -0xbbbbbc
        mainHorValuesPaint.isAntiAlias = true
        mainHorValuesPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, context.resources.displayMetrics)
    }
}