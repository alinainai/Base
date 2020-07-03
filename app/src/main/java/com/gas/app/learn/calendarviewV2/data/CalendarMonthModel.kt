package com.gas.app.learn.calendarviewV2.data

import org.joda.time.LocalDate

/**
 * month
 */
class CalendarMonthModel(var timeStamp: Long, private val offsetNow: Int) {

    val calendar = CalendarDataRepo()
    val dayList = mutableListOf<CalendarDayModel>()

    init {
        dayList.addAll(calendar.getDataInMonthOffsetNow(timeStamp, offsetNow).map { CalendarDayModel(it) })
        setEnable()
    }

    fun updateStamp(timeStamp: Long) {
        dayList.clear()
        dayList.addAll(calendar.getDataInMonthOffsetNow(timeStamp, offsetNow).map { CalendarDayModel(it) })
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

    fun setSelect(selectDay:LocalDate){
        dayList.forEach { dayModel ->
            dayModel.isSelected = dayModel.localDate == selectDay
        }
    }

}