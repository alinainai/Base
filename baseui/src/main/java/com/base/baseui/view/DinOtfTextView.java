package com.base.baseui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class DinOtfTextView extends AppCompatTextView {


    public DinOtfTextView(Context context) {
        this(context,null);
    }

    public DinOtfTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842884);
    }

    public DinOtfTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        try {
            Typeface newFont = Typeface.createFromAsset(context.getAssets(), "fonts/din_alternate_bold.ttf");
            setTypeface(newFont);
        }catch (Exception e){

        }
    }


}
