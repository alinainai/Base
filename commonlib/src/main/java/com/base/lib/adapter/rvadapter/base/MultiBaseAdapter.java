package com.base.lib.adapter.rvadapter.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.base.lib.adapter.rvadapter.ViewHolder;
import com.base.lib.adapter.rvadapter.interfaces.OnMultiItemClickListeners;

import java.util.ArrayList;
import java.util.List;



/**
 * Author: Othershe
 * Time: 2016/9/9 16:21
 */
public abstract class MultiBaseAdapter<T> extends BaseAdapter<T> {
    private OnMultiItemClickListeners<T> mItemClickListener;



    public MultiBaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    protected abstract void convert(ViewHolder holder, T data, int position, int viewType);

    protected abstract int getItemLayoutId(int viewType);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isCommonItemView(viewType)) {
            return ViewHolder.create(mContext, getItemLayoutId(viewType), parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (isCommonItemView(viewType)) {
            bindCommonItem(holder, position , viewType);
        }
    }

    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position, final int viewType) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        convert(viewHolder, getAllData().get(position), position, viewType);

        viewHolder.getConvertView().setOnClickListener(view -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(viewHolder, getAllData().get(position), position, viewType);
            }
        });

    }

    public void setOnMultiItemClickListener(OnMultiItemClickListeners<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

}
