package com.gas.app.learn.calendarfinal

import com.gas.app.R


sealed class CalendarTheme {
    object Gold : CalendarTheme()
    object Blue : CalendarTheme()
}
const val MONTH_COUNT_NEW=4
const val ROW_COUNT_NEW = 6
const val COLUMN_COUNT_NEW = 7
const val TOTAL_COUNT_NEW = ROW_COUNT_NEW * COLUMN_COUNT_NEW
val MONTH_NAMES_RES_ID_NEW = intArrayOf(R.string.smart_home_camera_month_name_1, R.string.smart_home_camera_month_name_2, R.string.smart_home_camera_month_name_3,
        R.string.smart_home_camera_month_name_4, R.string.smart_home_camera_month_name_5, R.string.smart_home_camera_month_name_6, R.string.smart_home_camera_month_name_7, R.string.smart_home_camera_month_name_8,
        R.string.smart_home_camera_month_name_9, R.string.smart_home_camera_month_name_10, R.string.smart_home_camera_month_name_11, R.string.smart_home_camera_month_name_12)
val WEEK_DAY_NAMES_RES_ID_NEW = intArrayOf(R.string.smart_home_camera_week_day_name_7, R.string.smart_home_camera_week_day_name_1
        , R.string.smart_home_camera_week_day_name_2, R.string.smart_home_camera_week_day_name_3, R.string.smart_home_camera_week_day_name_4, R.string.smart_home_camera_week_day_name_5, R.string.smart_home_camera_week_day_name_6)
