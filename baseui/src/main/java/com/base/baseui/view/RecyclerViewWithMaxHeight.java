package com.base.baseui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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


    private int maxHeight;

    public RecyclerViewWithMaxHeight(Context context) {
        super(context);
        initAttrs(null);
    }

    public RecyclerViewWithMaxHeight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public RecyclerViewWithMaxHeight(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RecyclerViewWithMaxHeight);
        maxHeight = typedArray.getDimensionPixelSize(R.styleable.RecyclerViewWithMaxHeight_maxHeight, getResources().getDimensionPixelSize(R.dimen.public_menu_max_height));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        heightSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightSpec);
    }
}
