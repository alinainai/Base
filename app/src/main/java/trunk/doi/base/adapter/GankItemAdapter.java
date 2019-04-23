package trunk.doi.base.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.base.paginate.PageViewHolder;
import com.base.paginate.base.SingleAdapter;

import java.util.List;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.bean.GankItemData;


/**
 *
 */
public class GankItemAdapter extends SingleAdapter<GankItemData> {


    @BindView(R.id.gank_item_desc)
    TextView gankItemDesc;
    @BindView(R.id.gank_item_who)
    TextView gankItemWho;
    @BindView(R.id.gank_item_publishedat)
    TextView gankItemPublishedat;

    public GankItemAdapter(Context context, List<GankItemData> data) {
        super(context, data);
    }

    @Override
    protected void convert(PageViewHolder holder, GankItemData data, int position) {
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
