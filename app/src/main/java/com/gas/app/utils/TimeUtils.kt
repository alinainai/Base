package com.gas.app.utils

import java.text.DecimalFormat

object TimeUtils {

    private const val DEFAULT_TIME = "00"

    //为了减少计算，传入的计时时间戳要先 /100 操作。
    private const val DEFAULT_SECOND_MILLISECOND_MILL = 1L
    private const val DEFAULT_SECOND_MILLISECOND = 10L //10*DEFAULT_SECOND_MILLISECOND_MILL
    private const val DEFAULT_MINUTE_MILLISECOND = 600L //60*10*DEFAULT_SECOND_MILLISECOND_MILL
    private const val DEFAULT_HOUR_MILLISECOND = 36000L //60*60*10*DEFAULT_SECOND_MILLISECOND_MILL
    private const val DEFAULT_DAY_MILLISECOND = 864000L

    //格式化 如：5 -> 05 （时分秒需要）
    private val mDf = DecimalFormat(DEFAULT_TIME)


    fun timeStampToDuration(time: Long): String? {
        val sb = StringBuilder()
        var optimizeTime = time
        if (optimizeTime >= 100) {
            optimizeTime /= 100
        }
        //天数
        val dayCount = optimizeTime / DEFAULT_DAY_MILLISECOND
        sb.append(dayCount).append("天").append(" ")
        optimizeTime -= dayCount * DEFAULT_DAY_MILLISECOND
        //小时数
        val hourCount = optimizeTime / DEFAULT_HOUR_MILLISECOND
        sb.append(mDf.format(hourCount)).append(":")
        optimizeTime -= hourCount * DEFAULT_HOUR_MILLISECOND
        //分钟数int
        val minuteCount = optimizeTime / DEFAULT_MINUTE_MILLISECOND
        sb.append(mDf.format(minuteCount)).append(":")
        optimizeTime -= minuteCount * DEFAULT_MINUTE_MILLISECOND
        //秒数
        val secondCount = optimizeTime / DEFAULT_SECOND_MILLISECOND
        sb.append(mDf.format(secondCount)).append(":")
        optimizeTime -= secondCount * DEFAULT_SECOND_MILLISECOND
        //秒数后面
        val secondMillCount = optimizeTime / DEFAULT_SECOND_MILLISECOND_MILL
        sb.append(secondMillCount)
        return sb.toString()
    }


}