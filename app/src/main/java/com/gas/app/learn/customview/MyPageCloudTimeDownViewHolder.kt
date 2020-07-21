package com.gas.app.learn.customview

import android.os.Handler
import android.os.Message
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import com.gas.app.R
import com.lib.commonsdk.kotlin.extension.gone
import com.lib.commonsdk.utils.AppUtils
import java.lang.ref.WeakReference

class MyPageCloudTimeDownViewHolder(val view: View) {

    companion object {
        private const val SHOW_DURATION_DEFAULT = 10 * 1000.toLong()
        private const val SHOW_ACTION = 0x02 //显示
        private const val HIDE_ACTION = 0x03 //隐藏
        private const val SHOW_TIME_COUNT_ACTION = 0x04
    }

    //倒计时
    private val mCloseDuration = SHOW_DURATION_DEFAULT
    private var mCloseTimeDown = mCloseDuration / 1000
    private var mHandler: CameraVideoPlayPromptBannerHandler

    //view
    private var imgCloudRecordAdAlert: ImageView = view.findViewById(R.id.img_cloud_record_ad_alert)
    private var cloudRecordAdTitle: TextView = view.findViewById(R.id.cloud_record_ad_title)
    private var timeDownView: CloudRecordTimeDownView = view.findViewById(R.id.cloud_record_time_down)
    private var tvTimeDown: TextView = view.findViewById(R.id.banner_time_down)
    private var viewContainer: View = view.findViewById(R.id.time_down_container)

    private val alertShaker: AlertWidgetShaker by lazy {
        AlertWidgetShaker.with(imgCloudRecordAdAlert).apply {
            repeatMaxTimes = -1
            repeat = true
            startDelay = 100L
            repeatInterval = 1000L
        }
    }

    private fun startAlertShaker() {
        // 判断今天是否点击过签到按钮，点击过就不再显示动画了
        alertShaker.shake()
    }

    private fun cancelAlertShaker() {
        alertShaker.cancel()
    }

    var onClickCallback: (() -> Unit)? = null

    fun show(tip: String, timeDown: Long) {
        cloudRecordAdTitle.text = tip
        timeDownView.setTimeDownStamp(timeDown)
        sendMessage(SHOW_ACTION)
    }

    fun hide(){
        sendMessage(HIDE_ACTION)
    }

    private fun updateTimeView() {
        if (viewContainer.visibility != View.GONE && mCloseTimeDown > 0) {
            mCloseTimeDown--
            tvTimeDown.text = AppUtils.getString(R.string.v4_camera_time_down, mCloseTimeDown)
            sendMessage(SHOW_TIME_COUNT_ACTION, 1000)
        } else {
            removeMessage(SHOW_TIME_COUNT_ACTION)
        }
    }

    private fun removeMessage(what: Int) {
        mHandler.removeMessages(what)
    }

    private fun sendMessage(what: Int, delayMillis: Long = 0) {
        mHandler.removeMessages(what)
        if (delayMillis == 0L) {
            mHandler.sendEmptyMessage(what)
        } else {
            mHandler.sendEmptyMessageDelayed(what, delayMillis)
        }
    }

    private fun showView() {
        viewContainer.clearAnimation()
        sendMessage(HIDE_ACTION, mCloseDuration)
        sendMessage(SHOW_TIME_COUNT_ACTION, 1000)
        mCloseTimeDown = mCloseDuration / 1000
        viewContainer.visibility = View.VISIBLE
        val a =  AlphaAnimation(0.2F, 1F).apply {
            duration=300
        }
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                //timeDown
                timeDownView.startTimeDown()
                startAlertShaker()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        viewContainer.startAnimation(a)
    }

    fun hideView() {
        if (viewContainer.visibility == View.GONE) {
            return
        }
        viewContainer.clearAnimation()
        val a =  AlphaAnimation(1.0f, 0.2f).apply {
            duration=300
        }
//        val a = AnimationUtils.loadAnimation(AppUtils.getApp(), R.anim.cloud_record_banner_hidded)
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                mHandler.removeCallbacksAndMessages(null)
                viewContainer.gone()
                timeDownView.stopDownTime()
                cancelAlertShaker()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        viewContainer.startAnimation(a)

    }

    init {
        mHandler = CameraVideoPlayPromptBannerHandler(this)
        viewContainer.gone()
        viewContainer.setOnClickListener {
            onClickCallback?.invoke()
        }
    }

    private class CameraVideoPlayPromptBannerHandler internal constructor(view: MyPageCloudTimeDownViewHolder) : Handler() {
        private val mRef: WeakReference<MyPageCloudTimeDownViewHolder> = WeakReference(view)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                SHOW_TIME_COUNT_ACTION -> {
                    removeMessages(SHOW_TIME_COUNT_ACTION)
                    if (mRef.get() != null) {
                        mRef.get()!!.updateTimeView()
                    }
                }
                SHOW_ACTION -> {
                    removeMessages(SHOW_ACTION)
                    if (mRef.get() != null) {
                        mRef.get()!!.showView()
                    }
                }
                HIDE_ACTION -> {
                    removeMessages(SHOW_TIME_COUNT_ACTION)
                    removeMessages(HIDE_ACTION)
                    if (mRef.get() != null) {
                        mRef.get()!!.hideView()
                    }
                }
            }
        }
    }

}