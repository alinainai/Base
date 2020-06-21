package com.gas.zhihu.fragment.paper

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.base.baseui.adapter.ExtendAdapter
import com.base.baseui.view.SwipeMenuLayout
import com.base.paginate.viewholder.PageViewHolder
import com.gas.zhihu.R
import com.gas.zhihu.bean.PaperShowBean
import com.gas.zhihu.bean.VoltageLevelBean

class PaperAdapter(context: Context?, private val openLoadMore: Boolean = true, openEmpty: Boolean = true) : ExtendAdapter<PaperShowBean>(context, openLoadMore, openEmpty) {

    private var mListener: OnPaperOptionListener? = null

    override fun getItemLayoutId(): Int {
        return R.layout.zhihu_item_pager
    }

    override fun convert(holder: PageViewHolder, data: PaperShowBean, position: Int) {

        data.let {
            holder.apply {
                setText(R.id.itemTitle, it.fileName)
                setText(R.id.itemMap, "厂站：${it.mapName}")
                setText(R.id.itemVoltage, "电压等级：${VoltageLevelBean.getVoltageName(it.voltageLevel)}")
                val img = this.getView<ImageView>(R.id.imgFileType)
                it.filePath.lastIndexOf('.').takeIf { it > -1 }?.apply {
                    when (it.filePath.substring(it.filePath.lastIndexOf('.'))) {
                        ".pdf" -> {
                            img.setImageResource(R.mipmap.zhihu_file_type_pdf)
                        }
                        ".docx", ".doc" -> {
                            img.setImageResource(R.mipmap.zhihu_file_type_word)
                        }
                        else -> {
                            img.setImageResource(R.mipmap.zhihu_file_type_unknown)
                        }
                    }
                }
            }
            holder.getView<SwipeMenuLayout>(R.id.event_swipe_layout).isSwipeEnable = openLoadMore
            holder.getView<View>(R.id.paperContent).setOnClickListener {
                mListener?.itemClick(data, position)
            }
            holder.getView<View>(R.id.btnModify).setOnClickListener {
                mListener?.itemModify(data, position)
            }
            holder.getView<View>(R.id.btnDelete).setOnClickListener {
                mListener?.itemDelete(data, position)
            }
        }

    }

    fun setOnPaperOptionListener(listener: OnPaperOptionListener) {
        mListener = listener
    }

    interface OnPaperOptionListener {
        fun itemClick(data: PaperShowBean, position: Int)
        fun itemModify(data: PaperShowBean, position: Int)
        fun itemDelete(data: PaperShowBean, position: Int)
    }

}