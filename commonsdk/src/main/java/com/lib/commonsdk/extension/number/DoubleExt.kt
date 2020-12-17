package com.lib.commonsdk.extension.number

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

//如果小数点后为零则显示整数否则保留两位小数
fun formatDouble(d: Double): String? {
    val bg = BigDecimal(d).setScale(2, RoundingMode.UP)
    val num: Double = bg.toDouble()
    if (num.roundToInt() - num == 0.0) {
        return num.toLong().toString()
    }
    return num.toString()
}

//String 保留2位小数
fun transformDoubleBit(str: String): String? {
    val data: Double = try {
        str.toDouble()
    } catch (e: NumberFormatException) {
        return str
    }
    val decimalFormat = DecimalFormat(",###.##")
    return decimalFormat.format(data)
}