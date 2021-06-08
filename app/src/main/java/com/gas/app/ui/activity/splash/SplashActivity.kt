package com.gas.app.ui.activity.splash


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.gas.app.R
import com.gas.app.ui.activity.main.MainActivity
import com.gas.app.ui.activity.splash.di.DaggerSplashComponent
import com.gas.app.ui.activity.splash.di.SplashModule
import com.gas.app.ui.activity.splash.mvp.SplashContract
import com.gas.app.ui.activity.splash.mvp.SplashPresenter
import com.gas.app.view.MyVideoView
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : BaseActivity<SplashPresenter>(), SplashContract.View, View.OnClickListener {

    private var mVideoView: MyVideoView? = null

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSplashComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .splashModule(SplashModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        return R.layout.activity_splash
    }

    override fun initData(savedInstanceState: Bundle?) {
        tvSkip.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.tvSkip -> {
                    tvSkip.isEnabled = false
                    mPresenter?.forceToMainPage()
                }
                else -> {
                }
            }
        }
    }

    //播放背景动画
    override fun playVideo(videoFilePath: String) {
        mVideoView = MyVideoView(this.applicationContext)
        mVideoView?.apply {
            layoutParams = RelativeLayout.LayoutParams(-1, -1)
            rlVideo.addView(mVideoView)
            setVideoPath(videoFilePath)
            setOnPreparedListener { mediaPlayer: MediaPlayer ->
                mediaPlayer.isLooping = true
                mediaPlayer.start()
            }
            setOnErrorListener { _: MediaPlayer?, _: Int, _: Int -> true }
        }
    }

    override fun toMainPage() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        killMyself()
    }

    override fun tvTimeDown(time: Long) {
        tvSkip.text =getString(R.string.app_str_skip_with_time,time.toString())
    }

    override fun versionCode(code: String?) {
        tv_version.text = getString(R.string.app_current_version,code?:"")
    }

    override fun onDestroy() {
        mVideoView?.let {
            it.suspend()
            it.setOnErrorListener(null)
            it.setOnPreparedListener(null)
            it.setOnCompletionListener(null)
            rlVideo.removeAllViews()
        }
        super.onDestroy()
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

}
