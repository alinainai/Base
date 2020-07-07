package com.gas.app.learn.calendarviewV2.mvp

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gas.app.R
import com.gas.app.R2.id.column
import com.gas.app.learn.calendarviewV2.CalendarTheme
import com.gas.app.learn.calendarviewV2.itemlist.CalendarListAdapter
import com.gas.app.learn.calendarviewV2.utils.transformdata.GridPagerUtils
import com.gas.app.learn.calendarviewV2.utils.transformdata.MonthDataTransform
import com.gas.app.learn.calendarviewV2.utils.widge.GridPagerSnapHelper
import org.joda.time.LocalDate


class CalendarSelectDialog(val context: Context, theme: CalendarTheme, onDayItemClick: CalendarListAdapter.OnDayClickListener) {

    private var dialog: Dialog
    private var adapter: CalendarListAdapter
    private var calendarSelectViewModel: CalendarSelectViewModel

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_calendar_select_for_message, null)
        calendarSelectViewModel = CalendarSelectViewModel()
        val thirdRV = view.findViewById<RecyclerView>(R.id.monthModeListView)
        thirdRV.setHasFixedSize(true)
        //setLayoutManager
        val gridLayoutManager = GridLayoutManager(context, 6, LinearLayoutManager.HORIZONTAL, false);
        thirdRV.layoutManager = gridLayoutManager;
        //attachToRecyclerView
        val gridPagerSnapHelper =  GridPagerSnapHelper()
        gridPagerSnapHelper.setRow(6).setColumn(7)
        gridPagerSnapHelper.attachToRecyclerView(thirdRV)
        val dataList = GridPagerUtils.transformAndFillEmptyData(
                MonthDataTransform(), calendarSelectViewModel.dayList)
        adapter = CalendarListAdapter(onDayItemClick,dataList  , theme)
        thirdRV.adapter = adapter

        dialog = Dialog(context, R.style.dialogFullscreen)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(true)
        //点击back键可以取消dialog
        dialog.setCancelable(true)
        dialog.window?.let { window ->
            window.setGravity(Gravity.BOTTOM)
            //设置窗口出现和窗口隐藏的动画
            window.setWindowAnimations(com.base.baseui.R.style.dialogBottomWindowAnim)
            val lp = window.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
        }
    }

    fun showSelect(select: LocalDate) {
        adapter.showData(select)
        dialog.show()
    }

}