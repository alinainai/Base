package com.gas.test.utils.lazyload

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

open class LazyStatePageAdapterV2(
        fragmentManager: FragmentManager,
        private val fragmentNames: MutableList<Class<out Fragment>>,
        private val titles: MutableList<String>
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return fragmentNames[position].newInstance() //反射加载Fragment
    }

    override fun getCount() = fragmentNames.size

    override fun getPageTitle(position: Int) = titles[position]

}