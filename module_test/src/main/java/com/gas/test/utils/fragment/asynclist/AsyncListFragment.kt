package com.gas.test.utils.fragment.asynclist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.test.R
import com.gas.test.utils.fragment.asynclist.bean.BaseTimestamp
import com.gas.test.utils.fragment.asynclist.bean.BeanUtils
import com.gas.test.utils.fragment.asynclist.bean.DataItemBean
import com.gas.test.utils.fragment.asynclist.utils.RecyclerScrollHelper
import com.gas.test.utils.fragment.asynclist.utils.RecyclerStickHeaderHelper
import kotlinx.android.synthetic.main.fragment_asynclist.*

class AsyncListFragment : BaseFragment<IPresenter>() {


    private var isList = true
    private val mAdapter = AsyncUpdateDataAdapter()
    private val originData = mutableListOf<DataItemBean>().apply {
        val timeStamp = System.currentTimeMillis()
        for (i in 1..300) {
            add(DataItemBean("title:$i", timeStamp + 3000000 * i))
        }
    }
    private val formatData = mutableListOf<BaseTimestamp>()
    private val scrollHelper by lazy {
        RecyclerScrollHelper(listview)
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_asynclist, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        titleView.setOnBackListener{
            activity?.finish()
        }

        titleView.setOnClickListener {
            scrollHelper.scrollTo(0 )
        }

        imgDisplayType.setOnClickListener{
            if(isList){
                mAdapter.displayMode =  DisPlayMode.GRID
                formatData.clear()
                formatData.addAll(BeanUtils.originToFormat(originData,false))
                mAdapter.submitList(formatData , Runnable { })
                mAdapter.notifyDataSetChanged()
                imgDisplayType.setImageResource(R.drawable.v4_op_mode_list)
                isList=false
            }else{
                mAdapter.displayMode =  DisPlayMode.LIST
                formatData.clear()
                formatData.addAll(BeanUtils.originToFormat(originData,true))
                mAdapter.submitList(formatData , Runnable { })
                mAdapter.notifyDataSetChanged()
                imgDisplayType.setImageResource(R.drawable.v4_op_mode_list)
                isList=true
            }
        }

        listview.apply {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
            adapter = mAdapter
        }
        RecyclerStickHeaderHelper(listview, listDockItem, IModeHelper.VIEW_TYPE_EVENT_ITEM_DATE)
        formatData.addAll(BeanUtils.originToFormat(originData))
        mAdapter.submitList(formatData , Runnable { })
    }



    override fun setData(data: Any?) {
    }




}