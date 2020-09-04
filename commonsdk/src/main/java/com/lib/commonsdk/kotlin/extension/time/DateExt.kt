package com.lib.commonsdk.kotlin.extension.time

import org.threeten.bp.DateTimeUtils
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Date的扩展函数
 */
fun Date.formatDate(format: String = "yyyy-MM-dd"): String? = SimpleDateFormat(format, Locale.getDefault()).format(this)

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

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
}

fun Date.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(DateTimeUtils.toInstant(this),ZoneId.systemDefault())
}

fun LocalDateTime.format(format:String = "yyyy-MM-dd HH:mm:ss"):String{
    val df: DateTimeFormatter = DateTimeFormatter.ofPattern(format)
    return df.format(this)
}


