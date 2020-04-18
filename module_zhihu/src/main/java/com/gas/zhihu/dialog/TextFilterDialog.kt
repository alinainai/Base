package com.gas.zhihu.dialog

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.paginate.base.SingleAdapter
import com.base.paginate.viewholder.PageViewHolder
import com.gas.zhihu.R
import kotlinx.android.synthetic.main.zhihu_dialog_text_filter.view.*


class TextFilterDialog {

    private var window: PopupWindow? = null
    private var adapter: TextFilterAdapter? = null
    lateinit var mListener:(String)->Unit

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
        adapter = TextFilterAdapter(anchor.context).apply {
            setOnMultiItemClickListener { _, data, _, _ ->
                run {
                    mListener.invoke(data)
                }
            }
        }
        view.recycler.adapter = adapter
        view.recycler.layoutManager = LinearLayoutManager(anchor.context)
        if (!strs.isNullOrEmpty()) {
            adapter?.setNewData(strs)
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
        adapter?.setNewData(data)
        if (data.isNullOrEmpty()) {
            window?.dismiss()
        }
    }

    fun setListener(listener: (String) -> Unit){
        this.mListener = listener
    }


    class TextFilterAdapter(context: Context?) : SingleAdapter<String>(context, false, false) {
        override fun convert(holder: PageViewHolder?, data: String?, position: Int) {
            holder?.apply {
                setText(R.id.tvSearchText,data)
            }
        }

        override fun getItemLayoutId(): Int {
            return R.layout.zhihu_item_text_filter
        }

    }

}