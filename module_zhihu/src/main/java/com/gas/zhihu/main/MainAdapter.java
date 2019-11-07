package com.gas.zhihu.main;

import android.content.Context;

import com.base.paginate.PageViewHolder;
import com.base.paginate.base.SingleAdapter;
import com.gas.zhihu.R;
import com.gas.zhihu.bean.DailyListBean;

import java.util.List;

public class MainAdapter extends SingleAdapter<DailyListBean.StoriesBean> {
    public MainAdapter(Context context, List<DailyListBean.StoriesBean> data) {
        super(context, data);
    }

    @Override
    protected void convert(PageViewHolder holder, DailyListBean.StoriesBean data, int position) {

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.zhihu_recycle_list;
    }
}
