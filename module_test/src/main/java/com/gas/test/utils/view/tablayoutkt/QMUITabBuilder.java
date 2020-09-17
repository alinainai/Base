package com.gas.test.utils.view.tablayoutkt;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.gas.test.R;
import com.gas.test.utils.view.tablayoutkt.tab.QMUITab;
import com.gas.test.utils.view.tablayoutkt.utils.TabUtils;


/**
 *
 */
public class QMUITabBuilder {

    /**
     * text size in normal state
     */
    private int normalTextSize;
    /**
     * text size in selected state
     */
    private int selectTextSize;

    /**
     * text color(icon color in if dynamicChangeIconColor == true) in  normal state
     */
    private int normalColorAttr = R.attr.qmui_skin_support_tab_normal_color;
    /**
     * text color(icon color in if dynamicChangeIconColor == true) in  selected state
     */
    private int selectedColorAttr = R.attr.qmui_skin_support_tab_selected_color;

    /**
     * text color with no skin support
     */
    private int normalColor = 0;

    /**
     * text color with no skin support
     */
    private int selectColor = 0;

    /**
     * gravity of text
     */
    private int gravity = Gravity.CENTER;
    /**
     * text
     */
    private CharSequence text;

    /**
     * text typeface in normal state
     */
    private Typeface normalTypeface;

    /**
     * text typeface in selected state
     */
    private Typeface selectedTypeface;

    float typefaceUpdateAreaPercent = 0.25f;

    /**
     * signCount or redPoint
     */
    private int signCount = QMUITab.NO_SIGN_COUNT_AND_RED_POINT;

    /**
     * max signCount digits, if the number is over the digits, use 'xx+' to present
     * if signCountDigits == 2 and number is 110, then component will show '99+'
     */
    private int signCountDigits = 2;
    /**
     * the margin left of signCount(redPoint) view
     */
    private int signCountLeftMarginWithIconOrText;
    /**
     * the margin top of signCount(redPoint) view
     */
    private int signCountBottomMarginWithIconOrText;

    QMUITabBuilder(Context context) {
        normalTextSize = selectTextSize = TabUtils.dpToPx(context, 12);
        signCountLeftMarginWithIconOrText = TabUtils.dpToPx(context, 3);
        signCountBottomMarginWithIconOrText = signCountLeftMarginWithIconOrText;
    }

    QMUITabBuilder(QMUITabBuilder other) {
        this.normalTextSize = other.normalTextSize;
        this.selectTextSize = other.selectTextSize;
        this.normalColorAttr = other.normalColorAttr;
        this.selectedColorAttr = other.selectedColorAttr;
        this.gravity = other.gravity;
        this.text = other.text;
        this.signCount = other.signCount;
        this.signCountDigits = other.signCountDigits;
        this.signCountLeftMarginWithIconOrText = other.signCountLeftMarginWithIconOrText;
        this.signCountBottomMarginWithIconOrText = other.signCountBottomMarginWithIconOrText;
        this.normalTypeface = other.normalTypeface;
        this.selectedTypeface = other.selectedTypeface;
        this.typefaceUpdateAreaPercent = other.typefaceUpdateAreaPercent;
    }

    public QMUITabBuilder setTypefaceUpdateAreaPercent(float typefaceUpdateAreaPercent) {
        this.typefaceUpdateAreaPercent = typefaceUpdateAreaPercent;
        return this;
    }

    public QMUITabBuilder setTextSize(int normalTextSize, int selectedTextSize) {
        this.normalTextSize = normalTextSize;
        this.selectTextSize = selectedTextSize;
        return this;
    }

    public QMUITabBuilder setTypeface(Typeface normalTypeface, Typeface selectedTypeface) {
        this.normalTypeface = normalTypeface;
        this.selectedTypeface = selectedTypeface;
        return this;
    }

    public QMUITabBuilder setSignCount(int signCount) {
        this.signCount = signCount;
        return this;
    }

    public QMUITabBuilder setSignCountMarginInfo(int digit, int leftMarginWithIconOrText, int bottomMarginWithIconOrText) {
        this.signCountDigits = digit;
        this.signCountLeftMarginWithIconOrText = leftMarginWithIconOrText;
        this.signCountBottomMarginWithIconOrText = bottomMarginWithIconOrText;
        return this;
    }

    public QMUITabBuilder setColorAttr(int normalColorAttr, int selectedColorAttr) {
        this.normalColorAttr = normalColorAttr;
        this.selectedColorAttr = selectedColorAttr;
        return this;
    }

    public QMUITabBuilder setNormalColorAttr(int normalColorAttr) {
        this.normalColorAttr = normalColorAttr;
        return this;
    }

    public QMUITabBuilder setSelectedColorAttr(int selectedColorAttr) {
        this.selectedColorAttr = selectedColorAttr;
        return this;
    }

    public QMUITabBuilder setColor(int normalColor, int selectColor) {
        this.normalColorAttr = 0;
        this.selectedColorAttr = 0;
        this.normalColor = normalColor;
        this.selectColor = selectColor;
        return this;
    }

    public QMUITabBuilder setNormalColor(int normalColor) {
        this.normalColorAttr = 0;
        this.normalColor = normalColor;
        return this;
    }

    public QMUITabBuilder setSelectColor(int selectColor) {
        this.selectedColorAttr = 0;
        this.selectColor = selectColor;
        return this;
    }

    public QMUITabBuilder setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public QMUITabBuilder setText(CharSequence text) {
        this.text = text;
        return this;
    }

    public QMUITab build(Context context) {
        QMUITab tab = new QMUITab(this.text);
        tab.gravity = this.gravity;
        tab.normalTextSize = this.normalTextSize;
        tab.selectedTextSize = this.selectTextSize;
        tab.normalTypeface = this.normalTypeface;
        tab.selectedTypeface = this.selectedTypeface;
        tab.normalColorAttr = this.normalColorAttr;
        tab.selectedColorAttr = this.selectedColorAttr;
        tab.normalColor = this.normalColor;
        tab.selectColor = this.selectColor;
        tab.signCount = this.signCount;
        tab.signCountDigits = this.signCountDigits;
        tab.signCountLeftMarginWithIconOrText = this.signCountLeftMarginWithIconOrText;
        tab.signCountBottomMarginWithIconOrText = this.signCountBottomMarginWithIconOrText;
        tab.typefaceUpdateAreaPercent = this.typefaceUpdateAreaPercent;
        return tab;
    }
}
