package com.gas.app.calendar.sdk.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.gas.app.R
import com.gas.app.calendar.sdk.data.CalendarDayModel
import com.gas.app.calendar.sdk.data.CalendarMonthModel
import com.gas.app.calendar.sdk.data.CalendarTheme
import com.gas.app.calendar.sdk.data.MONTH_COUNT_NEW
import com.gas.app.calendar.sdk.utils.CalendarSelectListener
import com.gas.app.calendar.sdk.view.SingleMonthLayout
import org.joda.time.LocalDate

class CalendarSelectDialog(val context: Context,
                           private val theme: CalendarTheme,
                           private val onDayItemClick: OnDayClickCallBack) : CalendarSelectListener {

    private var dialog: Dialog
    private var initTimeStamp: Long
    private var viewPager: ViewPager
    private var tvMonthNum: TextView
    private var mCurPos: Int = 0
    private val monthHolders = mutableListOf<CalendarMonthViewHolder>()
    private val view: View

    init {
        initTimeStamp = System.currentTimeMillis()
        dialog = Dialog(context, R.style.dialogFullscreen)
        view = LayoutInflater.from(context).inflate(R.layout.dialog_calendar_select_for_message, null)
        tvMonthNum = view.findViewById(R.id.tvMonthNum)
        val btnPreMonth = view.findViewById<ImageView>(R.id.btnPreMonth)
        val btnNextMonth = view.findViewById<ImageView>(R.id.btnNextMonth)
        viewPager = view.findViewById(R.id.listContainer)
        btnPreMonth.setOnClickListener {
            (mCurPos - 1).takeIf { it in 0 until monthHolders.size }?.let {
                viewPager.setCurrentItem(it, true)
            }
        }
        btnNextMonth.setOnClickListener {
            (mCurPos + 1).takeIf { it in 0 until monthHolders.size }?.let {
                viewPager.setCurrentItem(it, true)
            }
        }
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position in 0 until monthHolders.size) {
                    tvMonthNum.text = monthHolders[position].getMonthTitle()
                    btnPreMonth.isEnabled = position > 0
                    btnNextMonth.isEnabled = position < monthHolders.size - 1
                    mCurPos = position
                }
            }
        })
        for (i in 0 until MONTH_COUNT_NEW) {
            val monthView = LayoutInflater.from(context).inflate(R.layout.layout_month_calendar_view, null).findViewById<SingleMonthLayout>(R.id.calendarView)
            monthHolders.add(CalendarMonthViewHolder(monthView, CalendarMonthModel(initTimeStamp, -3 + i), this, theme))
        }
        val adapter = CalendarPagerAdapter(monthHolders)
        viewPager.adapter = adapter
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(monthHolders.size - 1, false)
        mCurPos = monthHolders.size - 1

        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(true)
        //点击back键可以取消dialog
        dialog.setCancelable(true)
        dialog.window?.let { window ->
            window.setGravity(Gravity.BOTTOM)
            val lp = window.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
            //设置窗口出现和窗口隐藏的动画
            window.setWindowAnimations(com.base.baseui.R.style.dialogBottomWindowAnim)
        }
    }

    fun showSelect(select: LocalDate) {
        //创建时的默认日期和展示时日期不同，需要重置时间戳
        var initLocalDate = LocalDate(initTimeStamp)
        val nowLocalDate = LocalDate.now()
        if (initLocalDate.monthOfYear != nowLocalDate.monthOfYear || initLocalDate.dayOfMonth != nowLocalDate.dayOfMonth) {
            initTimeStamp = System.currentTimeMillis()
            monthHolders.forEach { holder ->
                holder.updateDate(initTimeStamp)
            }
            initLocalDate = LocalDate(initTimeStamp)
        }
        val index = monthHolders.indexOfFirst { select.monthOfYear == it.getMonthNum() }
        if (index > -1 && index < monthHolders.size) {
            monthHolders.forEach { holder ->
                holder.showSelect(select)
            }
            if (mCurPos != index) {
                viewPager.setCurrentItem(index, false)
            }
        } else {
            monthHolders.forEach { holder ->
                holder.showSelect()
            }
            val reIndex = monthHolders.indexOfFirst { initLocalDate.monthOfYear == it.getMonthNum() }
            if (mCurPos != reIndex) {
                viewPager.setCurrentItem(reIndex, false)
            }
        }
        dialog.show()
    }

    interface OnDayClickCallBack {
        fun onDayItemClick(date: CalendarDayModel)
    }

    override fun onCalendarSelect(calendar: CalendarDayModel, select: Boolean) {
        mCurPos.takeIf { it in 0 until monthHolders.size }?.let {
            monthHolders[it].showSelect(calendar.localDate)
        }
        onDayItemClick.onDayItemClick(calendar)
        view.post { dialog.dismiss() }
    }

}