package com.gas.app.ui.fragment.mine

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.LazyLoadFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.gas.app.R
import com.gas.app.learn.calendarselect.mvp.CalendarSelectViewHolder
import com.gas.app.learn.customview.MyPageCloudTimeDownViewHolder
import com.gas.app.ui.fragment.mine.di.DaggerMineComponent
import com.gas.app.ui.fragment.mine.mvp.MineContract
import com.gas.app.ui.fragment.mine.mvp.MinePresenter
import kotlinx.android.synthetic.main.fragment_mine.*
import org.joda.time.LocalDate

/**
 * Author:
 * Time: 2016/8/12 14:28
 */
class MineFragment : LazyLoadFragment<MinePresenter>(), MineContract.View {

    private var mLocalDate = LocalDate.now()

    private lateinit var calendar: CalendarSelectViewHolder


    private lateinit var holder: MyPageCloudTimeDownViewHolder


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMineComponent.builder().appComponent(appComponent).view(this).build().inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        timeDown.setTimeDownStamp(24 * 1000 * 60 * 60)
        view?.let {
            holder = MyPageCloudTimeDownViewHolder(it.findViewById(R.id.time_down_container))
        }

        sampleText.setOnClickListener {
            timeDown.startTimeDown()
            holder.onClickCallback = ({
                showMessage("点击")
                holder.hide()
            })
            holder.show("优惠活动：", 24 * 1000 * 60 * 60)

        }
        sampleTextStop.setOnClickListener {
            timeDown.stopDownTime()
            holder.hide()
        }
    }

    override fun setData(data: Any?) {}


    override fun lazyLoadData() {}
    override val wrapContext: Context
        get() = context!!

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    companion object {
        const val TAG = "MineFragment"

        @JvmStatic
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }
}