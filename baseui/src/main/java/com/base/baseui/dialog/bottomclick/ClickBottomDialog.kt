package com.base.baseui.dialog.bottomclick

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.IntDef
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.baseui.R
import com.base.baseui.dialog.CommonBottomDialog
import kotlinx.android.synthetic.main.public_dialog_bottom_select.view.*

class ClickBottomDialog {

    private lateinit var mListener: OnItemClickListener
    private lateinit var mList: List<IClickItem>
    private var cancelable = true

    fun setCancelable(isCancelable: Boolean): ClickBottomDialog {
        cancelable = isCancelable
        return this
    }

    fun setOnItemOptionListener(listener: OnItemClickListener): ClickBottomDialog {
        mListener = listener
        return this
    }

    fun setList(list: List<IClickItem>): ClickBottomDialog {
        mList = list
        return this
    }

    fun show(context: Context) {
        if (mList.isEmpty()) {
            return
        }
        val view = LayoutInflater.from(context).inflate(R.layout.public_dialog_bottom_select, null)
        val dialog = CommonBottomDialog.Builder().let { builder ->
            builder.setCancelable(cancelable)
            builder.setDialogClickListener(mListener)
            val recycler: RecyclerView = view.recycler
            recycler.layoutManager = LinearLayoutManager(context)
            recycler.setHasFixedSize(true)
            val adapter = ClickItemAdapter(mList, mListener)
            recycler.adapter = adapter
            builder.setCustomView(view)
            builder.create(context)
        }
        view.dialog_cancel.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    companion object {
        fun getInstance(): ClickBottomDialog {
            return ClickBottomDialog()
        }
    }

}