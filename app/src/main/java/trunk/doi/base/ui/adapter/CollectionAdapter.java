package trunk.doi.base.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import trunk.doi.base.R;
import trunk.doi.base.base.adapter.rvadapter.ViewHolder;
import trunk.doi.base.base.adapter.rvadapter.base.CommonBaseAdapter;
import trunk.doi.base.bean.CollectionBean;
import trunk.doi.base.bean.GankItemData;


/**
 * Author: Othershe
 * Time: 2016/8/18 16:53
 */
public class CollectionAdapter extends CommonBaseAdapter<CollectionBean> {

    private boolean mIsEdit;

    public CollectionAdapter(Context context, List<CollectionBean> datas, boolean isOpenLoadMore,boolean isEdit) {
        super(context, datas, isOpenLoadMore);
        mIsEdit=isEdit;
    }

    @Override
    protected void convert(ViewHolder holder, CollectionBean data, int position) {

        holder.setText(R.id.tv_desc, data.getDesc());
        holder.setText(R.id.tv_date, data.getDataTime());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_collection;
    }


    public void setEdit(boolean isEdit){
        mIsEdit=isEdit;
    }

}
