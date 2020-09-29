package com.gas.app.ui.fragment.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.gas.app.ui.fragment.main.di.DaggerMainComponent
import com.gas.app.ui.fragment.main.mvp.MainContract
import com.gas.app.ui.fragment.main.mvp.MainPresenter
import com.lib.commonsdk.constants.RouterHub
import com.lib.commonsdk.kotlin.extension.app.navigation
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.concurrent.TimeUnit


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

    private val timeDownNum1 = 10L
    private val timeDownNum2 = 10L
    private val holderCompositeSubscription = CompositeDisposable()
    private val compositeSubscription = CompositeDisposable().apply {
        add(holderCompositeSubscription)
    }

    override fun initData(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        loadModuleInfo()

        btnModule1.setOnClickListener {
            navigation(activity, RouterHub.ZHIHU_HOMEACTIVITY)
        }
        btnModule2.setOnClickListener {
            navigation(activity, RouterHub.GANK_HOMEACTIVITY)
        }
        btnModule3.setOnClickListener {
            navigation(activity, RouterHub.TEST_HOMEACTIVITY)
        }
        btnPlugin1.setOnClickListener {
            holderCompositeSubscription.clear()
            holderCompositeSubscription.add(Observable.intervalRange(0, timeDownNum1, 0, 1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ l ->
                        btnPlugin2.text = (l).toString()
                    }, { e -> e.printStackTrace() }, { }))
            holderCompositeSubscription.add(Observable.intervalRange(0, timeDownNum2, 0, 1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ l ->
                        btnPlugin3.text = (l).toString()
                    }, { e -> e.printStackTrace() }, { }))
//            val date = Date()
//            info(date.toLocalDateTime().format())
//            info(date.toLocalDateTime().hour)
        }
        btnPlugin2.setOnClickListener {
            tvReportTypeViewNum1.text = textFormatStyle(0)
//            holderCompositeSubscription.clear()
//            debug("点击")
        }
        btnPlugin3.setOnClickListener {
            tvReportTypeViewNum1.text = textFormatStyle(521)
//            compositeSubscription.clear()
//            initEventTitle()
        }

    }

    private fun textFormatStyle(num:Int): SpannableStringBuilder{
        return  if(num>0){
            val startStr = num.toString()
            val endFix = "条"
            val str = "$startStr$endFix"
            SpannableStringBuilder(str).apply {
                setSpan(ForegroundColorSpan(Color.parseColor("#2B2D2F")), 0, startStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(AbsoluteSizeSpan(30, true), 0, startStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(ForegroundColorSpan(Color.parseColor("#7A7E8E")), startStr.length, str.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(AbsoluteSizeSpan(9, true), startStr.length, str.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }else{
             SpannableStringBuilder("暂无消息")
        }
    }

    private fun initEventTitle() {
        llContent.removeAllViews()
        val list = listOf("第一条Preconditions.checkNotNull(message) setData(data: Any?) setData(data: Any?)",
                " 第二条 ArmsUtils.snackbarText(message) Preconditions.checkNotNull(intent)",
                "第三条 ArmsUtils.startActivity(intent) Preconditions.checkNotNull(intent)",
                "第四条 Preconditions.checkNotNull(message) setData(data: Any?) setData(data: Any?)")
        list.forEach { title ->
            val itemView = View.inflate(context, R.layout.layout_event_title, null)
            val tvTitle = itemView.findViewById<TextView>(R.id.tvReportMessage)
            tvTitle.text = title
            val drawable = resources.getDrawable(R.drawable.shape_report_message_ring_blue)
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            tvTitle.setCompoundDrawables(drawable, null, null, null)
            llContent.addView(itemView)
        }
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
    }


}