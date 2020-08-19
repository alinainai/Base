package com.gas.app.ui.fragment.info

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.lib.util.Preconditions
import com.gas.app.R
import com.gas.app.calendar.sdk.data.CalendarDayModel
import com.gas.app.calendar.sdk.data.CalendarMonthModel
import com.gas.app.calendar.sdk.data.daysOfMonthOffset
import com.gas.app.calendar.sdk.utils.CalendarSelectListener
import com.gas.app.ui.fragment.info.di.DaggerInfoComponent
import com.gas.app.ui.fragment.info.mvp.InfoContract
import com.gas.app.ui.fragment.info.mvp.InfoPresenter
import kotlinx.android.synthetic.main.fragment_info.*
import org.joda.time.LocalDate

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/30/2019 14:54
 * ================================================
 */
class InfoFragment : BaseFragment<InfoPresenter?>(), InfoContract.View {


    private var mLocalDate = LocalDate.now()

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        val model= CalendarMonthModel(System.currentTimeMillis())
        calendarView.setMonthModel(model)
        calendarView.setCalendarSelectListener(object :CalendarSelectListener{
            override fun onCalendarSelect(calendar: CalendarDayModel, select: Boolean) {
                mLocalDate=calendar.localDate
                calendarView.setSelectDay(mLocalDate)
            }
        })
        calendarView.setSelectDay(mLocalDate)

    }

    override fun setData(data: Any?) {

    }
    override fun showLoading() {}
    override fun hideLoading() {}
    override fun showMessage(message: String) {
        Preconditions.checkNotNull(message)
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        Preconditions.checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {}

    companion object {
        @JvmStatic
        fun newInstance(): InfoFragment {
            return InfoFragment()
        }
    }
}