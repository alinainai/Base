package com.gas.zhihu.fragment.fileselect

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.base.baseui.adapter.ExtendAdapter
import com.base.paginate.viewholder.PageViewHolder
import com.gas.zhihu.R
import com.gas.zhihu.bean.FileItemBean
import com.gas.zhihu.bean.PaperShowBean
import com.gas.zhihu.bean.VoltageLevelBean
import com.gas.zhihu.bean.sizeFormat
import com.lib.commonsdk.utils.FileUtils

class FileItemAdapter(context: Context?) : ExtendAdapter<FileItemBean?>(context) {

    override fun getItemLayoutId(): Int {
        return R.layout.zhihu_item_file_item
    }

    override fun convert(holder: PageViewHolder?, data: FileItemBean?, position: Int) {

        data?.let {
            holder?.apply {
                setText(R.id.tvFileTitle, it.fileName)
                setText(R.id.tvSize,it.sizeFormat() )
                setText(R.id.tvTime,it.time)
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
        }
    }
}