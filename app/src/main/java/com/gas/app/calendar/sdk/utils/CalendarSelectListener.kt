package com.gas.app.calendar.sdk.utils

import com.gas.app.calendar.sdk.data.CalendarDayModel

interface CalendarSelectListener {
    fun onCalendarSelect(calendar: CalendarDayModel, select: Boolean)
}