package com.gas.zhihu.ui.map

import android.content.Context
import com.base.paginate.base.SingleAdapter
import com.base.paginate.viewholder.PageViewHolder
import com.gas.zhihu.R
import java.util.*

class SearchRecordAdapter(context: Context?) : SingleAdapter<String?>(context, false, false) {

    var data = LinkedList<String>()

    fun addItem(item: String) {
        data.apply {
            if (contains(item)) {
                remove(item)
                addFirst(item)
            } else {
                if (size >= 30) {
                    removeLast()
                    addFirst(item)
                } else {
                    addFirst(item)
                }
            }

        }
        showDataDiff(data as List<String?>?)
    }

    fun setInitData(initData: List<String>) {
        data.apply {
            addAll(initData)
            setNewData(data as List<String?>?)
        }
    }

    fun clearData() {
        data.clear()
        showDataDiff(data as List<String?>?)
    }

    fun onRecordItemClick(position: Int) {
        data.apply {
            val item = get(position)
            removeAt(position)
            addFirst(item)
            showDataDiff(data as List<String?>?)
        }
    }

    fun getRecordData(): List<String> {
        return data
    }

    override fun getItemLayoutId(): Int {
        return R.layout.zhihu_item_search_record
    }

    override fun convert(holder: PageViewHolder?, data: String?, position: Int) {
        holder?.setText(R.id.tvSearchText, data)
    }

    override fun itemsSameCompare(oldItem: String?, newItem: String?): Boolean {
        return oldItem.equals(newItem)
    }

    override fun contentsSameCompare(oldItem: String?, newItem: String?): Boolean {
        return oldItem.equals(newItem)
    }

}