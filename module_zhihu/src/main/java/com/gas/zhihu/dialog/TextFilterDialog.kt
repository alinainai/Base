package com.gas.zhihu.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.*
import com.gas.zhihu.R
import kotlinx.android.synthetic.main.zhihu_dialog_text_filter.view.*
import kotlinx.android.synthetic.main.zhihu_item_text_filter.view.*


class TextFilterDialog {

    companion object {
        const val NAME_KEY = "NAME_KEY"
    }

    private var window: PopupWindow? = null
    private var adapter: TextFilterAdapter? = null
    private lateinit var mListener: (String) -> Unit

    fun show(anchor: View, strs: List<String>) {

        val view = LayoutInflater.from(anchor.context).inflate(R.layout.zhihu_dialog_text_filter, null)
        window = PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            isFocusable = false
            isOutsideTouchable = true
            isTouchable = true
//        animationStyle = R.style.PopupDropDownStyle
            setBackgroundDrawable(ColorDrawable(0x00000000))
        }
        view.recycler.addItemDecoration(DividerItemDecoration(anchor.context, DividerItemDecoration.VERTICAL).apply {
            setDrawable(anchor.context.resources.getDrawable(R.drawable.zhihu_inset_text_filter_divider))
        })
        adapter = TextFilterAdapter(mListener)
        view.recycler.adapter = adapter
        view.recycler.layoutManager = LinearLayoutManager(anchor.context)
        if (!strs.isNullOrEmpty()) {
            adapter?.submitList(strs)
            window?.showAsDropDown(anchor)
        }
    }

    fun isShow(): Boolean {
        if (window != null) {
            return window!!.isShowing
        }
        return false
    }

    fun dismiss() {
        if (window != null && window!!.isShowing) {
            window!!.dismiss()
        }
    }

    fun updateData(data: List<String>) {
        adapter?.submitList(data)
        if (data.isNullOrEmpty()) {
            window?.dismiss()
        }
    }

    fun setListener(listener: (String) -> Unit) {
        this.mListener = listener
    }


    class TextDiffCallback : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: String, newItem: String): Any? {

            if (oldItem != newItem) {
                val diffBundle = Bundle()
                diffBundle.putString(NAME_KEY, newItem);
                return diffBundle;
            }
            return super.getChangePayload(oldItem, newItem)
        }

    }


    class TextFilterAdapter(var listener: (String) -> Unit) : ListAdapter<String, RecyclerView.ViewHolder>(TextDiffCallback()) {



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.zhihu_item_text_filter, parent, false)
            val holder= ViewHolder(view)
            //在onCreateViewHolder进行点击事件注入，不用放在onBindViewHolder中，会影响性能
            holder.itemView.setOnClickListener{
                val dataPos: Int = holder.adapterPosition
                if(dataPos>=0){
                    listener.invoke(getItem(dataPos))
                }

            }
            return holder;
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is ViewHolder) {
                holder.bind(getItem(position))
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {

            if (payloads.isNullOrEmpty()) {
                onBindViewHolder(holder, position)
            } else {
                if (holder is ViewHolder) {
                    val bundle:Bundle = payloads[0] as Bundle
                    holder.bind(bundle.getString(NAME_KEY,""))
                }
            }
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(text: String) {
                itemView.tvSearchText.text = text
            }
        }

    }


}