package com.gas.app.learn.calendarviewV2.itemlist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gas.app.learn.calendarviewV2.CalendarTheme
import com.gas.app.learn.calendarviewV2.data.CalendarDayModel

import org.joda.time.LocalDate

/**
 * 日历adapter
 */
class CalendarListAdapter(private val onDayItemClick: OnDayClickListener, val data: MutableList<CalendarDayModel>, val theme: CalendarTheme) : RecyclerView.Adapter<DayItemViewHolder>() {

    var mSelectDate: LocalDate? = null

    fun showData(select: LocalDate) {
        mSelectDate = select
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayItemViewHolder {
        val holder = DayItemViewHolder(parent, theme)
        //在onCreateViewHolder进行点击事件注入，不用放在onBindViewHolder中，会影响性能
        holder.itemView.setOnClickListener {
            holder.adapterPosition.takeIf { pos ->
                pos < data.size && pos >= 0
            }?.let { pos ->
                onDayItemClick.onDayItemClick(data[pos])
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: DayItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getItem(pos: Int): CalendarDayModel {
        return data[pos]
    }

    interface OnDayClickListener {
        fun onDayItemClick(date: CalendarDayModel)
    }

}