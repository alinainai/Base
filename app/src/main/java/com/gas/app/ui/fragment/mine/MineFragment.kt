package com.gas.app.ui.fragment.mine

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.LazyLoadFragment
import com.base.lib.di.component.AppComponent
import com.gas.app.R
import com.gas.app.learn.calendarselect.CalendarTheme
import com.gas.app.learn.calendarselect.data.CalendarDayModel
import com.gas.app.learn.calendarselect.mvp.CalendarSelectViewHolder
import com.gas.app.ui.fragment.mine.di.DaggerMineComponent
import com.gas.app.ui.fragment.mine.mvp.MineContract
import com.gas.app.ui.fragment.mine.mvp.MinePresenter
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.v4_calendar_select_view_for_message.*
import org.joda.time.LocalDate

/**
 * Author:
 * Time: 2016/8/12 14:28
 */
class MineFragment : LazyLoadFragment<MinePresenter>(), MineContract.View {

    private var mLocalDate = LocalDate.now()

    private lateinit var calendar: CalendarSelectViewHolder


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMineComponent.builder().appComponent(appComponent).view(this).build().inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        calendar = CalendarSelectViewHolder(calendarBg, CalendarTheme.Gold).apply {
            onCalendarToggle = { isShow -> sampleText.text = if (isShow) "打开" else "关闭" }
            onDayItemClick = { date -> mLocalDate = date.localDate }
        }
        sampleText.setOnClickListener {
            calendar.show(mLocalDate)
        }
    }

    override fun setData(data: Any?) {}


    override fun lazyLoadData() {}
    override val wrapContext: Context
        get() = context!!

    override fun showMessage(message: String) {}

    companion object {
        const val TAG = "MineFragment"

        @JvmStatic
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }
}