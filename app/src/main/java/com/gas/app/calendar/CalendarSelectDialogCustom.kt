package com.gas.app.calendar

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

class CalendarSelectDialogCustom(val context: Context)  {

    private var dialog: Dialog
    private var tvMonthNum: TextView

    init {
        dialog = Dialog(context, R.style.dialogFullscreen)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_calendar_select_for_message_v2, null)
        tvMonthNum = view.findViewById(R.id.tvMonthNum)
        val btnPreMonth = view.findViewById<ImageView>(R.id.btnPreMonth)
        val btnNextMonth = view.findViewById<ImageView>(R.id.btnNextMonth)

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



    fun showSelect() {
        dialog.show()
    }



}