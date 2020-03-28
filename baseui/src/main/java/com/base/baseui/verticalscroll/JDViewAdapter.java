package com.base.baseui.verticalscroll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

public abstract class JDViewAdapter<T> {
    private List<T> mDatas;
    private Context context;
    private int mLayoutId;

    public JDViewAdapter(List<T> mDatas, Context context,int layoutId) {
        this.mDatas = mDatas;
        this.context = context;
        this.mLayoutId = layoutId;
        if (mDatas == null || mDatas.isEmpty()) {
            throw new RuntimeException("nothing to showToast");
        }
    }
    /**
     * 获取数据的条数
     *
     * @return
     */
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    /**
     * 获取摸个数据
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        return mDatas.get(position);
    }

    /**
     * 获取条目布局
     *
     * @param parent
     * @return
     */
    public View getView(JDAdverView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(mLayoutId, null);
    }
    /**
     * 条目数据适配
     * @param view
     * @param data
     */
    public abstract void setItem(final View view, T data);

}
