package com.base.baseui.dialog.fiterpop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base.baseui.R
import com.base.baseui.dialog.select.ISelectItem



class TagAdapter(private val mList: List<ISelectItem>, private val mListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mSelectId:String="";

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.public_dialog_filter_item, parent, false))
        //在onCreateViewHolder进行点击事件注入，不用放在onBindViewHolder中，会影响性能
        holder.itemView.setOnClickListener(View.OnClickListener {
            val dataPos: Int = holder.adapterPosition
            if (dataPos < mList.size && dataPos >= 0) {
                mList[dataPos].apply {
                    setSelected(id)
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
            }
        }

    }

    fun setSelected(typeId:String){

        mSelectId=typeId;
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    private class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tagView: TextView = itemView.findViewById(R.id.tagView)
    }


    interface OnItemClickListener {
        fun onItemClickListener(item: ISelectItem, position: Int)
    }


}