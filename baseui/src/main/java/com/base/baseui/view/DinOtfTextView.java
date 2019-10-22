package com.base.baseui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 特殊字体的TextView
 */
public class DinOtfTextView extends AppCompatTextView {


    public DinOtfTextView(Context context) {
        super(context);
        init(context);
    }

    public DinOtfTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DinOtfTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        Typeface newFont = Typeface.createFromAsset(context.getAssets(), "fonts/din_alternate_bold.ttf");
        setTypeface(newFont);
    }


}
