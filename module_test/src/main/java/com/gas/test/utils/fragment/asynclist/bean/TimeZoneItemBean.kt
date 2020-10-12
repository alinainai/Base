package com.gas.test.utils.fragment.asynclist.bean

import com.lib.commonsdk.kotlin.extension.time.formatDate
import java.util.*

class TimeZoneItemBean(timestamp: Long) : BaseTimestamp(timestamp) {

    val title = "${Date(timestamp).formatDate("HH")}--${Date(timestamp).formatDate("HH") ?: "0".toInt() + 1}"

    override fun uniqueId(): Long {
        return timeStamp
    }

    override fun variableParam(): String {
        return title
    }
}