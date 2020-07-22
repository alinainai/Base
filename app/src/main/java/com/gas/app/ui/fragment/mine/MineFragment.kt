package com.gas.app.ui.fragment.mine

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
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
import com.gas.app.learn.customview.MyPageCloudAdTimeDownView
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


    private var holder: MyPageCloudAdTimeDownView? = null


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMineComponent.builder().appComponent(appComponent).view(this).build().inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        timeDown.setTimeDownStamp(24 * 1000 * 60 * 60)
        view?.let {

        }
        val transition = LayoutTransition()
        val animOut = ObjectAnimator.ofFloat(null, View.ALPHA, 1.0F, 0.2F)
        transition.setAnimator(LayoutTransition.DISAPPEARING, animOut)
//        ll_container.layoutTransition=transition
        timeDownHolder.setOnClickListener {
            showMessage("点击")
            timeDownHolder?.hide()
        }
        sampleText.setOnClickListener {
            timeDownHolder.show("优惠活动：", 24 * 1000 * 60 * 60)
//            timeDown.startTimeDown()
//            holder = MyPageCloudTimeDownViewHolder(view!!.findViewById(R.id.time_down_container))
        }
        sampleTextStop.setOnClickListener {
//            timeDown.stopDownTime()
            timeDownHolder.hide()
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