package com.gas.beauty.ui.classify

import android.content.Context
import android.text.TextUtils
import com.base.paginate.base.SingleAdapter
import com.base.paginate.viewholder.PageViewHolder
import com.gas.beauty.R
import com.gas.beauty.bean.BeautyBean

/**
 *
 */
class ClassifyAdapter(context: Context?) : SingleAdapter<BeautyBean>(context) {
    protected override fun convert(holder: PageViewHolder, data: BeautyBean, position: Int) {
        holder.setText(R.id.gank_item_desc, data.desc)
        val who = if (TextUtils.isEmpty(data.who)) "佚名" else data.who!!
        holder.setText(R.id.gank_item_who, who)
        holder.setText(R.id.gank_item_publishedat, if (TextUtils.isEmpty(data.publishedAt)) "" else data.publishedAt!!.substring(0, 10))
    }

    override fun getItemLayoutId(): Int {
        return R.layout.gank_item_article
    }
}