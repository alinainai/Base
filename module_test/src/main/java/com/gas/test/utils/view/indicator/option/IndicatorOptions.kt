package com.zhpan.indicator.option

import android.graphics.Color

import com.zhpan.indicator.annotation.AIndicatorSlideMode
import com.zhpan.indicator.annotation.AIndicatorStyle
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.utils.IndicatorUtils


/**
 * <pre>
 * Created by zhpan on 2019/11/20.
 * Description:Indicator的配置参数
</pre> *
 */
class IndicatorOptions {

    @AIndicatorStyle
    var indicatorStyle: Int = 0

    /**
     * Indicator滑动模式，目前仅支持两种
     *
     * @see IndicatorSlideMode.NORMAL
     *
     * @see IndicatorSlideMode.SMOOTH
     */
    @AIndicatorSlideMode
    var slideMode: Int = 0

    /**
     * 页面size
     */
    var pageSize: Int = 0
    /**
     * 未选中时Indicator颜色
     */
    var normalSliderColor: Int = 0
        private set
    /**
     * 选中时Indicator颜色
     */
    var checkedSliderColor: Int = 0
        private set
    /**
     * Indicator间距
     */
    var sliderGap: Float = 0.toFloat()

    var sliderHeight: Float = 0.toFloat()
        get() = if (field > 0) field else normalSliderWidth / 2

    var normalSliderWidth: Float = 0.toFloat()
        private set

    var checkedSliderWidth: Float = 0.toFloat()
        private set

    /**
     * 指示器当前位置
     */
    var currentPosition: Int = 0

    /**
     * 从一个点滑动到另一个点的进度
     */
    var slideProgress: Float = 0.toFloat()

    init {
        normalSliderWidth = IndicatorUtils.dp2px(8f).toFloat()
        checkedSliderWidth = normalSliderWidth
        sliderGap = normalSliderWidth
        normalSliderColor = Color.parseColor("#8C18171C")
        checkedSliderColor = Color.parseColor("#8C6C6D72")
        slideMode = IndicatorSlideMode.NORMAL
    }

    fun setCheckedColor(checkedColor: Int) {
        this.checkedSliderColor = checkedColor
    }

    fun setSliderWidth(normalIndicatorWidth: Float, checkedIndicatorWidth: Float) {
        this.normalSliderWidth = normalIndicatorWidth
        this.checkedSliderWidth = checkedIndicatorWidth
    }

    fun setSliderWidth(sliderWidth: Float) {
        setSliderWidth(sliderWidth, sliderWidth)
    }

    fun setSliderColor(normalColor: Int, checkedColor: Int) {
        this.normalSliderColor = normalColor
        this.checkedSliderColor = checkedColor
    }
}
