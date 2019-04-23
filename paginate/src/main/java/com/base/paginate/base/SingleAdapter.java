package com.base.paginate.base;

import android.content.Context;


import com.base.paginate.PageViewHolder;

import java.util.List;

/**
 * @param <T>
 */

@SuppressWarnings("WeakerAccess")
public abstract class SingleAdapter<T> extends MultiAdapter<T> {

    private static final int TYPE_COMMON_VIEW = 100001;


    public SingleAdapter(Context context, List<T> data) {
        this(context, data, true);
    }

    public SingleAdapter(Context context, List<T> data, boolean isOpenLoadMore) {
        super(context, data, isOpenLoadMore);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return getItemLayoutId();
    }


    @Override
    protected int getViewType(int position, T data) {
        return TYPE_COMMON_VIEW;
    }

    @Override
    protected void convert(PageViewHolder holder, T data, int position, int viewType) {
        convert(holder, data, position);
    }

    protected abstract void convert(PageViewHolder holder, T data, int position);

    protected abstract int getItemLayoutId();


}
