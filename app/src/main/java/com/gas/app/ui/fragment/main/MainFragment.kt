package com.gas.app.ui.fragment.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatDialogFragment
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
import com.gas.app.calendar.CalendarSelectDialogCustom
import com.gas.app.calendar.sdk.data.CalendarDayModel
import com.gas.app.calendar.sdk.data.CalendarTheme

import com.gas.app.learn.calendarfinal.calendar.CalendarSelectDialog as CalendarFinalDialog
import com.gas.app.calendar.sdk.dialog.CalendarSelectDialog as CalendarNewDialog

import com.gas.app.ui.fragment.main.di.DaggerMainComponent
import com.gas.app.ui.fragment.main.mvp.MainContract
import com.gas.app.ui.fragment.main.mvp.MainPresenter
import com.gas.app.utils.AppMoudleUtil
import com.gasview.metrialcalendar.CalendarDay
import com.gasview.metrialcalendar.MaterialCalendarView
import com.gasview.metrialcalendar.OnDateSelectedListener
import com.lib.commonsdk.constants.RouterHub
import com.lib.commonsdk.utils.Utils
import kotlinx.android.synthetic.main.fragment_main.*
import org.joda.time.LocalDate
import org.threeten.bp.format.DateTimeFormatter


/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/30/2019 15:27
 * ================================================
 */
class MainFragment : BaseFragment<MainPresenter?>(), MainContract.View {

    @JvmField
    @Autowired(name = RouterHub.ZHIHU_SERVICE_ZHIHUINFOSERVICE)
    var mZhihuInfoService: ZhihuInfoService? = null

    @JvmField
    @Autowired(name = RouterHub.GANK_SERVICE_GANKINFOSERVICE)
    var mGankInfoService: GankInfoService? = null

    @JvmField
    @Autowired(name = RouterHub.TEST_SERVICE_TESTINFOSERVICE)
    var mTestInfoService: TestInfoService? = null

    private var dialogFinal: CalendarFinalDialog? = null
    private var dialogFinal2: CalendarSelectDialogCustom? = null
    private var dialogFinal3: CalendarNewDialog? = null
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

            if(AppMoudleUtil.isChineseMainLand()) {
                Log.e("TAG","简体中文")
            }else if(AppMoudleUtil.isChineseLanguage()){
                Log.e("TAG","繁体中文")
            }else{
                Log.e("TAG","不是中文")
            }

            Log.e("TAG","isChineseMainLand=${AppMoudleUtil.isChineseMainLand()}")
            Log.e("TAG","isChineseTradition=${AppMoudleUtil.isChineseTradition()}")
//            cloudRecordUpgradeTip1.text="fsdfsdgsdfsfsgsdgsdgsdgshsdfgsdgsdhsdfgsdhgsdfsdfsd"

        }
        btnPlugin2.setOnClickListener {
            dialogFinal?.showSelect(mLocalDate)

        }
        btnPlugin3.setOnClickListener {
//            dialogFinal2?.showSelect()
            dialogFinal3?.showSelect(mLocalDate)
        }

        dialogFinal = CalendarFinalDialog(activity!!, com.gas.app.learn.calendarfinal.CalendarTheme.Gold,object :CalendarFinalDialog.OnDayClickCallBack {
            override fun onDayItemClick(date: com.gas.app.learn.calendarfinal.day.CalendarDayModel) {
                mLocalDate = date.localDate
            }
        })
        dialogFinal3 = CalendarNewDialog(activity!!, CalendarTheme.Gold,object :CalendarNewDialog.OnDayClickCallBack {
            override fun onDayItemClick(date: CalendarDayModel) {
                mLocalDate = date.localDate
            }

        })

        dialogFinal2 = CalendarSelectDialogCustom(activity!!)

        etx.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    cloudRecordUpgradeTip1.text=it
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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
        mZhihuInfoService?.let {
            btnModule2.text = it.info.name
        }
        mGankInfoService?.let {
            btnModule2.text = it.info.name
        }
        mTestInfoService?.let {
            btnModule3.text = it.info.name
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): MainFragment {
            return MainFragment()
        }

        val FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy")
    }

    class SimpleCalendarDialogFragment : AppCompatDialogFragment(), OnDateSelectedListener {
        private var textView: TextView? = null

        @NonNull
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val inflater: LayoutInflater = activity!!.layoutInflater

            //inflate custom layout and get views
            //pass null as parent view because will be in dialog layout
            val view: View = inflater.inflate(R.layout.activity_trans, null)
            textView = view.findViewById(R.id.textView)
            val widget: MaterialCalendarView = view.findViewById(R.id.calendarView)
            widget.setOnDateChangedListener(this)
            return AlertDialog.Builder(getActivity())
                    .setTitle("选择日期")
                    .setView(view)
                    .setPositiveButton(android.R.string.ok, null)
                    .create()
        }

        override fun onDateSelected(
                @NonNull widget: MaterialCalendarView,
                @NonNull date: CalendarDay,
                selected: Boolean) {
            textView!!.setText(FORMATTER.format(date.getDate()))
        }
    }
}