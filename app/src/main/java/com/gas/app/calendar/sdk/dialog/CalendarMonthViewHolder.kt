package com.gas.app.calendar.sdk.dialog

import com.gas.app.calendar.sdk.data.CalendarMonthModel
import com.gas.app.calendar.sdk.data.CalendarTheme
import com.gas.app.calendar.sdk.utils.CalendarSelectListener
import com.gas.app.calendar.sdk.view.SingleMonthLayout
import org.joda.time.LocalDate

class CalendarMonthViewHolder(private val view: SingleMonthLayout,
                              private val model: CalendarMonthModel,
                              onDayItemClick: CalendarSelectListener,
                              theme: CalendarTheme) {

    init {
        view.setMonthModel(model)
        view.setCalendarSelectListener(onDayItemClick)
    }

    fun getItemView(): SingleMonthLayout {
        return view
    }

    fun getMonthNum(): Int {
        return model.monthNum
    }

    fun getMonthTitle(): String {
        return model.yyyyMM
    }

    fun showSelect(select: LocalDate? = null) {
        if (select != null && select.monthOfYear == getMonthNum()) {
            view.setSelectDay(select)
        } else {
            view.setSelectDay(null)
        }
    }

    fun updateDate(timeStamp: Long) {
        view.updateTimeStamp(timeStamp)
    }


}