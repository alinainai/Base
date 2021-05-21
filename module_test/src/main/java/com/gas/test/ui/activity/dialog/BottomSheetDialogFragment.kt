package com.gas.test.ui.activity.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.baseui.R
import com.base.baseui.dialog.CommonBottomDialog
import com.base.baseui.dialog.ItemSelectDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lib.commonsdk.extension.app.gone
import com.lib.commonsdk.extension.app.visible

class BottomSheetDialogFragment<T : ItemSelectDialog.IItemSelect>(val title: String = "",
                                                                  val list: List<T>,
                                                                  private val selectKey: String,
                                                                  private val action: (item: T) -> Unit) : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.public_dialig_bottom_select, null)
        val builder = CommonBottomDialog.Builder()
        val adapter = ItemSelectDialog.SelectItemAdapter<T> { item ->
            view.post {
                dialog?.dismiss()
                action.invoke(item)
            }
        }
        val itemList = view.findViewById<RecyclerView>(R.id.itemList)
        val closeBtn = view.findViewById<View>(R.id.tv_close_btn)
        val dialogTitleDivider = view.findViewById<View>(R.id.dialogTitleDivider)
        val dialogTitle = view.findViewById<TextView>(R.id.dialogTitle)

        if (title.isNotBlank()) {
            dialogTitle.text = title
            dialogTitle.visible()
            dialogTitleDivider.visible()
        } else {
            dialogTitle.gone()
            dialogTitleDivider.gone()
        }

        itemList.layoutManager = LinearLayoutManager(context)
        itemList.adapter = adapter
        adapter.selectKey = this.selectKey
        adapter.addItems(list)
        closeBtn.setOnClickListener {
            dialog?.dismiss()
        }
        return builder.create(requireContext(), view)
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

    data class DefaultItemSelect(override val title: String, override val key: String) : IItemSelect

}