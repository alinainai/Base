package com.gas.app.learn.calendarfinal.day

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gas.app.R
import com.gas.app.learn.calendarfinal.CalendarTheme
import com.lib.commonsdk.kotlin.extension.gone
import com.lib.commonsdk.kotlin.extension.visible
import kotlinx.android.synthetic.main.v4_camera_calendar_selcet_day_item_layout.view.*


class DayItemViewHolder(parent: ViewGroup, private val theme: CalendarTheme) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.v4_camera_calendar_selcet_day_item_layout,parent,false)) {
    private val tvDayNum = itemView.tvDayNum
    private val selectBg = itemView.selectBg

    fun bind(model: CalendarDayModel) {

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
        tvDayNum.isSelected = model.isSelected && model.isEnabled
        if (model.isSelected && model.isEnabled) selectBg.visible() else selectBg.gone()

        //是否可选
        tvDayNum.isEnabled = model.isEnabled
        itemView.isEnabled = model.isEnabled
    }

    init {
        when (theme) {
            CalendarTheme.Gold -> {
                selectBg.setImageResource(R.drawable.home_smart_calendar_select_gold_circle)
                tvDayNum.setTextColor(itemView.context.resources.getColorStateList(R.color.v4_cloud_text_color_selector))
            }
            CalendarTheme.Blue -> {
                selectBg.setImageResource(R.drawable.home_smart_calendar_select_blue_circle)
                tvDayNum.setTextColor(itemView.context.resources.getColorStateList(R.color.v4_cloud_text_color_selector_blue))
            }
        }
    }

}

