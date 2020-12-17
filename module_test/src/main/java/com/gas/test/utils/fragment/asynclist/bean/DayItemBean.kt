package com.gas.test.utils.fragment.asynclist.bean

import com.lib.commonsdk.extension.time.formatDate
import java.util.*

class DayItemBean(timestamp: Long) : BaseTimestamp(timestamp) {

    val title = Date(timestamp).formatDate("MM月dd日")

    override fun uniqueId(): Long {
        return timeStamp
    }

    override fun variableParam(): String {
        return title ?: ""
    }
}