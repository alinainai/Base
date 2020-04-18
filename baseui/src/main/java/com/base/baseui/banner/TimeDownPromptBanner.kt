package com.base.baseui.banner

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import androidx.annotation.IntRange
import com.base.baseui.R
import com.base.baseui.banner.TimeDownPromptBanner.BannerConfig.AnimStyle
import java.lang.ref.WeakReference

/**
 * ================================================
 * desc: 倒计时pop布局
 *
 *
 * created by author ljx
 * Date  2020-03-04
 * email 569932357@qq.com
 *
 *
 * ================================================
 */
open class TimeDownPromptBanner : FrameLayout {
    /**
     * 关闭剩余倒计时时长 单位s
     */
    private var mCloseDuration = DEFAULT_SHOW_DURATION
    /**
     * 关闭剩余倒计时计时 单位s
     */
    private var mCloseTimeDown: Long = 0
    /**
     * 辅助完成倒计时的 handler 对象
     */
    private var mHandler: TimeDownHandler? = null
    /**
     * banner 的图标
     */
    private var mBannerIcon: ImageView? = null
    /**
     * 主标题
     */
    private var mBannerTitle: TextView? = null
    /**
     * 副标题
     */
    private var mBannerSubTitle: TextView? = null
    /**
     * 倒计时UI
     */
    private var mBannerTimeDown: TextView? = null
    /**
     * 关闭按钮
     */
    private var mBannerClose: ImageView? = null
    /**
     * 根布局背景
     */
    private var mBannerBg: View? = null
    /**
     * banner的显示配置信息
     *
     *
     * [BannerConfig]
     */
    private var mConfig: BannerConfig? = null
    /**
     * 动画方式，默认自上而下出现，自下而上隐藏
     */
    @AnimStyle
    private var mAnimStyle = BannerConfig.ANIM_TOP

    constructor(context: Context?) : super(context!!) {
        visibility = View.GONE
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        visibility = View.GONE
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        visibility = View.GONE
    }

    private fun initLayout(@LayoutRes layout: Int) {
        if (this.childCount > 0) {
            removeAllViews()
        }
        val view = View.inflate(context, layout, this)
        mBannerBg = view.findViewById(R.id.banner_bg)
        mBannerIcon = view.findViewById(R.id.banner_icon)
        mBannerTitle = view.findViewById(R.id.banner_main_title)
        mBannerSubTitle = view.findViewById(R.id.banner_sub_title)
        mBannerTimeDown = view.findViewById(R.id.banner_time_down)
        mBannerClose = view.findViewById(R.id.banner_close)
        mHandler = TimeDownHandler(this)
    }

