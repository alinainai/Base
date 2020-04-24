package com.base.paginate.base;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.base.paginate.Utils;
import com.base.paginate.interfaces.OnMultiItemClickListeners;
import com.base.paginate.viewholder.PageViewHolder;


/**
 * 多个布局条目
 *
 * @param <T>
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class MultiAdapter<T> extends BaseAdapter<T> {


    protected Context mContext;
    /**
     * 条目点击监听
     */
    protected OnMultiItemClickListeners<T> mItemClickListener;

    public MultiAdapter(Context context) {
        this(context, true);
    }

    public MultiAdapter(Context context, boolean openLoadMore) {
        this(context, openLoadMore, true);
    }

    public MultiAdapter(Context context, boolean openLoadMore, boolean openEmpty) {
        super(openLoadMore, openEmpty);
        mContext = context;
    }

    protected abstract int getItemLayoutId(int viewType);

    @Override
    protected PageViewHolder getViewHolder(ViewGroup parent, int viewType) {

        PageViewHolder holder = Utils.createPageViewHolder(getItemLayoutId(viewType), parent);
        //在onCreateViewHolder进行点击事件注入，不用放在onBindViewHolder中，会影响性能
        holder.getConvertView().setOnClickListener(view -> {

            final int dataPos = holder.getAdapterPosition() - getHeaderCount();
            if (mItemClickListener != null && dataPos < mData.size() && dataPos >= 0) {
                mItemClickListener.onItemClick(holder, mData.get(dataPos), dataPos, viewType);
            }
        });

        return holder;
    }

    @Override
    protected void convert(RecyclerView.ViewHolder holder, T data, int position, int viewType) {
        convert((PageViewHolder) holder, data, position, viewType);

    }

    protected abstract void convert(PageViewHolder holder, T data, int position, int viewType);

    public void setOnMultiItemClickListener(OnMultiItemClickListeners<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }


}
