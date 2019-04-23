package com.base.lib.adapter.rvadapter.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.base.lib.adapter.rvadapter.ViewHolder;
import com.base.lib.adapter.rvadapter.interfaces.OnItemClickListener;

import java.util.List;


/**
 * Author: Othershe
 * Time: 2016/9/9 15:52
 */
public abstract class CommonBaseAdapter<T> extends BaseAdapter<T> {
    private OnItemClickListener<T> mItemClickListener;

    public CommonBaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    protected abstract void convert(ViewHolder holder, T data, int position);

    protected abstract int getItemLayoutId();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isCommonItemView(viewType)) {
            return ViewHolder.create(mContext, getItemLayoutId(), parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (isCommonItemView(viewType)) {
            bindCommonItem(holder, position);
        }
    }

    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        convert(viewHolder, getAllData().get(position), position);

        viewHolder.getConvertView().setOnClickListener(view -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(viewHolder, getAllData().get(position), position);
            }
        });
    }

    @Override
    protected int getViewType(int position, T data) {
        return TYPE_COMMON_VIEW;
    }

    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }


}
