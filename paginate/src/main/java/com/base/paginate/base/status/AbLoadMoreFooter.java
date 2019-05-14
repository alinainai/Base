package com.base.paginate.base.status;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.base.paginate.Util;

@SuppressLint("ViewConstructor")
public abstract class AbLoadMoreFooter extends LinearLayout {

    public AbLoadMoreFooter(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Util.dpToPx(getContext(), 50)));
        LayoutInflater.from(context).inflate(getLayoutID(), this, true);
        setBackgroundColor(Color.WHITE);
    }


    @IStatus.StatusType
    public abstract int getLoadMoreStatus();

    @LayoutRes
    public abstract int getLayoutID();

    public abstract void setStatus(@IStatus.StatusType int status);


}
