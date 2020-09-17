package com.gas.test.utils.fragment.lazyload

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.base.baseui.utils.ViewUtils
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.test.R
import com.gas.test.utils.fragment.lazyload.child.*
import com.gas.test.utils.lazyload.LazyLoadPagerAdapter
import com.gas.test.utils.view.tablayoutkt.QMUIBasicTabSegment
import com.gas.test.utils.view.tablayoutkt.QMUITabBuilder
import com.gas.test.utils.view.tablayoutkt.indicator.QMUITabIndicator
import com.gas.test.utils.view.tablayoutkt.QMUITabSegment2
import com.gas.test.utils.view.tablayoutkt.utils.TabUtils

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.lib.commonsdk.kotlin.extension.app.debug
import kotlinx.android.synthetic.main.fragment_lazy_load_parent.*

class LazyLoadParentFragment : BaseFragment<IPresenter>() {


    private val titles = mutableListOf<String>()
    private val fragments = mutableListOf<Fragment>()
    private var mAdapter: LazyLoadPagerAdapter? = null


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

        val tabBuilder: QMUITabBuilder = tabSegment.tabBuilder()
        tabBuilder.setTextSize(
                TabUtils.dpToPx(context, 14),
                TabUtils.dpToPx(context, 18))
        tabSegment.setSelectNoAnimation(true)
//        tabBuilder.setTypeface(Typeface.DEFAULT,Typeface.DEFAULT_BOLD)
        tabBuilder.setNormalColor(Color.parseColor("#2B2D2F"))
        tabBuilder.setSelectColor(Color.parseColor("#2B2D2F"))
        for (i in 0 until titles.size) {
            tabSegment.addTab(tabBuilder.setText(titles[i]).build(context))
        }
        val space: Int = TabUtils.dpToPx(context, 16)
        tabSegment.setIndicator(QMUITabIndicator(
                TabUtils.dpToPx(context, 2), false, true))
        tabSegment.setMode(QMUITabSegment2.MODE_SCROLLABLE)
        tabSegment.setItemSpaceInScrollMode(space)

        tabSegment.setPadding(space, 0, space, 0)
        tabSegment.addOnTabSelectedListener(object : QMUIBasicTabSegment.OnTabSelectedListener {

            override  fun onTabSelected(index: Int) {
                Toast.makeText(context, "select index $index", Toast.LENGTH_SHORT).show()
            }

            override   fun onTabUnselected(index: Int) {
                Toast.makeText(context, "unSelect index $index", Toast.LENGTH_SHORT).show()
            }

            override   fun onTabReselected(index: Int) {
                Toast.makeText(context, "reSelect index $index", Toast.LENGTH_SHORT).show()
            }

            override  fun onDoubleTap(index: Int) {
                Toast.makeText(context, "double tap index $index", Toast.LENGTH_SHORT).show()
            }

        })


//        tabLayout.setupWithViewPager(viewPager)
        mAdapter = LazyLoadPagerAdapter(activity!!)

        viewPager.adapter = mAdapter
        viewPager.offscreenPageLimit = fragments.size - 1
        mAdapter?.setFragment(fragments)
        tabSegment.setupWithViewPager(viewPager)
        TabLayoutMediator(tabLayout, viewPager, object : TabConfigurationStrategy {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = titles[position]
            }
        }).attach()
//        ViewUtils.setIndicatorWidth(tabLayout, 10)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                debug("position=${position}")
                val tabCount = tabLayout.tabCount
                for (i in 0 until tabCount) {

                }
            }
        })

    }

    override fun setData(data: Any?) {
    }

}