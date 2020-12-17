package com.gas.test.utils.fragment.vp2

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.test.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_lazy_load_viewager2.*


class VP2ParentFragment : BaseFragment<IPresenter>() {


    private val titles = mutableListOf<String>()


    override fun setupFragmentComponent(appComponent: AppComponent) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_lazy_load_viewager2, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {


        titles.add("我家门口大眼")
        titles.add("客厅卫士")
        titles.add("食堂监控")
        titles.add("我家卧室偷窥器")
        titles.add("学校大门口")
        titles.add("餐厅")
//        mAdapter2 = LazyStatePageAdapter(fragmentManager!!, fragments, titles)
//        viewPager.adapter = mAdapter2
//        viewPager.offscreenPageLimit = fragments.size - 1
//        tabLayout.setupWithViewPager(viewPager)
//        initTabView()
//
//
////
////
////
        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy { tab, i ->
            tab.setCustomView(R.layout.tab_item)
            tab.customView?.findViewById<TextView>(R.id.tab_item_title)?.let {
                it.text = titles[i]
                if (i == 0) {
                    it.isSelected = true
                    it.typeface = Typeface.DEFAULT_BOLD
                }
            } }).attach()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.customView?.findViewById<TextView>(R.id.tab_item_title)?.let {
                    it.isSelected = true
                    it.typeface = Typeface.DEFAULT_BOLD
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.customView?.findViewById<TextView>(R.id.tab_item_title)?.let {
                    it.isSelected = false
                    it.typeface = Typeface.DEFAULT
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }
        })


    }


    private var holder: ViewHolder? = null

    private fun initTabView() {
        holder = null
        for (i in 0 until titles.size) {
            tabLayout.getTabAt(i)?.let { tab ->
                //给tab设置自定义布局
                tab.setCustomView(R.layout.tab_item)
                holder = ViewHolder(tab.customView!!)
                //填充数据
                holder!!.mTabItemTitle.setText(titles[i])
                //默认选择第一项
                if (i == 0) {
                    holder!!.mTabItemTitle.setSelected(true)
                    holder!!.mTabItemTitle.setTypeface(Typeface.DEFAULT_BOLD)
                }
            }
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                holder = ViewHolder(tab.customView!!)
                holder!!.mTabItemTitle.setSelected(true)
                holder!!.mTabItemTitle.setTypeface(Typeface.DEFAULT_BOLD)

//                holder!!.mTabItemTitle.setTextSize(18F)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                holder = ViewHolder(tab.customView!!)
                holder!!.mTabItemTitle.setSelected(false)
                holder!!.mTabItemTitle.setTypeface(Typeface.DEFAULT)
                //恢复默认字体大小
//                holder!!.mTabItemTitle.setTextSize(14F)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    internal class ViewHolder(tabView: View) {
        var mTabItemTitle: TextView = tabView.findViewById<View>(R.id.tab_item_title) as TextView
    }

    override fun setData(data: Any?) {
    }

}