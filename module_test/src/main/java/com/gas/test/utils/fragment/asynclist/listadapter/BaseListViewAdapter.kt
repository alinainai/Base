package com.gas.test.utils.fragment.asynclist.listadapter

import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * 通过子线程
 *
 */
abstract class BaseListViewAdapter<VH : RecyclerView.ViewHolder, M> protected constructor(diffCallback: DiffUtil.ItemCallback<M>) : RecyclerView.Adapter<VH>() {

    private val mHelper by lazy { CallBackAsyncListDiffer(AdapterListUpdateCallback(this), CallBackAsyncListDiffer.CallbackAsyncDifferConfig.Builder(diffCallback).build()) }

    val data: List<M>
        get() = mHelper.currentList

    fun getItem(position: Int): M? {
        if (position in data.indices)
            return data[position]
        return null
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun submitList(data: List<M>?, runnable: Runnable) {
        val newList: List<M> = ArrayList(data ?: emptyList())
        mHelper.submitList(newList, runnable)
    }

    fun getItemPos(item: M): Int {
        return data.indexOf(item)
    }


}