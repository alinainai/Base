package com.gas.test.utils.fragment.customview

import android.graphics.Color
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
import com.gas.test.utils.view.AnimationPercentPieView

import kotlinx.android.synthetic.main.fragment_asynclist.*
import kotlinx.android.synthetic.main.fragment_custom_view.*

class CustomViewFragment : BaseFragment<IPresenter>() {



    override fun setupFragmentComponent(appComponent: AppComponent) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_custom_view, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        val arcs = listOf(AnimationPercentPieView.ArcInfo(Color.parseColor("#FFFF0000"),Color.parseColor("#FFFF00FF"),0.25F),
                AnimationPercentPieView.ArcInfo(Color.parseColor("#FF00FF00"),Color.parseColor("#FF00FF00"),0.25F),
                AnimationPercentPieView.ArcInfo(Color.parseColor("#FF0000FF"),Color.parseColor("#FF0000FF"),0.25F),
                AnimationPercentPieView.ArcInfo(Color.parseColor("#FF00FFFF"),Color.parseColor("#FF00FFFF"),0.25F)
        )
        pieView.setData(arcs)

    }



    override fun setData(data: Any?) {
    }

}