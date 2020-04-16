package com.gas.test.learn.viewcircle

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import timber.log.Timber

/**
 * ================================================
 * desc: view 生命周期
 *
 * created by author ljx
 * Date  2020/4/9
 * email lijiaxing@360.cn
 *
 * ================================================
 */
class LifecycleView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        Timber.e("constructor")
        attrs?.let {

        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        Timber.e("onFinishInflate")
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        Timber.e("onVisibilityChanged.visibility=$visibility")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Timber.e("onAttachedToWindow")
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        Timber.e("onWindowVisibilityChanged.visibility=$visibility")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Timber.e("onMeasure/widthMeasureSpec=$widthMeasureSpec/heightMeasureSpec=$heightMeasureSpec")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Timber.e("onSizeChanged/w=$w/h=$h/oldw=$oldw/oldh=$oldh")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Timber.e("onLayout/changed=$changed/left=$left/top=$top/right=$right/bottom=$bottom")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Timber.e("onDraw/canvas=$canvas")
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        Timber.e("onWindowFocusChanged/hasWindowFocus=$hasWindowFocus")
    }

    override fun onDetachedFromWindow() {
        Timber.e("onDetachedFromWindow")
        super.onDetachedFromWindow()
    }

}