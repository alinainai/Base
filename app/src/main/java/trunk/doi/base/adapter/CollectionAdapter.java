package trunk.doi.base.adapter;

import android.content.Context;
import android.view.View;

import com.base.lib.base.adapter.rvadapter.ViewHolder;
import com.base.lib.base.adapter.rvadapter.base.CommonBaseAdapter;

import java.util.List;

import trunk.doi.base.R;
import trunk.doi.base.bean.CollectionBean;


/**
 * Author: Othershe
 * Time: 2016/8/18 16:53
 */
public class CollectionAdapter extends CommonBaseAdapter<CollectionBean> {


    private OnDeleteClickListener mListener;

    public CollectionAdapter(Context context, List<CollectionBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }


    @Override
    protected void convert(final ViewHolder holder, final CollectionBean data, final int position) {

        holder.setText(R.id.tv_desc, data.getDesc());
        holder.setText(R.id.tv_date, data.getDataTime());
        holder.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mListener) {
                    mListener.onItemClick(holder, data, position);
                }
            }
        });

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_collection;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        mListener = listener;
    }

    public interface OnDeleteClickListener {
        void onItemClick(ViewHolder viewHolder, CollectionBean data, int position);
    }


}
