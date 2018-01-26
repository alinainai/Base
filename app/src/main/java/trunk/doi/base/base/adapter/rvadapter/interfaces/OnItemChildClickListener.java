package trunk.doi.base.base.adapter.rvadapter.interfaces;


import trunk.doi.base.base.adapter.rvadapter.ViewHolder;

/**
 * Author: Othershe
 * Time: 2016/8/29 10:48
 */
public interface OnItemChildClickListener<T> {
    void onItemChildClick(ViewHolder viewHolder, T data, int position);
}
