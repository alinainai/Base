package com.base.baseui.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.base.baseui.R;

/**
 * ================================================
 * desc: 支持最大高度RecyclerView
 *
 * created by author ljx
 * date  2020/4/2
 * email 569932357@qq.com
 *
 * ================================================
 */
public class RecyclerViewWithMaxHeight extends RecyclerView {

    public RecyclerViewWithMaxHeight(Context context) {
        super(context);
    }

    public RecyclerViewWithMaxHeight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewWithMaxHeight(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        heightSpec = MeasureSpec.makeMeasureSpec(getResources().getDimensionPixelSize(R.dimen.public_menu_max_height), MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightSpec);
    }
}
