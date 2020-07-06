package com.gas.app.learn.calendarviewV2.itemlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gas.app.R
import com.gas.app.learn.calendarviewV2.CalendarTheme
import com.gas.app.learn.calendarviewV2.MONTH_NAMES_RES_ID
import com.gas.app.learn.calendarviewV2.data.CalendarDayModel
import com.lib.commonsdk.kotlin.extension.gone
import com.lib.commonsdk.kotlin.extension.visible
import kotlinx.android.synthetic.main.v4_camera_calendar_selcet_day_item_layout.view.*


class DayItemViewHolder(parent: ViewGroup,private val theme: CalendarTheme) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.v4_camera_calendar_selcet_day_item_layout, parent, false)) {
    private val tvMonthNum = itemView.tvMonthNum
    private val tvDayNum = itemView.tvDayNum
    private val tvTodayTag = itemView.tvTodayTag
    private val selectBg = itemView.selectBg

    fun bind(model: CalendarDayModel) {
        if (model.dayOfMonth == 1) {//1号显示月份
            tvMonthNum.visible()
            tvMonthNum.setText(MONTH_NAMES_RES_ID[model.monthNum])
        } else {
            tvMonthNum.gone()
        }
        tvDayNum.text = model.dayOfMonth.toString()
        if (model.isToday) {
            when (theme) {
                CalendarTheme.Gold -> tvDayNum.setTextColor(itemView.context.resources.getColorStateList(R.color.v4_cloud_text_color_selector_today))
                CalendarTheme.Blue -> tvDayNum.setTextColor(itemView.context.resources.getColorStateList(R.color.v4_cloud_text_color_selector_blue_today))
            }
        } else {
            when (theme) {
                CalendarTheme.Gold -> tvDayNum.setTextColor(itemView.context.resources.getColorStateList(R.color.v4_cloud_text_color_selector))
                CalendarTheme.Blue -> tvDayNum.setTextColor(itemView.context.resources.getColorStateList(R.color.v4_cloud_text_color_selector_blue))
            }
        }
        //选中
        if(model.isSelected)  selectBg.visible() else selectBg.gone()
        tvDayNum.isSelected = model.isSelected
        //是否可选
        tvDayNum.isEnabled = model.isEnabled
        itemView.isEnabled = model.isEnabled
    }

    init {
        when (theme) {
            CalendarTheme.Gold -> {
                selectBg.setImageResource(R.mipmap.v4_calendar_play_day_selector)
                tvTodayTag.setTextColor(itemView.context.resources.getColor(R.color.calendar_item_view_text_color_vip))
                tvDayNum.setTextColor(itemView.context.resources.getColorStateList(R.color.v4_cloud_text_color_selector))
            }
            CalendarTheme.Blue -> {
                selectBg.setImageResource(R.drawable.home_smart_calendar_select_blue_circle)
                tvTodayTag.setTextColor(itemView.context.resources.getColor(R.color.smart_home_calendar_view_default_theme))
                tvDayNum.setTextColor(itemView.context.resources.getColorStateList(R.color.v4_cloud_text_color_selector_blue))
            }
        }

    }


}