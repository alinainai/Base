package com.lib.commonsdk.kotlin.extension.app

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.lib.commonsdk.kotlin.extension.file.*

/**
 * ================================================
 * [visible]                    : 设置 View 的 visibility 为 visible
 * [invisible]                  : 设置 View 的 visibility 为 invisible
 * [gone]                       : 设置 View 的 visibility 为 gone
 * [visibleOrGone]              : 根据 flag设置 View 的 visibility true:visible false:gone
 * [visibleOrInvisible]         : 根据 flag设置 View 的 visibility true:visible false:invisible
 * [toggleVisibility]           : 在 visible 和 invisible 之间切换
 * [showSmoothly]               : 渐渐的显示出这个View,而不是突然出现
 * [hideSmoothly]               : 移动文件或目录
 * ================================================
 */
fun <T : View> T.visible() = apply {
    visibility = View.VISIBLE
}

fun <T : View> T.visible(show: Boolean) = apply {
    visibility = if (show)
        View.VISIBLE
    else
        View.GONE
}

fun <T : View> T.invisible() = apply {
    visibility = View.INVISIBLE
}

fun <T : View> T.gone() = apply {
    visibility = View.GONE
}

fun <T : View> T.visibleOrGone(flag: Boolean) = apply {
    visibility = if (flag) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun <T : View> T.visibleOrInvisible(flag: Boolean) {
    visibility = if (flag) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

fun <T : View> T.toggleVisibility() {
    if (visibility == View.VISIBLE) {
        invisible()
    } else {
        visible()
    }
}

fun <T : View> T.showSmoothly() = apply {
    val alphaAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 1.0F)
    alphaAnimator.addUpdateListener {
        this.alpha = it.animatedValue as Float
    }
    alphaAnimator.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
            this@apply.visible()
        }
    })
    alphaAnimator.duration = 300L
    alphaAnimator.start()
}

/**
 * 渐渐的隐藏这个View,而不是突然消失
 */
fun <T : View> T.hideSmoothly() = apply {
    val alphaAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 0.0F)
    alphaAnimator.addUpdateListener {
        this.alpha = it.animatedValue as Float
    }

    alphaAnimator.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            this@apply.gone()
        }
    })
    alphaAnimator.duration = 300L
    alphaAnimator.start()
}

/**
 * 以Pixel为单位设置TextView的字号。
 */
var <V : TextView> V.textSizePx: Float
    get() = throw UnsupportedOperationException("cannot get textSizePx for TextView")
    set(v) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, v)
    }

/**
 * View 被 attached 到 window 的时候做一些事情
 */
fun <V : View> V.doWhenAttachedToWindow(detached: ((View) -> Unit)? = null, attached: (View) -> Unit) {
    if (isAttachedToWindow) {
        attached(this)
    }
    if (!isAttachedToWindow || detached != null) {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(view: View) {
                attached(view)
            }

            override fun onViewDetachedFromWindow(view: View) {
                detached?.let { it(view) }
            }
        })
    }
}

/**
 * 给一个Drawable设置大小, 用这个方法设置之后Drawable是正方形的。
 */
var <T : Drawable> T.boundSize: Int
    get() = bounds.width()
    set(v) {
        setBounds(0, 0, v, v)
    }

var <T : Drawable> T.tint: Int
    get() = throw java.lang.UnsupportedOperationException("cannot get tint for Drawable")
    set(v) = DrawableCompat.setTint(this, v)


fun TextView.topIcon(@DrawableRes res: Int = 0, ctx: Context = app) = apply {
    if (res != 0) {
        ContextCompat.getDrawable(ctx, res)?.let { drawable ->
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            setCompoundDrawables(null, drawable, null, null)
        }
    } else {
        setCompoundDrawables(null, null, null, null)
    }
}

fun TextView.startIcon(@DrawableRes res: Int = 0, ctx: Context = app) = apply {
    if (res != 0) {
        ContextCompat.getDrawable(ctx, res)?.let { drawable ->
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            setCompoundDrawables(drawable, null, null, null)
        }
    } else {
        setCompoundDrawables(null, null, null, null)
    }
}

fun TextView.endIcon(@DrawableRes res: Int = 0, ctx: Context = app) = apply {
    if (res != 0) {
        ContextCompat.getDrawable(ctx, res)?.let { drawable ->
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            setCompoundDrawables(null, null, drawable, null)
        }
    } else {
        setCompoundDrawables(null, null, null, null)
    }
}

fun TextView.bottomIcon(@DrawableRes res: Int = 0, ctx: Context = app) = apply {
    if (res != 0) {
        ContextCompat.getDrawable(ctx, res)?.let { drawable ->
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            setCompoundDrawables(null, null, null, drawable)
        }
    } else {
        setCompoundDrawables(null, null, null, null)
    }
}

/**
 * 渐渐的隐藏这个View,而不是突然消失
 */
fun <T : View> T.hideQuickly() = apply {
    val alphaAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 1.0F, 0.0F)
    alphaAnimator.addUpdateListener {
        this.alpha = it.animatedValue as Float
    }

    alphaAnimator.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            this@apply.gone()
        }
    })
    alphaAnimator.duration = 300L
    alphaAnimator.start()
}

