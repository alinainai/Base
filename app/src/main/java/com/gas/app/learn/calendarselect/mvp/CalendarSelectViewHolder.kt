package com.gas.app.learn.calendarselect.mvp

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.gas.app.R
import com.gas.app.learn.calendarselect.CalendarTheme
import com.gas.app.learn.calendarselect.data.CalendarDayModel
import com.gas.app.learn.calendarselect.data.CalendarMonthModel
import com.gas.app.learn.calendarselect.itemlist.CalendarListAdapter
import com.gas.app.learn.calendarselect.itemlist.CalendarPagerAdapter
import com.gas.app.learn.calendarviewV2.MONTH_COUNT
import com.lib.commonsdk.kotlin.extension.gone
import com.lib.commonsdk.kotlin.extension.visible

import org.joda.time.LocalDate

class CalendarSelectViewHolder(private val calendarLayout: View, private val theme: CalendarTheme) : CalendarListAdapter.OnDayClickListener {
    var onDayItemClick: ((CalendarDayModel) -> Unit)? = null
    var onCalendarToggle: ((Boolean) -> Unit)? = null
    private val monthHolders = mutableListOf<CalendarMonthViewHolder>()
    private var initTimeStamp: Long
    private var mCurPos: Int = 0
    private val viewPager: ViewPager
    private val tvMonthNum: TextView
    private val calendarBg: View
    private val calendarContainer: View

    init {
        tvMonthNum = calendarLayout.findViewById(R.id.tvMonthNum)
        calendarBg = calendarLayout.findViewById(R.id.calendarBg)
        calendarContainer = calendarLayout.findViewById(R.id.calendarContainer)
        val btnPreMonth = calendarLayout.findViewById<ImageView>(R.id.btnPreMonth)
        val btnNextMonth = calendarLayout.findViewById<ImageView>(R.id.btnNextMonth)
        viewPager = calendarLayout.findViewById(R.id.listContainer)
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
        calendarLayout.findViewById<View>(R.id.tvDialogClose).setOnClickListener {
            hide()
        }
        calendarBg.setOnClickListener {
            hide()
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
        initTimeStamp = System.currentTimeMillis()
        for (i in 0 until MONTH_COUNT) {
            val pagerView = LayoutInflater.from(calendarLayout.context).inflate(R.layout.dialog_calendar_select_for_message_item, null)
            monthHolders.add(CalendarMonthViewHolder(pagerView, CalendarMonthModel(initTimeStamp, -3 + i), this, theme))
        }
        val adapter = CalendarPagerAdapter(monthHolders)
        viewPager.adapter = adapter
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(monthHolders.size - 1, false)
        mCurPos = monthHolders.size - 1
        calendarLayout.gone()
    }

    fun show(select: LocalDate) {
        monthHolders.indexOfFirst { select.monthOfYear == it.getMonthNum() }
                .takeIf { it > -1 && it < monthHolders.size }?.let {
                    monthHolders.forEach { holder ->
                        holder.showSelect(select)
                    }
                    viewPager.setCurrentItem(it, false)
                }
        calendarLayout.visible()

        onCalendarToggle?.invoke(true)
        calendarContainer.translationY = calendarContainer.height.toFloat()
        calendarContainer
                .animate()
                .translationY(0f)
                .setDuration(300)
                .setInterpolator(LinearInterpolator())
                .setListener(null)
                .start()
        calendarBg.alpha = 0f
        calendarBg.animate()
                .setDuration(300)
                .alpha(1F)
                .start()

    }

    fun hide() {
        onCalendarToggle?.invoke(false)
        calendarContainer
                .animate()
                .setDuration(300)
                .translationY(calendarContainer.height.toFloat())
                .setInterpolator(LinearInterpolator())
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animator: Animator) {}
                    override fun onAnimationEnd(animator: Animator) {
                        calendarLayout.gone()
                    }
                    override fun onAnimationCancel(animator: Animator) {}
                    override fun onAnimationRepeat(animator: Animator) {}
                })
                .start()
        calendarBg.animate()
                .setDuration(300)
                .alpha(0f)
                .start()
    }

    interface OnDayClickCallBack {
        fun onDayItemClick(date: CalendarDayModel)
    }

    interface OnCalendarToggle {
        fun onCalendarToggle(isShow: Boolean)
    }

    override fun onDayItemClick(date: CalendarDayModel) {
        mCurPos.takeIf { it in 0 until monthHolders.size }?.let {
            monthHolders[it].showSelect(date.localDate)
        }
        onDayItemClick?.invoke(date)
    }

}