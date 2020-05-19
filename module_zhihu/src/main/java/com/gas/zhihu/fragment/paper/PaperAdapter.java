package com.gas.zhihu.fragment.paper;

import android.content.Context;

import com.base.paginate.base.SingleAdapter;
import com.base.paginate.viewholder.PageViewHolder;
import com.gas.zhihu.R;
import com.gas.zhihu.bean.DailyListBean;
import com.gas.zhihu.bean.IPaperShowBean;
import com.gas.zhihu.bean.PaperBean;


public class PaperAdapter extends SingleAdapter<IPaperShowBean> {



    public PaperAdapter(Context context) {
        super(context);
    }

    @Override
    protected void convert(PageViewHolder holder, IPaperShowBean data, int position) {
        holder.setText(R.id.itemTitle,data.getFileName());
        holder.setText(R.id.itemMap,data.getMapName());
        holder.setText(R.id.itemVoltage,data.getVoltageLevel());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.zhihu_item_pager;
    }
}
