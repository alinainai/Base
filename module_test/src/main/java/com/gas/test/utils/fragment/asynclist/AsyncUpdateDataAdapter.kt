package com.gas.test.utils.fragment.asynclist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.gas.test.R
import com.gas.test.utils.fragment.asynclist.IModeHelper.Companion.VIEW_TYPE_EVENT_ITEM
import com.gas.test.utils.fragment.asynclist.IModeHelper.Companion.VIEW_TYPE_EVENT_ITEM_DATE
import com.gas.test.utils.fragment.asynclist.IModeHelper.Companion.VIEW_TYPE_EVENT_ITEM_GRID
import com.gas.test.utils.fragment.asynclist.IModeHelper.Companion.VIEW_TYPE_EVENT_ITEM_TIME
import com.gas.test.utils.fragment.asynclist.bean.BaseTimestamp
import com.gas.test.utils.fragment.asynclist.bean.DataItemBean
import com.gas.test.utils.fragment.asynclist.bean.DayItemBean
import com.gas.test.utils.fragment.asynclist.bean.TimeZoneItemBean
import com.gas.test.utils.fragment.asynclist.listadapter.BaseListViewAdapter
import com.lib.commonsdk.utils.TimeUtils

class AsyncUpdateDataAdapter : BaseListViewAdapter<RecyclerView.ViewHolder, BaseTimestamp>(DIFF_CALLBACK), IModeHelper {

    override var displayMode: DisPlayMode = DisPlayMode.LIST

    override fun getItemViewType(position: Int): Int {
        return when(data[position]){
            is TimeZoneItemBean->{
                VIEW_TYPE_EVENT_ITEM_TIME
            }
            is DayItemBean->{
                VIEW_TYPE_EVENT_ITEM_DATE
            }
            is DataItemBean->{
                if (displayMode.isList()) VIEW_TYPE_EVENT_ITEM else VIEW_TYPE_EVENT_ITEM_GRID
            }
            else->{
                VIEW_TYPE_EVENT_ITEM
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_EVENT_ITEM -> {
                ListViewHolder(parent)
            }
            VIEW_TYPE_EVENT_ITEM_GRID -> {
                GridViewHolder(parent)
            }
            VIEW_TYPE_EVENT_ITEM_TIME -> {
                TimeZoneViewHolder(parent)
            }
            VIEW_TYPE_EVENT_ITEM_DATE -> {
                DateZoneViewHolder(parent)
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
            is TimeZoneViewHolder->{
                holder.bind(data[position] as TimeZoneItemBean)
            }
            is DateZoneViewHolder->{
                holder.bind(data[position] as DayItemBean)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
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
            tvTitle.text ="序号：${info.title} ----- 时间：${TimeUtils.getHourAndMinute(info.timeStamp)}"
        }
    }

    class GridViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_async_test_grid, parent, false)) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        fun bind(info: DataItemBean) {
            tvTitle.text ="序号：${info.title} 时间：${TimeUtils.getHourAndMinute(info.timeStamp)}"
        }
    }

    class TimeZoneViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_async_test_time_zone, parent, false)) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        fun bind(info: TimeZoneItemBean) {
            tvTitle.text =info.title
        }
    }

    class DateZoneViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_async_test_date_zone, parent, false)) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        fun bind(info: DayItemBean) {
            tvTitle.text =info.title
        }
    }


}