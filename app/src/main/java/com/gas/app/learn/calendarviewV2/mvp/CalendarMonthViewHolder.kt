package com.gas.app.learn.calendarviewV2.mvp

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gas.app.R
import com.gas.app.learn.calendarviewV2.COLUMN_COUNT
import com.gas.app.learn.calendarviewV2.CalendarTheme
import com.gas.app.learn.calendarviewV2.data.CalendarMonthModel
import com.gas.app.learn.calendarviewV2.itemlist.CalendarListAdapter
import org.joda.time.LocalDate

class CalendarMonthViewHolder(private val view: View, private val model: CalendarMonthModel, onDayItemClick: CalendarListAdapter.OnDayClickListener, theme: CalendarTheme) {

    private var adapter: CalendarListAdapter

    init {
        val thirdRV = view.findViewById<RecyclerView>(R.id.monthModeListView)
        thirdRV.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(view.context, COLUMN_COUNT)
        thirdRV.layoutManager = gridLayoutManager;
        adapter = CalendarListAdapter(onDayItemClick, model.dayList, theme)
        thirdRV.adapter = adapter
    }

    fun getItemView(): View {
        return view
    }

    fun getMonthNum(): Int {
        return model.monthNum
    }

    fun getMonthTitle(): String {
        return model.yyyyMM
    }

    fun showSelect(select: LocalDate) {
        adapter.showData(select)
    }

    fun updateDate() {
        adapter.notifyDataSetChanged()
    }


}