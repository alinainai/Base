package com.gas.test.ui.activity.home;

import android.content.Context;

import com.base.paginate.viewholder.PageViewHolder;
import com.base.paginate.base.SingleAdapter;
import com.gas.test.R;
import com.gas.test.bean.TestInfoBean;


/**
 *
 */
public class HomeAdapter extends SingleAdapter<TestInfoBean> {


    public HomeAdapter(Context context) {
        super(context,false,true);
    }

    @Override
    protected void convert(PageViewHolder holder, TestInfoBean data, int position) {
        holder.setText(R.id.item_desc,data.getShowInfo());
    }


    @Override
    protected int getItemLayoutId() {
        return R.layout.test_item_test_info;
    }

}
