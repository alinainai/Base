package com.gas.test.utils.fragment.asynclist.bean

class DataItemBean(val title: String, time: Long) : BaseTimestamp(time) {
    override fun uniqueId(): Long {
        return title.hashCode() + timeStamp
    }

    override fun variableParam(): String {
        return title
    }
}