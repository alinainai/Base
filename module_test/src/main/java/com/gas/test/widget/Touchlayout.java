package com.gas.test.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class Touchlayout extends ViewGroup {

    //记录用户按到哪了
    int downY;


    public Touchlayout(Context context) {
        super(context);
    }

    public Touchlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Touchlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    //是否延迟子view的press事件，不支持滑动返回false
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if(ev.getActionMasked()==MotionEvent.ACTION_DOWN){
//            downY=(int)ev.getY();
//        }
//
////        if()
////            return false;
////        else
////            return true;
//    }
}
