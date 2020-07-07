package com.gas.app.learn.calendarviewV2.itemlist

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.gas.app.learn.calendarviewV2.mvp.CalendarMonthViewHolder
import java.util.*

class CalendarPagerAdapter(private val views: List<CalendarMonthViewHolder>) : PagerAdapter() {
    override fun getCount(): Int {
        return views.size
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view === any
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(views[position].getItemView())
        return views[position].getItemView()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(views[position].getItemView())
    }

}