package trunk.doi.base.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import java.util.List;

import trunk.doi.base.R;
import trunk.doi.base.base.adapter.rvadapter.ViewHolder;
import trunk.doi.base.base.adapter.rvadapter.base.BaseAdapter;
import trunk.doi.base.base.adapter.rvadapter.base.CommonBaseAdapter;
import trunk.doi.base.bean.GankItemData;


/**
 *
 *
 */
public class GankItemAdapter extends CommonBaseAdapter<GankItemData> {


    public GankItemAdapter(Context context, List<GankItemData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, GankItemData data, int position) {
        holder.setText(R.id.gank_item_desc, data.getDesc());
        String who = TextUtils.isEmpty(data.getWho()) ? "佚名" : data.getWho();
        holder.setText(R.id.gank_item_who, who);
        holder.setText(R.id.gank_item_publishedat, data.getPublishedAt().substring(0, 10));
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_gank_layout;
    }

}
