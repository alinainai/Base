package com.gas.test.utils.fragment.asynclist.bean


object BeanUtils {

    fun originToFormat(events: List<DataItemBean>, isList: Boolean = true): List<BaseTimestamp> {
        val allViewModels = mutableListOf<BaseTimestamp>()
        var mLastItemDate: String? = null
        var mTimeZoneKey: Int? = null
        if (events.isNotEmpty()) {
            for (toAdd in events) {
                var daySplit: DayItemBean? = null
                if (mLastItemDate != null) {
                    if (mLastItemDate != toAdd.dateZone) {
                        daySplit = DayItemBean(toAdd.timeStamp)
                    }
                } else {
                    daySplit = DayItemBean(toAdd.timeStamp)
                }
                mLastItemDate = toAdd.dateZone
                if (daySplit != null) {
                    allViewModels.add(daySplit)
                }
                //如果是grid模式，插入时间段布局
                if (!isList) {
                    var timeZoneSplit: TimeZoneItemBean? = null
                    if (mTimeZoneKey != null) {
                        if (mTimeZoneKey != toAdd.timeZone) {
                            // next timeZone, need add timeZone split
                            timeZoneSplit = TimeZoneItemBean(toAdd.timeStamp)
                        } // else same timeZone, no need add timeZone split
                    } else {
                        timeZoneSplit = TimeZoneItemBean(toAdd.timeStamp)
                    }
                    mTimeZoneKey = toAdd.timeZone
                    if (timeZoneSplit != null) {
                        allViewModels.add(timeZoneSplit)
                    }
                }
                allViewModels.add(toAdd)
            }
        }
        return allViewModels
    }

}