    private fun setConfig(config: BannerConfig) {
        mAnimStyle = config.animStyle
        // 设置背景资源
        if (config.bgResId != 0) {
            if (mBannerBg != null) {
                mBannerBg!!.setBackgroundResource(config.bgResId)
            }
        } else {
            if (mBannerBg != null) {
                mBannerBg!!.setBackgroundResource(defaultBgRes())
            }
        }
        //设置图标资源
        if (config.iconResId != 0) {
            if (mBannerIcon != null) {
                mBannerIcon!!.setImageResource(config.iconResId)
            }
        } else {
            if (mBannerIcon != null) {
                mBannerIcon!!.setImageResource(defaultIconRes())
            }
        }
        //设置主标题
        if (!TextUtils.isEmpty(config.title)) {
            if (mBannerTitle != null) {
                mBannerTitle!!.text = config.title
            }
        } else {
            if (mBannerTitle != null) {
                mBannerTitle!!.text = ""
            }
        }
        //设置主标题颜色
        if (config.titleColor != 0) {
            if (mBannerTitle != null) {
                mBannerTitle!!.setTextColor(resources.getColor(config.titleColor))
            }
        } else {
            if (mBannerTitle != null) {
                mBannerTitle!!.setTextColor(resources.getColor(defaultTextColor()))
            }
        }
        //设置副标题
        if (!TextUtils.isEmpty(config.subTitle)) {
            if (mBannerSubTitle != null) {
                mBannerSubTitle!!.visibility = View.VISIBLE
                mBannerSubTitle!!.text = config.subTitle
            }
        } else {
            if (mBannerSubTitle != null) {
                mBannerSubTitle!!.visibility = View.GONE
            }
        }
        //设置副标题颜色
        if (config.subTitleColor != 0) {
            if (mBannerSubTitle != null) {
                mBannerSubTitle!!.setTextColor(resources.getColor(config.subTitleColor))
            }
        } else {
            if (mBannerSubTitle != null) {
                mBannerSubTitle!!.setTextColor(resources.getColor(defaultTextColor()))
            }
        }
        //设置关闭图标资源
        if (config.closeResId != 0) {
            if (mBannerClose != null) {
                mBannerClose!!.setImageResource(config.closeResId)
            }
        } else {
            if (mBannerClose != null) {
                mBannerClose!!.setImageResource(defaultCloseRes())
            }
        }
        //设置副标题点击事件
        if (mBannerSubTitle != null && config.onSubTitleClick != null) {
            mBannerSubTitle!!.isClickable = true
            mBannerSubTitle!!.setOnClickListener { v: View? ->
                config.onSubTitleClick!!.run()
                sendMessage(HIDE_ACTION)
            }
        } else {
            if (mBannerSubTitle != null) {
                mBannerSubTitle!!.isClickable = false
            }
        }
        //设置关闭按钮点击事件
        if (mBannerClose != null && config.onCloseClick != null) {
            mBannerClose!!.isClickable = true
            mBannerClose!!.setOnClickListener { v: View? ->
                config.onCloseClick!!.run()
                sendMessage(HIDE_ACTION)
            }
        } else {
            if (mBannerClose != null) {
                mBannerClose!!.isClickable = false
            }
        }
        //设置当前条目点击事件
        setOnClickListener { v: View? ->
            if (config.viewOnClick != null) {
                config.viewOnClick!!.run()
            }
            sendMessage(HIDE_ACTION)
        }
        if (config.showDuration == DEFAULT_SHOW_DURATION) {
            mCloseDuration = config.showDuration
            if (mBannerTimeDown != null) {
                mBannerTimeDown!!.visibility = View.GONE
            }
        } else {
            mCloseDuration = config.showDuration
            mCloseTimeDown = mCloseDuration
            if (mBannerTimeDown != null) {
                mBannerTimeDown!!.visibility = View.VISIBLE
            }
        }
    }

    fun dismiss() {
        sendMessage(HIDE_ACTION)
    }

    /**
     * 立即显示且不带关闭倒计时
     */
    fun show(config: BannerConfig?) {
        showAfterDuration(config, 0)
    }

    /**
     * 延迟 duration 毫秒后展示且不带倒计时关闭
     *
     * @param duration 延迟展示时长 单位：s
     */
    fun showAfterDuration(config: BannerConfig?, duration: Long) {
        mConfig = config
        sendMessage(SHOW_ACTION, duration * 1000)
    }

    @SuppressLint("DefaultLocale")
    private fun updateTimeView() {
        if (this.visibility != View.GONE && mCloseTimeDown > 0) {
            mCloseTimeDown--
            if (mBannerTimeDown != null) {
                mBannerTimeDown!!.text = String.format(TIME_DOWN_WRAP_UNIT, mCloseTimeDown)
                sendMessage(SHOW_TIME_COUNT_ACTION, 1000)
            } else {
                removeMessage(SHOW_TIME_COUNT_ACTION)
            }
        } else {
            removeMessage(SHOW_TIME_COUNT_ACTION)
        }
    }

