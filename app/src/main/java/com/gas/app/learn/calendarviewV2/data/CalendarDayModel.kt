package com.gas.app.learn.calendarviewV2.data

import com.lib.commonsdk.kotlin.extension.isToday
import org.joda.time.LocalDate

/**
 * day
 */
class CalendarDayModel(private val timeStamp: LocalDate) {
    var info: String? = null
    var isSelected = false
    var isEnabled = false
    val dayOfMonth: Int = timeStamp.dayOfMonth
    val monthNum: Int = timeStamp.monthOfYear
    val isToday: Boolean
        get() = timeStamp.toDate().isToday
    val localDate: LocalDate
        get() = timeStamp
}