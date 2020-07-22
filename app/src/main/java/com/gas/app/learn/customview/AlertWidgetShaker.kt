package com.gas.app.learn.customview

import android.animation.Animator
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View

/**
 * 一个动画控制器，
 * 用于实现一个view摇晃放大的动画效果
 */
class AlertWidgetShaker private constructor(private val widget: View) {

    private var objectAnimator: ObjectAnimator? = null

    // 第一次启动延迟时间，单位 ms
    var startDelay = 500L

    // 两次动画的间隔时间，单位 ms
    var repeatInterval = 500L

    // 每次动画的持续时间，单位 ms
    var duration = 800L

    // 是否循环执行动画
    var repeat = true

    // 重复执行动画的次数上限，负数为无限制
    var repeatMaxTimes = -1

    // 当前已经执行动画的次数
    private var currentTimes = 0

    private var cancel = true

    fun shake() {
        cancel()
        widget.post {
            widget.pivotX = widget.measuredWidth / 2f
            widget.pivotY = widget.measuredHeight / 2f
            val shakeDegrees = 10f
            val scaleXValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                    Keyframe.ofFloat(0f, 1f),
                    Keyframe.ofFloat(.2f, 1.2f),
                    Keyframe.ofFloat(.4f, 1.2f),
                    Keyframe.ofFloat(.6f, 1.2f),
                    Keyframe.ofFloat(.8f, 1.2f),
                    Keyframe.ofFloat(1f, 1f)
            )

            val scaleYValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                    Keyframe.ofFloat(0f, 1f),
                    Keyframe.ofFloat(.2f, 1.2f),
                    Keyframe.ofFloat(.4f, 1.2f),
                    Keyframe.ofFloat(.6f, 1.2f),
                    Keyframe.ofFloat(.8f, 1.2f),
                    Keyframe.ofFloat(1f, 1f)
            )

            val rotateValuesHolder = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                    Keyframe.ofFloat(0f, 0f),
                    Keyframe.ofFloat(0.2f, shakeDegrees),
                    Keyframe.ofFloat(0.4f, -shakeDegrees),
                    Keyframe.ofFloat(0.6f, shakeDegrees),
                    Keyframe.ofFloat(0.8f, -shakeDegrees),
                    Keyframe.ofFloat(1.0f, 0f)
            )
            val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(widget, scaleXValuesHolder, scaleYValuesHolder, rotateValuesHolder)
            this.objectAnimator = objectAnimator
            objectAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    currentTimes++
                    if (repeat) {
                        // 小于0为无限制
                        val maximumLimitReached = if (repeatMaxTimes >= 0) {
                            currentTimes >= repeatMaxTimes
                        } else {
                            false
                        }
                        if (!maximumLimitReached && !cancel) {
                            animation?.startDelay = repeatInterval
                            animation?.start()
                        }
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            objectAnimator.duration = duration
            objectAnimator.startDelay = startDelay
            objectAnimator.start()
            cancel = false
        }
    }

    fun cancel() {
        cancel = true
        currentTimes = 0
        this.objectAnimator?.cancel()
    }

    companion object {
        @JvmStatic
        fun with(widget: View): AlertWidgetShaker {
            return AlertWidgetShaker(widget)
        }
    }
}