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
import com.gas.test.utils.fragment.asynclist.bean.DataItemBean
import com.gas.test.utils.fragment.asynclist.utils.RecyclerScrollHelper

import kotlinx.android.synthetic.main.fragment_asynclist.*

class AsyncListFragment : BaseFragment<IPresenter>() {


    private val adapter = AsyncUpdateDataAdapter()
    private val data = mutableListOf<DataItemBean>()

    override fun setupFragmentComponent(appComponent: AppComponent) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_asynclist, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        val layoutManager = GridLayoutManager(context, 3)
        listview.layoutManager = layoutManager
        listview.setHasFixedSize(true)
        listview.adapter = adapter
        updateData()
        RecyclerScrollHelper(listview)
        adapter.submitList(data, Runnable { })
    }

    private fun updateData() {

        val timeStamp = System.currentTimeMillis()
        for (i in 1..1000) {
            data.add(DataItemBean("title:$i", timeStamp + 1000 * i))
        }

    }

    override fun setData(data: Any?) {
    }




}