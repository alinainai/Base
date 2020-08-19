package com.gas.app.calendar.sdk.data

import org.joda.time.LocalDate

/**
 * month
 */
class CalendarMonthModel(var timeStamp: Long, private val offsetNow: Int=0) {

    val dayList = mutableListOf<CalendarDayModel>()
    var monthNum: Int
    var yyyyMM: String

    init {
        val localDate = LocalDate(timeStamp).plusMonths(offsetNow)
        monthNum = localDate.monthOfYear
        yyyyMM = "${localDate.year}年${localDate.monthOfYear}月"
        dayList.addAll(daysOfMonthOffset(offsetNow, timeStamp).map { CalendarDayModel(it) })
        setEnable()
    }

    //更新日期
    fun updateStamp(timeStamp: Long) {
        this.timeStamp = timeStamp
        val localDate = LocalDate(timeStamp).plusMonths(offsetNow)
        monthNum = localDate.monthOfYear
        yyyyMM = "${localDate.year}年${localDate.monthOfYear}月"
        dayList.clear()
        dayList.addAll(daysOfMonthOffset(offsetNow, timeStamp).map { CalendarDayModel(it) })
        setEnable()
    }

    private fun setEnable() {
        val monthNum = LocalDate(timeStamp).plusMonths(offsetNow).monthOfYear
        val toDay = LocalDate(timeStamp)
        dayList.forEach { dayModel ->
            if (dayModel.monthNum != monthNum) {
                dayModel.isEnabled = false
            } else {
                dayModel.isEnabled = !dayModel.localDate.isAfter(toDay)
            }
        }
    }

}