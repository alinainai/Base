package com.gas.app.learn.calendarviewV2.itemlist

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.recyclerview.widget.RecyclerView
import com.gas.app.R
import com.gas.app.learn.calendarviewV2.CalendarTheme
import com.gas.app.learn.calendarviewV2.MONTH_NAMES_RES_ID
import com.gas.app.learn.calendarviewV2.data.CalendarDayModel
import com.lib.commonsdk.kotlin.extension.gone
import com.lib.commonsdk.kotlin.extension.visible
import kotlinx.android.synthetic.main.v4_camera_calendar_selcet_day_item_layout.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout


class DayItemViewHolder(parent: ViewGroup, private val theme: CalendarTheme) : RecyclerView.ViewHolder(CalendarDayUI().createView(AnkoContext.create(parent.context, parent))) {
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

class CalendarDayUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        constraintLayout {
            imageView {
                id = R.id.selectBg
                imageResource = R.drawable.home_smart_calendar_select_gold_circle
            }.lparams(width = dip(28), height = dip(28)) {
                bottomToBottom = R.id.tvDayNum
                startToStart = R.id.tvDayNum
                topToTop = R.id.tvDayNum
                endToEnd = R.id.tvDayNum
            }
            textView {
                id = R.id.tvDayNum
                gravity = Gravity.CENTER
                textColor = resources.getColor(R.color.v4_cloud_text_color_selector)
                textSize = 16f //sp

            }.lparams(width = matchParent, height = 0) {
                bottomToBottom = PARENT_ID
                startToStart = PARENT_ID
                topToTop = PARENT_ID
                endToEnd = PARENT_ID
            }
            lparams(width = matchParent, height = dip(50))
        }
    }
}