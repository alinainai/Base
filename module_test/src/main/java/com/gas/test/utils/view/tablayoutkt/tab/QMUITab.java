package com.gas.test.utils.view.tablayoutkt.tab;

import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class QMUITab {

    public static final int NO_SIGN_COUNT_AND_RED_POINT = 0;
    public static final int RED_POINT_SIGN_COUNT = -1;
    public static final int SIGN_COUNT_VIEW_LAYOUT_VERTICAL_CENTER = Integer.MIN_VALUE;
    public int normalTextSize;
    public int selectedTextSize;
    public Typeface normalTypeface;
    public  Typeface selectedTypeface;
    public  float typefaceUpdateAreaPercent;
    public int normalColor;
    public  int selectColor;
    public int normalColorAttr;
    public  int selectedColorAttr;
    public  int contentWidth = 0;
    public  int contentLeft = 0;
    public int gravity = Gravity.CENTER;
    public  CharSequence text;
    public  int signCountDigits = 2;
    public int signCountLeftMarginWithIconOrText = 0;
    public  int signCountBottomMarginWithIconOrText = 0;
    public  int signCount = NO_SIGN_COUNT_AND_RED_POINT;

    public  float rightSpaceWeight = 0f;
    public  float leftSpaceWeight = 0f;
    public  int leftAddonMargin = 0;
    public  int rightAddonMargin = 0;


    public QMUITab(CharSequence text) {
        this.text = text;
    }


    public CharSequence getText() {
        return text;
    }

    public void setText(CharSequence text) {
        this.text = text;
    }



    public void setSpaceWeight(float leftWeight, float rightWeight) {
        leftSpaceWeight = leftWeight;
        rightSpaceWeight = rightWeight;
    }

    public void setTypefaceUpdateAreaPercent(float typefaceUpdateAreaPercent) {
        this.typefaceUpdateAreaPercent = typefaceUpdateAreaPercent;
    }

    public float getTypefaceUpdateAreaPercent() {
        return typefaceUpdateAreaPercent;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setSignCount(int signCount) {
        this.signCount = signCount;
    }

    public void setRedPoint(){
        this.signCount =  RED_POINT_SIGN_COUNT;
    }

    public int getSignCount(){
        return this.signCount;
    }

    public boolean isRedPointShowing(){
        return this.signCount == RED_POINT_SIGN_COUNT;
    }

    public void clearSignCountOrRedPoint(){
        this.signCount = NO_SIGN_COUNT_AND_RED_POINT;
    }

    public int getNormalColor(@NonNull View skinFollowView) {
        if(normalColorAttr == 0){
            return normalColor;
        }
        return normalColorAttr;
    }

    public int getSelectColor(@NonNull View skinFollowView) {
        if(selectedColorAttr == 0){
            return selectColor;
        }
        return selectedColorAttr;
    }
    public int getNormalColorAttr() {
        return normalColorAttr;
    }
    public int getSelectedColorAttr() {
        return selectedColorAttr;
    }
    public int getNormalTextSize() {
        return normalTextSize;
    }
    public int getSelectedTextSize() {
        return selectedTextSize;
    }
    public Typeface getNormalTypeface() {
        return normalTypeface;
    }
    public Typeface getSelectedTypeface() {
        return selectedTypeface;
    }

}
