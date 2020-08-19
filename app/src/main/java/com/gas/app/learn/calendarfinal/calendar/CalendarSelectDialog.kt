package com.gas.app.learn.calendarfinal.calendar

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.gas.app.R
import com.gas.app.learn.calendarfinal.CalendarTheme
import com.gas.app.learn.calendarfinal.day.CalendarDayModel
import com.gas.app.learn.calendarfinal.month.CalendarListAdapter
import com.gas.app.learn.calendarfinal.month.CalendarMonthModel
import com.gas.app.learn.calendarfinal.month.CalendarMonthViewHolder
import com.gas.app.learn.calendarviewV2.MONTH_COUNT
import org.joda.time.LocalDate
import java.util.logging.Handler

class CalendarSelectDialog(val context: Context,
                           private val theme: CalendarTheme,
                           private val onDayItemClick: OnDayClickCallBack) : CalendarListAdapter.OnDayClickListener {

    private var dialog: Dialog
    private val monthHolders = mutableListOf<CalendarMonthViewHolder>()
    private var initTimeStamp: Long
    private var viewPager: ViewPager
    private var tvMonthNum: TextView
    private var mCurPos: Int = 0

    init {
        initTimeStamp = System.currentTimeMillis()
        dialog = Dialog(context, R.style.dialogFullscreen)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_calendar_select_for_message, null)
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
        initPagers()
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

    /**
     * 展示月份
     */
    private fun initPagers() {
        for (i in 0 until MONTH_COUNT) {
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_calendar_select_for_message_item, null)
            monthHolders.add(CalendarMonthViewHolder(view, CalendarMonthModel(initTimeStamp, -3 + i), this, theme))
        }
        val adapter = CalendarPagerAdapter(monthHolders)
        viewPager.adapter = adapter
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(monthHolders.size - 1, false)
        mCurPos = monthHolders.size - 1
    }

    fun showSelect(select: LocalDate) {
        if (LocalDate(initTimeStamp).monthOfYear == LocalDate.now().monthOfYear) {//创建时的默认月份和展示时月份相同
            monthHolders.indexOfFirst { select.monthOfYear == it.getMonthNum() }
                    .takeIf { it > -1 && it < monthHolders.size }?.let {
                monthHolders.forEach { holder ->
                    holder.showSelect(select)
                }
                viewPager.setCurrentItem(it, false)
                dialog.show()
            }
        } else {
            initTimeStamp = System.currentTimeMillis()
        }
    }

    interface OnDayClickCallBack {
        fun onDayItemClick(date: CalendarDayModel)
    }

    override fun onDayItemClick(date: CalendarDayModel) {
        mCurPos.takeIf { it in 0 until monthHolders.size }?.let {
            monthHolders[it].showSelect(date.localDate)
        }
        onDayItemClick.onDayItemClick(date)
        dialog.dismiss()
    }

}