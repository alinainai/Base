package com.gas.app.learn.calendarviewV2.mvp

import com.gas.app.learn.calendarviewV2.data.CalendarDayModel
import com.gas.app.learn.calendarviewV2.data.CalendarMonthModel
import org.joda.time.LocalDate

class CalendarSelectViewModel {

    private var initTimeStamp: Long
    private var pre3Month: CalendarMonthModel
    private var pre2Month: CalendarMonthModel
    private var pre1Month: CalendarMonthModel
    private var currentMonth: CalendarMonthModel
    val dayList = mutableListOf<CalendarDayModel>()

    init {
        initTimeStamp = System.currentTimeMillis()
        pre3Month = CalendarMonthModel(initTimeStamp, -3)
        pre2Month = CalendarMonthModel(initTimeStamp, -2)
        pre1Month = CalendarMonthModel(initTimeStamp, -1)
        currentMonth = CalendarMonthModel(initTimeStamp, 0)
        dayList.addAll(pre3Month.dayList)
        dayList.addAll(pre2Month.dayList)
        dayList.addAll(pre1Month.dayList)
        dayList.addAll(currentMonth.dayList)
    }

    fun updateTimeStamp(timeStamp: Long) {
        initTimeStamp = timeStamp
        pre3Month.updateStamp(initTimeStamp)
        pre2Month.updateStamp(initTimeStamp)
        pre1Month.updateStamp(initTimeStamp)
        currentMonth.updateStamp(initTimeStamp)
        dayList.clear()
        dayList.addAll(pre3Month.dayList)
        dayList.addAll(pre2Month.dayList)
        dayList.addAll(pre1Month.dayList)
        dayList.addAll(currentMonth.dayList)
    }

}