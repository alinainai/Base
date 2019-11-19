package com.gas.test.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class RatioView extends View {

    private int mRatio=2;

    public RatioView(Context context) {
        super(context);
    }

    public RatioView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((ViewGroup)this.getParent()).requestDisallowInterceptTouchEvent(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        if(mRatio!=0){
            int height=width/2;
            heightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
