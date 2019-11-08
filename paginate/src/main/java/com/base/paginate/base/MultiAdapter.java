package com.base.paginate.base;

import android.content.Context;
import android.view.ViewGroup;

import com.base.paginate.PageViewHolder;

import java.util.List;


/**
 * 多个布局条目
 *
 * @param <T>
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class MultiAdapter<T> extends BaseAdapter<T> {

    public MultiAdapter(Context context) {
        this(context,true);
    }

    public MultiAdapter(Context context,  boolean openLoadMore) {
        this(context,  openLoadMore, true);
    }

    public MultiAdapter(Context context,  boolean openLoadMore, boolean openEmpty) {
        super(context,  openLoadMore, openEmpty);
    }

    protected abstract int getItemLayoutId(int viewType);

    @Override
    protected PageViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return PageViewHolder.create(parent.getContext(), getItemLayoutId(viewType), parent);
    }


}
