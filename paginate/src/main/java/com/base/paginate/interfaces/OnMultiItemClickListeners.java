package com.base.paginate.interfaces;


import com.base.paginate.viewholder.PageViewHolder;

/**
 * 多种布局条目点击事件
 *
 * @param <T>
 */
public interface OnMultiItemClickListeners<T> {
    void onItemClick(PageViewHolder viewHolder, T data, int position, int viewType);
}
