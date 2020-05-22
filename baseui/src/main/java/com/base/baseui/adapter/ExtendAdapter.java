package com.base.baseui.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.base.paginate.viewholder.PageViewHolder;

/**
 * ================================================
 * desc: BaseAdapter的推展，主要有以下功能
 * 1.自定义加载布局、错误布局、空布局样式
 * 2.自定义底部加载更多样式
 * <p>
 * created by author ljx
 * Date  2020/3/20
 * email 569932357@qq.com
 * <p>
 * ================================================
 */
public abstract class ExtendAdapter<T> extends ExtendMultiAdapter<T> {


    private static final int TYPE_COMMON_VIEW = 100001;


    public ExtendAdapter(Context context) {
        this(context, true);
    }

    public ExtendAdapter(Context context, boolean openLoadMore) {
        this(context, openLoadMore, true);
    }

    public ExtendAdapter(Context context, boolean openLoadMore, boolean openEmpty) {
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
    protected void convert(PageViewHolder holder, T data, int position, int viewType) {
        convert( holder, data, position);
    }

    protected abstract void convert(PageViewHolder holder, T data, int position);

    protected abstract int getItemLayoutId();


}
