package com.base.lib.base.adapter.rvadapter.interfaces;


import com.base.lib.base.adapter.rvadapter.ViewHolder;

/**
 * Author: Othershe
 * Time: 2016/8/29 10:48
 */
public interface OnItemClickListener<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position);
}