package com.gas.test.ui.fragment.adapter;

import android.content.Context;

import com.base.paginate.viewholder.PageViewHolder;
import com.base.paginate.base.SingleAdapter;
import com.gas.test.R;

public class SimpleAdapter extends SingleAdapter<String> {

    public SimpleAdapter(Context context) {
        super(context);
    }

    @Override
    protected void convert(PageViewHolder holder, String data, int position) {

        holder.setText(R.id.item_desc, data);

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.test_item_simple_item;
    }





}
