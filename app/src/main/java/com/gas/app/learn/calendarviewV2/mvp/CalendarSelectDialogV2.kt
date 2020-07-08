package com.gas.app.learn.calendarviewV2.mvp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.viewpager.widget.ViewPager
import com.gas.app.R
import com.gas.app.R2.id.wrap_content
import com.gas.app.learn.calendarviewV2.BaseDialogFragment
import com.gas.app.learn.calendarviewV2.CalendarTheme
import com.gas.app.learn.calendarviewV2.MONTH_COUNT
import com.gas.app.learn.calendarviewV2.data.CalendarDayModel
import com.gas.app.learn.calendarviewV2.data.CalendarMonthModel
import com.gas.app.learn.calendarviewV2.itemlist.CalendarListAdapter
import com.gas.app.learn.calendarviewV2.itemlist.CalendarPagerAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.viewPager
import org.joda.time.LocalDate

class CalendarSelectDialogV2(private val theme: CalendarTheme) : BaseDialogFragment(), CalendarListAdapter.OnDayClickListener {

    private var onDayItemClick: OnDayClickCallBack? = null
    private val monthHolders = mutableListOf<CalendarMonthViewHolder>()
    private var initTimeStamp: Long
//    private var viewPager: ViewPager
//    private var tvMonthNum: TextView
    private var mCurPos: Int = 0

    fun setOnDayClickCallBack(onDayItemClick: OnDayClickCallBack) {
        this.onDayItemClick = onDayItemClick
    }

