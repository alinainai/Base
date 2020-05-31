package com.gas.zhihu.fragment.mapshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base.baseui.adapter.ExtendMultiAdapter
import com.base.paginate.viewholder.PageViewHolder
import com.gas.zhihu.R
import com.gas.zhihu.fragment.mapshow.bean.CharSortBean
import com.gas.zhihu.fragment.mapshow.bean.ISortBean

class MapShowAdapter(context: Context?) : ExtendMultiAdapter<ISortBean>(context, true, true) {

    companion object {
        const val TYPE_CHAR = 0xF1
        const val TYPE_ITEM = 0xF2
    }


    override fun getItemLayoutId(viewType: Int): Int {
        R.layout.zhihu_dialog_select_char
        R.layout.zhihu_dialog_select_item
        return when(viewType){
            TYPE_CHAR -> {
                R.layout.zhihu_dialog_select_char
            }
            else -> {
                R.layout.zhihu_dialog_select_item
            }
        }
    }

    override fun getViewType(position: Int, data: ISortBean?): Int {
        return if (data is CharSortBean) {
            TYPE_CHAR
        } else {
            TYPE_ITEM
        }
    }

    override fun convert(holder: PageViewHolder?, data: ISortBean?, position: Int, viewType: Int) {
        holder?.setText(R.id.tagView,data?.showTitle?:"")
    }


}