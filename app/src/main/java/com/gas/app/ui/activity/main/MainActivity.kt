package com.gas.app.ui.activity.main


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.gas.app.R
import com.gas.app.ui.activity.main.di.DaggerMainComponent
import com.gas.app.ui.activity.main.di.MainModule
import com.gas.app.ui.activity.main.mvp.MainContract
import com.gas.app.ui.activity.main.mvp.MainPresenter
import com.gas.app.utils.AppMoudleUtil.startLottieAnimation
import com.gas.app.utils.AppMoudleUtil.stopLottieAnimation
import com.lib.commonsdk.adapter.AdapterViewPager
import com.lib.commonsdk.constants.RouterHub
import com.lib.commonsdk.statusbar.StatusBarManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject


/**
 * ================================================
 * desc:首页
 *
 * created by author ljx
 * date  2020/7/22
 * email 569932357@qq.com
 *
 * ================================================
 */
@Route(path = RouterHub.APP_MAINACTIVITY)
class MainActivity : BaseActivity<MainPresenter>(), MainContract.View, View.OnClickListener {


    @Inject
    lateinit var mAdapter: AdapterViewPager




    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(MainModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        StatusBarManager.fullTransStatusBar(this)
        StatusBarManager.setStatusBarLightMode(this)
        return R.layout.activity_main

    }


    override fun initData(savedInstanceState: Bundle?) {
        container.adapter = mAdapter
        container.offscreenPageLimit = mAdapter.count - 1
        //防止屏幕切换等操作造成按钮错位
        if (null != savedInstanceState) {
            mSavedTabIndex = savedInstanceState.getInt(CURRENTTABINDEX, -1)
            if (mSavedTabIndex != -1) resetButton(mSavedTabIndex) else resetButton(0)
        } else {
            resetButton(0)
        }
        mainBtn1.setOnClickListener(this)
        mainBtn2.setOnClickListener(this)
        mainBtn3.setOnClickListener(this)
        mainBtn4.setOnClickListener(this)
    }

    override fun getWrapContext(): Context {
        return this
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }





    private val CURRENTTABINDEX = "current_tab_index"

    // 当前fragment的index
    var mCurrentTabIndex = -1
    var mSavedTabIndex = -1


    override fun onSaveInstanceState(outState: Bundle) {
        //防止屏幕切换等操作造成按钮错位
        outState.putInt(CURRENTTABINDEX, mCurrentTabIndex)
        super.onSaveInstanceState(outState)
    }


    private fun resetButton(index: Int) {
        if (index == mCurrentTabIndex) return
        when (index) {
            0 -> {
                mainBtn1!!.isSelected = true
                mainBtn2!!.isSelected = false
                mainBtn3!!.isSelected = false
                mainBtn4!!.isSelected = false
                startLottieAnimation(mainIcon1)
                stopLottieAnimation(mainIcon2)
                stopLottieAnimation(mainIcon3)
                stopLottieAnimation(mainIcon4)
            }
            1 -> {
                mainBtn1!!.isSelected = false
                mainBtn2!!.isSelected = true
                mainBtn3!!.isSelected = false
                mainBtn4!!.isSelected = false
                stopLottieAnimation(mainIcon1)
                startLottieAnimation(mainIcon2)
                stopLottieAnimation(mainIcon3)
                stopLottieAnimation(mainIcon4)
            }
            2 -> {
                mainBtn1!!.isSelected = false
                mainBtn2!!.isSelected = false
                mainBtn3!!.isSelected = true
                mainBtn4!!.isSelected = false
                stopLottieAnimation(mainIcon1)
                stopLottieAnimation(mainIcon2)
                startLottieAnimation(mainIcon3)
                stopLottieAnimation(mainIcon4)
            }
            3 -> {
                mainBtn1!!.isSelected = false
                mainBtn2!!.isSelected = false
                mainBtn3!!.isSelected = false
                mainBtn4!!.isSelected = true
                stopLottieAnimation(mainIcon1)
                stopLottieAnimation(mainIcon2)
                stopLottieAnimation(mainIcon3)
                startLottieAnimation(mainIcon4)
            }
        }
        container!!.setCurrentItem(index, false)
        mCurrentTabIndex = index
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click() //调用双击退出函数
        }
        return false
    }

    /**
     * 双击退出函数
     */
    private var isExit = false

    private fun exitBy2Click() {
        val tExit: Timer
        if (!isExit) {
            isExit = true // 准备退出
            ArmsUtils.snackbarText("再按一次退出程序")
            tExit = Timer()
            tExit.schedule(object : TimerTask() {
                override fun run() {
                    isExit = false // 取消退出
                }
            }, 2000) //如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            ArmsUtils.exitApp()
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.mainBtn1 -> resetButton(0)
                R.id.mainBtn2 -> resetButton(1)
                R.id.mainBtn3 -> resetButton(2)
                R.id.mainBtn4 -> resetButton(3)
            }
        }
    }


}
