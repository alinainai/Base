package com.gas.test.utils.fragment.lazyload

import android.R.id.tabs
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.test.R
import com.gas.test.utils.fragment.lazyload.child.*
import com.gas.test.utils.lazyload.LazyLoadPagerAdapter
import com.gas.test.utils.lazyload.LazyStatePageAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_lazy_load_parent.*


class LazyLoadParentFragment : BaseFragment<IPresenter>() {


    private val titles = mutableListOf<String>()
    private val fragments = mutableListOf<Fragment>()
    private var mAdapter: LazyLoadPagerAdapter? = null
    private var mAdapter2: LazyStatePageAdapter? = null


    override fun setupFragmentComponent(appComponent: AppComponent) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_lazy_load_parent, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        fragments.add(AFragment.newInstance())
        fragments.add(BFragment.newInstance())
        fragments.add(CFragment.newInstance())
        fragments.add(DFragment.newInstance())
        fragments.add(EFragment.newInstance())
        fragments.add(FFragment.newInstance())
        titles.add("我家门口大眼")
        titles.add("客厅卫士")
        titles.add("食堂监控")
        titles.add("我家卧室偷窥器")
        titles.add("学校大门口")
        titles.add("餐厅")
        mAdapter2 = LazyStatePageAdapter(fragmentManager!!, fragments, titles)
        viewPager.adapter = mAdapter2
        viewPager.offscreenPageLimit = fragments.size - 1
//        ViewUtils.setIndicatorWidth(tabLayout, 10)
        tabLayout.setupWithViewPager(viewPager)
        initTabView()


//        val tabBuilder: QMUITabBuilder = tabSegment.tabBuilder()
//        tabBuilder.setTextSize(
//                TabUtils.dpToPx(context, 14),
//                TabUtils.dpToPx(context, 18))
//        tabSegment.setSelectNoAnimation(true)
////        tabBuilder.setTypeface(Typeface.DEFAULT,Typeface.DEFAULT_BOLD)
//        tabBuilder.setNormalColor(Color.parseColor("#2B2D2F"))
//        tabBuilder.setSelectColor(Color.parseColor("#2B2D2F"))
//        for (i in 0 until titles.size) {
//            tabSegment.addTab(tabBuilder.setText(titles[i]).build(context))
//        }
//        val space: Int = TabUtils.dpToPx(context, 16)
//        tabSegment.setIndicator(QMUITabIndicator(
//                TabUtils.dpToPx(context, 2), false, true))
//        tabSegment.setMode(QMUITabSegment2.MODE_SCROLLABLE)
//        tabSegment.setItemSpaceInScrollMode(space)
//
//        tabSegment.setPadding(space, 0, space, 0)
//        tabSegment.addOnTabSelectedListener(object : QMUIBasicTabSegment.OnTabSelectedListener {
//
//            override  fun onTabSelected(index: Int) {
//                Toast.makeText(context, "select index $index", Toast.LENGTH_SHORT).show()
//            }
//
//            override   fun onTabUnselected(index: Int) {
//                Toast.makeText(context, "unSelect index $index", Toast.LENGTH_SHORT).show()
//            }
//
//            override   fun onTabReselected(index: Int) {
//                Toast.makeText(context, "reSelect index $index", Toast.LENGTH_SHORT).show()
//            }
//
//            override  fun onDoubleTap(index: Int) {
//                Toast.makeText(context, "double tap index $index", Toast.LENGTH_SHORT).show()
//            }
//
//        })
//
//
////        tabLayout.setupWithViewPager(viewPager)
//        mAdapter = LazyLoadPagerAdapter(activity!!)
//
//        viewPager.adapter = mAdapter
//        viewPager.offscreenPageLimit = fragments.size - 1
//        mAdapter?.setFragment(fragments)
//        tabSegment.setupWithViewPager(viewPager)
//        TabLayoutMediator(tabLayout, viewPager, object : TabConfigurationStrategy {
//            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
//                tab.text = titles[position]
//            }
//        }).attach()
////        ViewUtils.setIndicatorWidth(tabLayout, 10)
//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                debug("position=${position}")
//                val tabCount = tabLayout.tabCount
//                for (i in 0 until tabCount) {
//
//                }
//            }
//        })


    }


    private var holder:ViewHolder?=null

    private fun initTabView() {
        holder = null
        for (i in 0 until titles.size) {
            tabLayout.getTabAt(i)?.let {tab->
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