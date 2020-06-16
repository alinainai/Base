package com.base.baseui.dialog.itempop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base.baseui.R
import com.base.baseui.dialog.select.ISelectItem


class PopItemAdapter(private val mList: List<ISelectItem>, private val mListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mSelectId: String = "";

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.public_dialog_select_item, parent, false))
        //在onCreateViewHolder进行点击事件注入，不用放在onBindViewHolder中，会影响性能
        holder.itemView.setOnClickListener(View.OnClickListener {
            val dataPos: Int = holder.adapterPosition
            if (dataPos < mList.size && dataPos >= 0) {
                mList[dataPos].apply {
                    mListener.onItemClickListener(mList[dataPos], dataPos)
                }
            }
        })
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            mList[position].apply {
                holder.tagView.text = name
                holder.tagView.isSelected = id == mSelectId
                holder.imgSelect.setImageResource(if (id == mSelectId) R.mipmap.baseui_selectpop_item_select else R.drawable.public_default_trans_shape)
            }
        }
    }

    fun setSelected(typeId: String) {
        mSelectId = typeId;
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    private class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tagView: TextView = itemView.findViewById(R.id.tagView)
        var imgSelect: ImageView = itemView.findViewById(R.id.imgSelect)
    }


    interface OnItemClickListener {
        fun onItemClickListener(item: ISelectItem, position: Int)
    }


}