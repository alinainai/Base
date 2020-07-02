package com.lib.commonsdk.kotlin.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Date的扩展函数
 */
fun Date.formatDate(): String? = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(this)

fun Date.formatDateToNumber(): String? = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(this)

val Date.isToday: Boolean
    get() = formatDate() == Date().formatDate()

fun Date.formatDatetime(): String? = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(this)

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
