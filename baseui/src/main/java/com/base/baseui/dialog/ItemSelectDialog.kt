package com.base.baseui.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.baseui.R
import com.lib.commonsdk.extension.app.gone
import com.lib.commonsdk.extension.app.visible


class ItemSelectDialog<T : ItemSelectDialog.IItemSelect>(ctx: Context,
                                                         val title: String= "",
                                                         val list: List<T>,
                                                         private val selectKey: String,
                                                         action: (item: T) -> Unit) {

    private val view = LayoutInflater.from(ctx).inflate(R.layout.public_dialig_bottom_select, null)
    private val builder = CommonBottomDialog.Builder()
    private var dialog: Dialog? = null


    init {
        val adapter = SelectItemAdapter<T> { item ->
            view.post {
                dialog?.dismiss()
                action.invoke(item)
            }
        }
        val itemList = view.findViewById<RecyclerView>(R.id.itemList)
        val closeBtn = view.findViewById<View>(R.id.tv_close_btn)
        val dialogTitleDivider = view.findViewById<View>(R.id.dialogTitleDivider)
        val dialogTitle = view.findViewById<TextView>(R.id.dialogTitle)

        if(title.isNotBlank()){
            dialogTitle.text =title
            dialogTitle.visible()
            dialogTitleDivider.visible()
        }else{
            dialogTitle.gone()
            dialogTitleDivider.gone()
        }

        itemList.layoutManager = LinearLayoutManager(ctx)
        itemList.adapter = adapter
        adapter.selectKey = this.selectKey
        adapter.addItems(list)
        closeBtn.setOnClickListener {
            dialog?.dismiss()
        }
        dialog = builder.create(ctx,view)
    }

    fun show(){
        dialog?.show()
    }

    class SelectItemAdapter<T : IItemSelect>(private val action: (item: T) -> Unit) : RecyclerView.Adapter<SelectItemAdapter.ItemSelectViewHolder>() {

        val list: MutableList<T> = mutableListOf()
        var selectKey = "-1"

        fun addItems(list: List<T>) {
            this.list.apply {
                clear()
                addAll(list)
            }
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSelectViewHolder {
            val holder = ItemSelectViewHolder(parent)
            holder.itemView.setOnClickListener {
                val dataPos: Int = holder.adapterPosition
                if (dataPos < list.size && dataPos >= 0) {
                    action.invoke(list[dataPos])
                    selectKey = list[dataPos].key
                    notifyDataSetChanged()
                }
            }
            return holder
        }

        override fun onBindViewHolder(holder: ItemSelectViewHolder, position: Int) {
            list[position].let {
                holder.title.text = it.title ?: ""
                holder.title.isSelected = selectKey == it.key
                holder.selectIcon.visible(selectKey == it.key)
            }
        }

        class ItemSelectViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.public_dialig_bottom_select_item, parent, false)) {
            internal val title: TextView = itemView.findViewById(R.id.title)
            internal val selectIcon: ImageView = itemView.findViewById(R.id.selectIcon)
        }

    }

    interface IItemSelect {
        val title: String
        val key: String
    }
    data class DefaultItemSelect(override val title: String, override val key: String) :IItemSelect

}