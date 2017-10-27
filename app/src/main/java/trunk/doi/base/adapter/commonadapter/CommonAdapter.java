package trunk.doi.base.adapter.commonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ly on 2016/5/30 14:52.
 * 通用适配器
 * 适用于ListView 和 GirdView
 */
public abstract class CommonAdapter<T> extends BaseAdapter{
    protected LayoutInflater mInflater;
    protected boolean conver = true;
    protected int mItemLayoutId;
    protected List<T> mList;
    protected Context context;

    public CommonAdapter(Context context, List<T> list, int itemLayoutId) {
        this.mItemLayoutId = itemLayoutId;
        this.context = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return IGNORE_ITEM_VIEW_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.get(context,
                convertView, parent, mItemLayoutId, position);
        convert(viewHolder, position, getItem(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(CommonViewHolder holder, int position, T item);
}
