package com.gas.app.ui.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.BindView
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.base.componentservice.gank.service.GankInfoService
import com.base.componentservice.test.service.TestInfoService
import com.base.componentservice.zhihu.service.ZhihuInfoService
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.lib.util.Preconditions
import com.gas.app.R
import com.gas.app.R2
import com.gas.app.learn.calendarviewV2.CalendarTheme
import com.gas.app.learn.calendarviewV2.data.CalendarDayModel
import com.gas.app.learn.calendarviewV2.mvp.CalendarSelectDialog
import com.gas.app.learn.calendarviewV2.mvp.CalendarSelectDialogV2
import com.gas.app.learn.calendarviewV2.mvp.CalendarSelectDialogV3
import com.gas.app.ui.fragment.main.di.DaggerMainComponent
import com.gas.app.ui.fragment.main.mvp.MainContract
import com.gas.app.ui.fragment.main.mvp.MainPresenter
import com.lib.commonsdk.constants.RouterHub
import com.lib.commonsdk.utils.Utils
import kotlinx.android.synthetic.main.fragment_main.*
import org.joda.time.LocalDate

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/30/2019 15:27
 * ================================================
 */
class MainFragment : BaseFragment<MainPresenter?>(), MainContract.View {

    @Autowired(name = RouterHub.ZHIHU_SERVICE_ZHIHUINFOSERVICE)
    lateinit var mZhihuInfoService: ZhihuInfoService

    @Autowired(name = RouterHub.GANK_SERVICE_GANKINFOSERVICE)
    lateinit var mGankInfoService: GankInfoService

    @Autowired(name = RouterHub.TEST_SERVICE_TESTINFOSERVICE)
    lateinit var mTestInfoService: TestInfoService


    private var dialog: CalendarSelectDialog? = null
    private var dialogV2: CalendarSelectDialogV2? = null
    private var dialogV3: CalendarSelectDialogV3? = null
    private var mLocalDate = LocalDate.now()
    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        loadModuleInfo()

        btnModule1.setOnClickListener {
            Utils.navigation(activity, RouterHub.ZHIHU_HOMEACTIVITY)
        }
        btnModule2.setOnClickListener {
            Utils.navigation(activity, RouterHub.GANK_MAINACTIVITY)
        }
        btnModule3.setOnClickListener {
            Utils.navigation(activity, RouterHub.TEST_HOMEACTIVITY)
        }
        btnPlugin1.setOnClickListener {
            dialog?.showSelect(mLocalDate)
        }
        btnPlugin2.setOnClickListener {
            dialog?.showSelect(mLocalDate)
        }

        dialog = CalendarSelectDialog(activity!!, CalendarTheme.Gold, object : CalendarSelectDialog.OnDayClickCallBack {
            override fun onDayItemClick(date: CalendarDayModel) {
                mLocalDate = date.localDate
            }
        })
        dialogV2 = CalendarSelectDialogV2(CalendarTheme.Gold)
        dialogV2!!.setCanceledOnTouchOutside(true)
        dialogV3 = CalendarSelectDialogV3(activity!!, CalendarTheme.Gold)
        dialogV3!!.setOnDayClickCallBack(object : CalendarSelectDialogV3.OnDayClickCallBack {
            override fun onDayItemClick(date: CalendarDayModel) {
                mLocalDate = date.localDate
            }
        })
    }

    override fun setData(data: Any?) {}
    override fun showMessage(message: String) {
        Preconditions.checkNotNull(message)
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        Preconditions.checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {}
    private fun loadModuleInfo() {
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
        if (mZhihuInfoService != null) {
            btnModule2.text = mZhihuInfoService!!.info.name
        }
        if (mGankInfoService != null) {
            btnModule2.text = mGankInfoService!!.info.name
        }
        if (mTestInfoService != null) {
            btnModule3.text = mTestInfoService!!.info.name
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}