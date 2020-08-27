package com.lib.commonsdk.kotlin.extension.time

import java.text.SimpleDateFormat
import java.util.*

/**
 * Date的扩展函数
 */
fun Date.formatDate(format:String="yyyy-MM-dd"): String? = SimpleDateFormat(format, Locale.getDefault()).format(this)

fun Date.formatDatetime(): String? = formatDate("yyyy-MM-dd HH:mm:ss")

val Date.isToday: Boolean
    get() = formatDate() == Date().formatDate()

fun Date.isSameDay(date: Date): Boolean {
    val date1 = Calendar.getInstance()
    date1.time = this
    val date2 = Calendar.getInstance().apply {
        time = date
    }
    return date1[Calendar.YEAR] == date2[Calendar.YEAR] &&
            date1[Calendar.DAY_OF_YEAR] == date2[Calendar.DAY_OF_YEAR]
}

fun Date.isNotSameDay(date: Date) = !this.isSameDay(date)
