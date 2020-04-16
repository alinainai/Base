package com.gas.zhihu.dialog

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.PopupWindow
import androidx.recyclerview.widget.DividerItemDecoration
import com.base.paginate.base.SingleAdapter
import com.base.paginate.viewholder.PageViewHolder
import com.gas.zhihu.R
import kotlinx.android.synthetic.main.zhihu_dialog_text_filter.view.*

class TextFilterDialog {

    private var window:PopupWindow?= null
    private var adapter:TextFilterAdapter?= null

    fun show(context: Context?) {

        val view = LayoutInflater.from(context).inflate(R.layout.zhihu_dialog_text_filter, null)
        window = PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            isFocusable = true
            isOutsideTouchable = true
            isTouchable = true
//        animationStyle = R.style.PopupDropDownStyle
            setBackgroundDrawable(ColorDrawable(0x00000000))
        }
        view.recycler.addItemDecoration( DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            setDrawable(view.context.resources.getDrawable(R.drawable.zhihu_inset_text_filter_divider))
        })

    }

    fun isShow():Boolean{
        if(window!=null){
            return  window!!.isShowing
        }
        return false
    }

    fun updateData(data:List<String>){
        adapter?.apply {
            setNewData(data)
        }
    }

    class TextFilterAdapter(context: Context?): SingleAdapter<String>(context, false, false) {
        override fun convert(holder: PageViewHolder?, data: String?, position: Int) {

        }

        override fun getItemLayoutId(): Int {
            return R.layout.zhihu_item_text_filter
        }

    }

}