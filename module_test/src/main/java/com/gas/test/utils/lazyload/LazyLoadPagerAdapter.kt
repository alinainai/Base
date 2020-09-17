package com.gas.test.utils.lazyload

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class LazyLoadPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val mFragments= mutableListOf<Fragment>()

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getItemCount(): Int {
        return mFragments.size
    }

   fun setFragment(fragments:List<Fragment>){
       mFragments.clear()
       mFragments.addAll(fragments)
       notifyDataSetChanged()
   }

}
