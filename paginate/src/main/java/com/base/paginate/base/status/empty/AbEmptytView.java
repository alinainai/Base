package com.base.paginate.base.status.empty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import com.base.paginate.base.status.IStatus;


@SuppressLint("ViewConstructor")
public abstract class AbEmptytView extends LinearLayout {


    public AbEmptytView(Context context) {
        super(context);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(getLayoutID(), this, true);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);
        setBackgroundColor(Color.WHITE);

    }

    @LayoutRes
    public abstract int getLayoutID();

    public abstract void setStatus(@IStatus.StatusType int status);

}
