package com.gas.test.utils.fragment.asynclist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.gas.test.R
import com.gas.test.utils.fragment.asynclist.IModeHelper.Companion.VIEW_TYPE_EVENT_ITEM
import com.gas.test.utils.fragment.asynclist.IModeHelper.Companion.VIEW_TYPE_EVENT_ITEM_GRID
import com.gas.test.utils.fragment.asynclist.bean.BaseTimestamp
import com.gas.test.utils.fragment.asynclist.bean.DataItemBean
import com.gas.test.utils.listadapter.BaseListViewAdapter

class AsyncUpdateDataAdapter : BaseListViewAdapter<RecyclerView.ViewHolder, BaseTimestamp>(DIFF_CALLBACK), IModeHelper {

    override var displayMode: DisPlayMode = DisPlayMode.LIST

    override fun getItemViewType(position: Int): Int {
        return if (displayMode.isList()) VIEW_TYPE_EVENT_ITEM else VIEW_TYPE_EVENT_ITEM_GRID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_EVENT_ITEM -> {
                ListViewHolder(parent)
            }
            VIEW_TYPE_EVENT_ITEM_GRID -> {
                GridViewHolder(parent)
            }
            else -> ListViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListViewHolder -> {
                holder.bind(data[position] as DataItemBean)
            }
            is GridViewHolder -> {
                holder.bind(data[position] as DataItemBean)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (getItemViewType(position) == VIEW_TYPE_EVENT_ITEM_GRID) 1 else layoutManager.spanCount
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<BaseTimestamp> = object : DiffUtil.ItemCallback<BaseTimestamp>() {
            override fun areItemsTheSame(oldItem: BaseTimestamp, newItem: BaseTimestamp): Boolean {
                //用于区分的实体类的唯一表示符
                return oldItem.uniqueId() == newItem.uniqueId()
            }

            override fun areContentsTheSame(oldItem: BaseTimestamp, newItem: BaseTimestamp): Boolean {
                //可变内容
                return oldItem.variableParam() == newItem.variableParam()
            }
        }
    }

    class ListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_async_test_list, parent, false)) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        fun bind(info: DataItemBean) {
            tvTitle.text = info.title
        }
    }

    class GridViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_async_test_grid, parent, false)) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        fun bind(info: DataItemBean) {
            tvTitle.text = info.title
        }
    }


}