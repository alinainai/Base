package com.gas.beauty.ui.article.classify;

import android.content.Context;
import android.text.TextUtils;


import com.base.paginate.PageViewHolder;
import com.base.paginate.base.SingleAdapter;
import com.gas.beauty.R;
import com.gas.beauty.bean.GankItemBean;


/**
 *
 */
public class ClassifyAdapter extends SingleAdapter<GankItemBean> {



    public ClassifyAdapter(Context context) {
        super(context);
    }

    @Override
    protected void convert(PageViewHolder holder, GankItemBean data, int position) {
        holder.setText(R.id.gank_item_desc, data.getDesc());
        String who = TextUtils.isEmpty(data.getWho()) ? "佚名" : data.getWho();
        holder.setText(R.id.gank_item_who, who);
        holder.setText(R.id.gank_item_publishedat,TextUtils.isEmpty(data.getPublishedAt())?"":data.getPublishedAt().substring(0, 10));
    }


    @Override
    protected int getItemLayoutId() {
        return R.layout.gank_item_article;
    }

}
