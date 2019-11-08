package com.gas.zhihu.main;

import android.content.Context;

import com.base.lib.https.image.ImageLoader;
import com.base.paginate.PageViewHolder;
import com.base.paginate.base.SingleAdapter;
import com.gas.zhihu.R;
import com.gas.zhihu.bean.DailyListBean;

import java.util.List;



public class MainAdapter extends SingleAdapter<DailyListBean.StoriesBean> {



    public MainAdapter(Context context) {
        super(context,false,true);
    }

    @Override
    protected void convert(PageViewHolder holder, DailyListBean.StoriesBean data, int position) {
        holder.setText(R.id.tv_name,data.getTitle());
        ImageLoader.loadSet(mContext,data.getImages().get(0),holder.getView(R.id.iv_avatar),R.mipmap.zhihu_ic_launcher);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.zhihu_recycle_list;
    }
}
