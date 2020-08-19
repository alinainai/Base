package com.gas.app.learn.calendarfinal.calendar

import com.gas.app.learn.calendarviewV2.TOTAL_COUNT
import org.joda.time.LocalDate
import java.util.*

/**
 * 日期数据仓库
 */
class CalendarDataRepo {
    /**
     * 获取距离时间戳指定月份的day数据
     * offsetNow 0： timeStamp 对应月份月 -1：上月  1：下月
     */
    fun getDataInMonthOffsetNow(timeStamp: Long, offsetNow: Int): List<LocalDate> {
        val result: MutableList<LocalDate> = ArrayList()
        //根据穿入的时间戳获取对应的月份第一天
        val theFirstDateOfMonth = LocalDate(timeStamp).plusMonths(offsetNow).dayOfMonth().withMinimumValue()

        //1.首先对当前月份第一行补充前一个月的数据
        //joda localData 对应的 dayOfWeek 和 Calendar 类不同，Calendar SunDay=1，joda 对应7。由于起始日期是周日，所以对7取余
        val preCount = theFirstDateOfMonth.dayOfWeek % 7
        if (preCount > 0) {
            for (i in preCount downTo 1) {
                result.add(theFirstDateOfMonth.minusDays(i))
            }
        }
        //2.填充当前月数据
        //获取当前月份天数
        val dayCount = theFirstDateOfMonth.dayOfMonth().withMaximumValue().dayOfMonth
        for (i in 0 until dayCount) {
            result.add(theFirstDateOfMonth.plusDays(i))
        }
        //3.补充尾部数据，每个月展示 6*7 个日期数字
        val extraCount = TOTAL_COUNT - result.size
        val lastDate = result.lastOrNull()
        lastDate?.let { last ->
            for (i in 1..extraCount) {
                result.add(last.plusDays(i))
            }
        }
        return result
    }

}