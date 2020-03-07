package com.base.paginate.base;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.base.paginate.viewholder.PageViewHolder;

/**
 * @param <T>
 */

@SuppressWarnings("WeakerAccess")
public abstract class SingleAdapter<T> extends MultiAdapter<T> {

    private static final int TYPE_COMMON_VIEW = 100001;


    public SingleAdapter(Context context) {
        this(context, true);
    }

    public SingleAdapter(Context context, boolean openLoadMore) {
        this(context, openLoadMore, true);
    }

    public SingleAdapter(Context context,  boolean openLoadMore, boolean openEmpty) {
        super(context, openLoadMore, openEmpty);
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
    protected void convert(RecyclerView.ViewHolder holder, T data, int position, int viewType) {
        convert((PageViewHolder)holder, data, position);
    }

    protected abstract void convert(PageViewHolder holder, T data, int position);

    protected abstract int getItemLayoutId();


}