    init {


        initTimeStamp = System.currentTimeMillis()
//        val view = LayoutInflater.from(context).inflate(R.layout.dialog_calendar_select_for_message, null)
//        tvMonthNum = view.findViewById(R.id.tvMonthNum)
//        viewPager = view.findViewById(R.id.listContainer)
//
//
//        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {
//            }
//
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//            }
//
//            override fun onPageSelected(position: Int) {
//                if (position in 0 until monthHolders.size) {
//                    tvMonthNum.text = monthHolders[position].getMonthTitle()
//                    btnPreMonth.isEnabled = position > 0
//                    btnNextMonth.isEnabled = position < monthHolders.size - 1
//                    mCurPos = position
//                }
//            }
//        })
//
//        for (i in 0 until MONTH_COUNT) {
//            val view = LayoutInflater.from(context).inflate(R.layout.dialog_calendar_select_for_message_item, null)
//            monthHolders.add(CalendarMonthViewHolder(view, CalendarMonthModel(initTimeStamp, -3 + i), this, theme))
//        }
//        val adapter = CalendarPagerAdapter(monthHolders)
//        viewPager.adapter = adapter
//        adapter.notifyDataSetChanged()
//        viewPager.setCurrentItem(monthHolders.size - 1, false)
//        mCurPos = monthHolders.size - 1

        generateView {
            constraintLayout {
                backgroundResource = R.drawable.bg_cloud_record_select_list_dialog
                imageView {
                    id = R.id.btnPreMonth
                    scaleType = ImageView.ScaleType.FIT_START
                    imageResource = R.drawable.dialog_calendar_select_pre_arrow
                    onClick {
                        (mCurPos - 1).takeIf { it in 0 until monthHolders.size }?.let {
//                            viewPager.setCurrentItem(it, true)
                        }
                    }
                }.lparams(width = dip(50), height = dip(30)) {
                    marginStart = dip(10)
                    startToStart=PARENT_ID
                    bottomToBottom=R.id.tvMonthNum
                    topToTop=R.id.tvMonthNum
                }
                textView {
                    id = R.id.tvMonthNum
                    textColor = Color.parseColor("#4C3F32")
                    textSize = 20f //sp
                }.lparams {
                    topMargin = dip(8)
                    endToEnd= PARENT_ID
                    startToStart= PARENT_ID
                    topToTop= PARENT_ID
                }
                imageView {
                    id = R.id.btnNextMonth
                    scaleType = ImageView.ScaleType.FIT_END
                    imageResource = R.drawable.dialog_calendar_select_next_arrow
                    onClick {
                        (mCurPos + 1).takeIf { it in 0 until monthHolders.size }?.let {
//                            viewPager.setCurrentItem(it, true)
                        }
                    }
                }.lparams(width = dip(50), height = dip(30)) {
                    marginEnd = dip(10)
                    bottomToBottom=R.id.tvMonthNum
                    endToEnd= PARENT_ID
                    topToTop=R.id.tvMonthNum
                }
                linearLayout {
                    id = R.id.weekDaysBanner
                    backgroundColor = Color.parseColor("#FBF6F2")
                    topPadding = dip(6)
                    bottomPadding = dip(6)
                    textView {
                        id=R.id.calendarWeekSun
                        textColor = Color.parseColor("#2D2D2D")
                        textSize = 14f //sp
                        gravity = Gravity.CENTER
                        text = resources.getString(R.string.smart_home_camera_week_day_name_7)
                    }.lparams(width = 0) {
                        weight = 1f
                    }
                    textView {
                        id=R.id.calendarWeekMon
                        textColor = Color.parseColor("#2D2D2D")
                        textSize = 14f //sp
                        gravity = Gravity.CENTER
                        text = resources.getString(R.string.smart_home_camera_week_day_name_1)
                    }.lparams(width = 0) {
                        weight = 1f
                    }
                    textView {
                        id=R.id.calendarWeekTue
                        textColor = Color.parseColor("#2D2D2D")
                        textSize = 14f //sp
                        gravity = Gravity.CENTER
                        text = resources.getString(R.string.smart_home_camera_week_day_name_2)
                    }.lparams(width = 0) {
                        weight = 1f
                    }
                    textView {
                        id=R.id.calendarWeekWed
                        textColor = Color.parseColor("#2D2D2D")
                        textSize = 14f //sp
                        gravity = Gravity.CENTER
                        text = resources.getString(R.string.smart_home_camera_week_day_name_3)
                    }.lparams(width = 0) {
                        weight = 1f
                    }
                    textView {
                        id=R.id.calendarWeekThur
                        textColor = Color.parseColor("#2D2D2D")
                        textSize = 14f //sp
                        gravity = Gravity.CENTER
                        text = resources.getString(R.string.smart_home_camera_week_day_name_4)
                    }.lparams(width = 0) {
                        weight = 1f
                    }
                    textView {
                        id=R.id.calendarWeekFri
                        textColor = Color.parseColor("#2D2D2D")
                        textSize = 14f //sp
                        gravity = Gravity.CENTER
                        text = resources.getString(R.string.smart_home_camera_week_day_name_5)
                    }.lparams(width = 0) {
                        weight = 1f
                    }
                    textView {
                        id=R.id.calendarWeekSat
                        textColor = Color.parseColor("#2D2D2D")
                        textSize = 14f //sp
                        gravity = Gravity.CENTER
                        text = resources.getString(R.string.smart_home_camera_week_day_name_6)
                    }.lparams(width = 0) {
                        weight = 1f
                    }
                }.lparams(width = matchParent) {
                    endToEnd= PARENT_ID
                    startToStart= PARENT_ID
                    topToBottom=R.id.tvMonthNum
                    topMargin = dip(8)
                }
                viewPager() {
                    id = R.id.listContainer
                }.lparams(width = 0, height = dip(300)){
                    endToEnd= PARENT_ID
                    startToStart= PARENT_ID
                    topToBottom=R.id.weekDaysBanner
                }
                view {
                    id = R.id.divider
                    backgroundColor = Color.parseColor("#F5F6F8")

                }.lparams(width = matchParent, height = dip(1)){
                    endToEnd= PARENT_ID
                    startToStart= PARENT_ID
                    topToBottom=R.id.listContainer
                }
                textView {
                    id = R.id.tvDialogClose
                    backgroundColor = resources.getColor(R.color.public_white)
                    gravity = Gravity.CENTER
                    text = resources.getString(R.string.common_cancel)
                    textColor = Color.parseColor("#999999")
                    textSize = 16f //sp
                }.lparams(width = matchParent, height = dip(50)){
                    endToEnd= PARENT_ID
                    startToStart= PARENT_ID
                    topToBottom=R.id.divider
                }
            }
        }


    }

    override fun onResume() {
        dialog?.window?.apply {
            val params = attributes
            params.width = matchParent
            params.height = wrapContent
            attributes = params
            setGravity(Gravity.BOTTOM)
            //不知道为什么要设置个透明背景才能全屏显示
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setWindowAnimations(com.base.baseui.R.style.dialogBottomWindowAnim)
        }
        super.onResume()
    }


    fun showSelect(select: LocalDate) {
        if (LocalDate(initTimeStamp).monthOfYear == LocalDate.now().monthOfYear) {//创建时的默认月份和展示时月份相同
            monthHolders.indexOfFirst { select.monthOfYear == it.getMonthNum() }.takeIf { it > -1 && it < monthHolders.size }?.let {
                monthHolders.forEach { holder ->
                    holder.showSelect(select)
                }
//                viewPager.setCurrentItem(it, false)
//                show()
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
        onDayItemClick?.onDayItemClick(date)
        dismiss()
    }

}