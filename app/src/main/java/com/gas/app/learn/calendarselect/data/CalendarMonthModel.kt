package com.gas.app.learn.calendarselect.data

import org.joda.time.LocalDate

/**
 * month
 */
class CalendarMonthModel(var timeStamp: Long, private val offsetNow: Int) {

    private val calendar = CalendarDataRepo()
    val dayList = mutableListOf<CalendarDayModel>()
    var monthNum: Int
    val yyyyMM: String

    init {
        val localDate = LocalDate(timeStamp).plusMonths(offsetNow)
        monthNum = localDate.monthOfYear
        yyyyMM = "${localDate.year}年${localDate.monthOfYear}月"
        dayList.addAll(calendar.getDataInMonthOffsetNow(timeStamp, offsetNow).map { CalendarDayModel(it) })
        setEnable()
    }

    fun updateStamp(timeStamp: Long) {
        dayList.clear()
        dayList.addAll(calendar.getDataInMonthOffsetNow(timeStamp, offsetNow).map { CalendarDayModel(it) })
        monthNum = LocalDate(timeStamp).plusMonths(offsetNow).monthOfYear
        this.timeStamp = timeStamp
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