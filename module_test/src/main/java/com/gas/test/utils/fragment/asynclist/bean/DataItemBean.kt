package com.gas.test.utils.fragment.asynclist.bean

import com.lib.commonsdk.utils.TimeUtils

class DataItemBean(val title: String, time: Long) : BaseTimestamp(time) {
    override fun uniqueId(): Long {
        return title.hashCode() + timeStamp
    }

    override fun variableParam(): String {
        return title
    }

    val timeZone = TimeUtils.getHour(timeStamp)
    val dateZone = TimeUtils.getDateWithoutTime(timeStamp)?:""

}