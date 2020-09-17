package com.gas.app.ui.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.lib.commonsdk.kotlin.extension.app.debug
import com.lib.commonsdk.kotlin.extension.app.info
import com.lib.commonsdk.kotlin.extension.app.navigation
import com.lib.commonsdk.kotlin.extension.time.format
import com.lib.commonsdk.kotlin.extension.time.toLocalDateTime
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*


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
            val date = Date()
            info(date.toLocalDateTime().format())
            info(date.toLocalDateTime().hour)
        }
        btnPlugin2.setOnClickListener {
            debug("点击")
        }
        btnPlugin3.setOnClickListener {

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