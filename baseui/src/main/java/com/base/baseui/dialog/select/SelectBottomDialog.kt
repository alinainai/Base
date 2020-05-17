package com.base.baseui.dialog.select

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.IntDef
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.baseui.R
import com.base.baseui.dialog.CommonBottomDialog
import com.base.lib.util.Preconditions
import kotlinx.android.synthetic.main.public_dialog_bottom_select.view.*

class SelectBottomDialog private constructor(private val mContext: Context) {
    // 自定义一个注解MyState
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(MODE_CLICK, MODE_SELECT, MODE_CHECK)
    annotation class Mode

    private val mBuilder: CommonBottomDialog.Builder = CommonBottomDialog.Builder(mContext)
    private var mListener: OnItemOperateListener? = null

    @Mode
    private var mMode = MODE_CLICK
    private var mList: List<ISelectItem>? = null
    fun setCancelable(isCancelable: Boolean): SelectBottomDialog {
        Preconditions.checkNotNull(mBuilder, "You should call getInstance first")
        mBuilder.setCancelable(isCancelable)
        return this
    }

    fun setOnItemOptionListener(listener: OnItemOperateListener?): SelectBottomDialog {
        Preconditions.checkNotNull(mBuilder, "You should call getInstance first")
        mBuilder.setDialogClickListener(listener)
        mListener = listener
        return this
    }

    fun setMode(@Mode mode: Int): SelectBottomDialog {
        Preconditions.checkNotNull(mContext, "You should call getInstance first")
        mMode = mode
        return this
    }

    fun setList(list: List<ISelectItem>?): SelectBottomDialog {
        Preconditions.checkNotNull(mContext, "You should call getInstance first")
        mList = list
        return this
    }

    fun show() {
        if (mList == null || mList!!.isEmpty()) {
            return
        }
        Preconditions.checkNotNull(mBuilder, "You should call getInstance first")
        val view = LayoutInflater.from(mContext).inflate(R.layout.public_dialog_bottom_select, null)
        mBuilder.setCustomView(view)
        val dialog = mBuilder.create();
        val recycler: RecyclerView = view.recycler
        view.dialog_cancel.setOnClickListener { dialog.dismiss() }
        recycler.layoutManager = LinearLayoutManager(mContext)
        recycler.setHasFixedSize(true)
        val itemListener = object : SelectItemAdapter.OnItemClickListener {
            override fun onItemClickListener(item: ISelectItem, position: Int) {
                mListener?.apply {

                    when (mMode) {

                        MODE_CLICK -> {
                            mListener?.onItemClickListener(item)
                            dialog.dismiss()
                        }
                        MODE_SELECT -> {

                        }
                        MODE_CHECK -> {

                        }


                    }

                }
            }
        }
        val adapter = SelectItemAdapter(mList, itemListener)
        recycler.adapter = adapter
        dialog.show()

    }

    companion object {
        const val MODE_CLICK = 1
        const val MODE_SELECT = 2
        const val MODE_CHECK = 3
        fun getInstance(context: Context): SelectBottomDialog {
            return SelectBottomDialog(context)
        }
    }

}