    /**
     * 显示弹窗
     */
    @SuppressLint("DefaultLocale")
    private fun showView() {
        removeMessage(HIDE_ACTION)
        removeMessage(SHOW_TIME_COUNT_ACTION)
        if (this.visibility != View.VISIBLE) {
            initLayout(mConfig!!.layoutId)
            setConfig(mConfig!!)
            clearAnimation()
            if (mCloseDuration != DEFAULT_SHOW_DURATION) {
                mCloseTimeDown = mCloseDuration
                if (mBannerTimeDown != null) {
                    mBannerTimeDown!!.text = String.format(TIME_DOWN_WRAP_UNIT, mCloseTimeDown)
                    sendMessage(SHOW_TIME_COUNT_ACTION, 1000)
                }
                sendMessage(HIDE_ACTION, mCloseDuration * 1000)
            }
            this.visibility = View.VISIBLE
            this.tag = mConfig!!.tag
            val a = AnimationUtils.loadAnimation(this.context, showAnimRes())
            startAnimation(a)
        } else {
            if (this.tag != mConfig!!.tag) {
                clearAnimation()
                val a = AnimationUtils.loadAnimation(this.context, hideAnimRes())
                a.setAnimationListener(object : AnimationEndListener() {
                    override fun onAnimationEnd(animation: Animation) {
                        visibility = View.GONE
                        sendMessage(SHOW_ACTION)
                    }
                })
                startAnimation(a)
            }
        }
    }

    open fun showAnimRes() = when (mAnimStyle) {
            BannerConfig.ANIM_LEFT -> R.anim.public_slide_in_from_left
            BannerConfig.ANIM_RIGHT -> R.anim.public_slide_in_from_right
            BannerConfig.ANIM_BOTTOM -> R.anim.public_slide_in_from_bottom
            BannerConfig.ANIM_TOP -> R.anim.public_slide_in_from_top
            else -> R.anim.public_slide_in_from_top
        }

    open fun hideAnimRes() = when (mAnimStyle) {
            BannerConfig.ANIM_LEFT -> R.anim.public_slide_out_to_left
            BannerConfig.ANIM_RIGHT -> R.anim.public_slide_out_to_right
            BannerConfig.ANIM_BOTTOM -> R.anim.public_slide_out_to_bottom
            BannerConfig.ANIM_TOP -> R.anim.public_slide_out_to_top
            else -> R.anim.public_slide_out_to_top
        }

    @ColorRes
    open fun defaultTextColor() = R.color.public_white

    @DrawableRes
    open fun defaultBgRes() = R.drawable.public_banner_time_down_bg

    @DrawableRes
    open fun defaultCloseRes() = R.drawable.public_banner_time_down_close

    @DrawableRes
    open fun defaultIconRes() = R.mipmap.drawable_banner_time_down_icon

    /**
     * 隐藏弹窗
     */
    private fun hideView() {
        if (this.visibility == View.GONE) {
            return
        }
        clearAnimation()
        val a = AnimationUtils.loadAnimation(this.context, hideAnimRes())
        a.setAnimationListener(object : AnimationEndListener() {
            override fun onAnimationEnd(animation: Animation) {
                visibility = View.GONE
            }
        })
        startAnimation(a)
    }

    private fun removeMessage(what: Int) {
        if (mHandler != null) {
            mHandler!!.removeMessages(what)
        }
    }

    private fun sendMessage(what: Int, delayMillis: Long = 0) {
        if (mHandler == null) {
            mHandler = TimeDownHandler(this)
        }
        removeMessage(what)
        if (delayMillis == 0L) {
            mHandler!!.sendEmptyMessage(what)
        } else {
            mHandler!!.sendEmptyMessageDelayed(what, delayMillis)
        }
    }

    private fun removeAllMessage() {
        if (null != mHandler) {
            mHandler!!.removeCallbacksAndMessages(null)
            mHandler = null
        }
    }

    override fun onDetachedFromWindow() {
        removeAllMessage()
        super.onDetachedFromWindow()
    }

    private class TimeDownHandler internal constructor(view: TimeDownPromptBanner?) : Handler() {
        private val mRef: WeakReference<TimeDownPromptBanner?> = WeakReference(view)
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

