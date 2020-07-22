package com.gas.app.learn.customview


import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.gas.app.R
import com.lib.commonsdk.kotlin.extension.gone
import com.lib.commonsdk.utils.AppUtils


import java.lang.ref.WeakReference

class MyPageCloudAdTimeDownView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val SHOW_DURATION_DEFAULT = 10000.toLong()
        private const val SHOW_ACTION = 0x02 //显示
        private const val HIDE_ACTION = 0x03 //隐藏
        private const val SHOW_TIME_COUNT_ACTION = 0x04
    }

    //倒计时
    private val mCloseDuration = SHOW_DURATION_DEFAULT
    private var mCloseTimeDown = mCloseDuration / 1000
    private var mHandler: CameraVideoPlayPromptBannerHandler

    //view
    private val imgCloudRecordAdAlert: ImageView
    private val cloudRecordAdTitle: TextView
    private val timeDownView: CloudRecordTimeDownView
    private val tvTimeDown: TextView
    private val mRoot: View
    private val alertShaker: AlertWidgetShaker

    var hideCallback: (() -> Unit)? = null
    var showCallback: (() -> Unit)? = null

    private fun startAlertShaker() {
        alertShaker.shake()
    }

    private fun cancelAlertShaker() {
        alertShaker.cancel()
    }

    fun show(tip: String, timeDown: Long) {
        if (mRoot.visibility == View.VISIBLE)
            return
        cloudRecordAdTitle.text = tip
        mCloseTimeDown = mCloseDuration / 1000
        tvTimeDown.text = AppUtils.getString(R.string.v4_camera_time_down, mCloseTimeDown)
        timeDownView.setTimeDownStamp(timeDown)
        sendMessage(SHOW_ACTION)
    }

    fun hide() {
        sendMessage(HIDE_ACTION)
    }

    private fun updateTimeView() {
        if (mRoot.visibility != View.GONE && mCloseTimeDown > 0) {
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

    override fun onDetachedFromWindow() {
        cancelAlertShaker()
        timeDownView.stopDownTime()
        mHandler.removeCallbacksAndMessages(null)
        super.onDetachedFromWindow()
    }

    private fun showView() {
        mRoot.clearAnimation()
        sendMessage(HIDE_ACTION, mCloseDuration)
        sendMessage(SHOW_TIME_COUNT_ACTION, 1000)
        mRoot.visibility = View.VISIBLE
        val a = AlphaAnimation(0.2F, 1F).apply {
            duration = 300
        }
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                //timeDown
                timeDownView.startTimeDown()
                startAlertShaker()
                showCallback?.invoke()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        mRoot.startAnimation(a)
    }

    fun hideView() {
        if (mRoot.visibility == View.GONE) {
            return
        }
        mRoot.clearAnimation()
        val a = AlphaAnimation(1.0f, 0.2f).apply {
            duration = 300
        }
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                mHandler.removeCallbacksAndMessages(null)
                mRoot.gone()
                cancelAlertShaker()
                timeDownView.stopDownTime()
                hideCallback?.invoke()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        mRoot.startAnimation(a)
    }

    init {
        val view = View.inflate(context, R.layout.fragment_my_item_cloud_record_time_down, this)
        imgCloudRecordAdAlert = view.findViewById(R.id.img_cloud_record_ad_alert)
        cloudRecordAdTitle = view.findViewById(R.id.cloud_record_ad_title)
        timeDownView = view.findViewById(R.id.cloud_record_time_down)
        tvTimeDown = view.findViewById(R.id.banner_time_down)
        mHandler = CameraVideoPlayPromptBannerHandler(this)
        alertShaker = AlertWidgetShaker.with(imgCloudRecordAdAlert).apply {
            repeatMaxTimes = -1
            repeat = true
            startDelay = 100L
            repeatInterval = 1000L
        }
        mRoot = this
    }

    private class CameraVideoPlayPromptBannerHandler internal constructor(view: MyPageCloudAdTimeDownView) : Handler() {
        private val mRef: WeakReference<MyPageCloudAdTimeDownView> = WeakReference(view)
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