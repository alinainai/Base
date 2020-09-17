package com.gas.test.utils.fragment.lazyload.child

import com.gas.test.R
import com.gas.test.utils.lazyload.LazyLoadFragment
import com.lib.commonsdk.kotlin.extension.app.debug

class AFragment : LazyLoadFragment() {

    companion object {
        fun newInstance() = AFragment()
    }


    override fun lazyInit() {
        super.lazyInit()
        debug("AFragment")
    }

    override fun getContentViewId(): Int {
       return R.layout.fragment_text
    }
}