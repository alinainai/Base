package com.test.module_beauty.main;

import android.content.Context;

import com.base.lib.https.image.ImageLoader;
import com.base.paginate.PageViewHolder;
import com.base.paginate.base.SingleAdapter;
import com.test.module_beauty.R;
import com.test.module_beauty.bean.GankItemBean;


public class MainAdapter extends SingleAdapter<GankItemBean> {

    public MainAdapter(Context context) {
        super(context);
    }

    @Override
    protected void convert(PageViewHolder holder, GankItemBean data, int position) {
        ImageLoader.loadSet(mContext,data.getUrl(),holder.getView(R.id.iv_avatar),R.mipmap.gank_ic_launcher);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.gank_recycle_list;
    }
}