    /**
     * 配置信息
     */
    class BannerConfig(@field:LayoutRes @get:LayoutRes
                       @param:LayoutRes val layoutId: Int) {
        // 自定义一个注解MyState
        @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
        @IntDef(ANIM_TOP, ANIM_BOTTOM, ANIM_LEFT, ANIM_RIGHT)
        annotation class AnimStyle

        /**
         * 整个条目背景资源Id
         */
        @DrawableRes
        var bgResId = 0
            private set
        /**
         * Icon图片的资源Id
         */
        @DrawableRes
        var iconResId = 0
            private set
        /**
         * titleColor
         */
        @ColorRes
        var titleColor = 0
            private set
        /**
         * title
         */
        var title: String? = null
            private set
        /**
         * subTitleColor
         */
        @ColorRes
        var subTitleColor = 0
            private set
        /**
         * title
         */
        var subTitle = ""
            private set
        /**
         * close图片的资源Id
         */
        @DrawableRes
        var closeResId = 0
            private set
        /**
         * 条目点击事件
         */
        var viewOnClick: Runnable? = null
            private set
        /**
         * 副标题点击事件
         */
        var onSubTitleClick: Runnable? = null
            private set
        /**
         * 关闭按钮点击事件
         */
        var onCloseClick: Runnable? = null
            private set
        /**
         * 展示时长
         */
        var showDuration = DEFAULT_SHOW_DURATION
            private set
        /**
         * 一个界面多次展示不同消息的TAG，防止多次显示
         */
        var tag = DEFAULT_BANNER_TAG
            private set
        @get:AnimStyle
        @AnimStyle
        var animStyle = ANIM_TOP
            private set

        fun setBgResId(@DrawableRes bgResId: Int): BannerConfig {
            this.bgResId = bgResId
            return this
        }

        fun setIconResId(iconResId: Int): BannerConfig {
            this.iconResId = iconResId
            return this
        }

        fun setTitleColor(@ColorRes titleColorId: Int): BannerConfig {
            titleColor = titleColorId
            return this
        }

        fun setTitle(title: String?): BannerConfig {
            this.title = title
            return this
        }

        fun setSubTitleColor(@ColorRes titleColorId: Int): BannerConfig {
            subTitleColor = titleColorId
            return this
        }

        fun setSubTitle(title: String): BannerConfig {
            subTitle = title
            return this
        }

        fun setCloseResId(@DrawableRes resId: Int): BannerConfig {
            closeResId = resId
            return this
        }

        fun setSubTitleOnClick(actionOnClick: Runnable?): BannerConfig {
            onSubTitleClick = actionOnClick
            return this
        }

        fun setOnCloseClick(onCloseClick: Runnable?): BannerConfig {
            this.onCloseClick = onCloseClick
            return this
        }

        fun setViewOnClick(viewOnClick: Runnable?): BannerConfig {
            this.viewOnClick = viewOnClick
            return this
        }

        fun setShowDuration(@IntRange(from = 0, to = 20) showDuration: Int): BannerConfig {
            this.showDuration = showDuration.toLong()
            return this
        }

        fun setTag(tag: String): BannerConfig {
            this.tag = tag
            return this
        }

        fun setAnimStyle(@AnimStyle style: Int): BannerConfig {
            animStyle = style
            return this
        }

        companion object {
            const val ANIM_TOP = 1
            const val ANIM_BOTTOM = 2
            const val ANIM_LEFT = 3
            const val ANIM_RIGHT = 4
        }

    }

    internal abstract inner class AnimationEndListener : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationRepeat(animation: Animation) {}
    }

    companion object {
        /**
         * -1不显示倒计时且不再倒计时后消失
         */
        const val DEFAULT_SHOW_DURATION: Long = -1
        /**
         * 倒计时单位
         */
        const val TIME_DOWN_WRAP_UNIT = "%ds"
        const val DEFAULT_BANNER_TAG = "banner"
        /**
         * banner显示消息
         */
        const val SHOW_ACTION = 0x02
        /**
         * banner隐藏消息
         */
        const val HIDE_ACTION = 0x03
        /**
         * 更新倒计时 UI 消息
         */
        const val SHOW_TIME_COUNT_ACTION = 0x04
    }
}