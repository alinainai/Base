package com.gas.test.utils.fragment.lazyload.child

import com.gas.test.R
import com.gas.test.utils.lazyload.LazyLoadFragment
import com.lib.commonsdk.kotlin.extension.app.debug

class FFragment : LazyLoadFragment() {

    companion object {
        fun newInstance() = FFragment()
    }

    override fun lazyInit() {
        super.lazyInit()
        debug("FFragment")
    }

    override fun getContentViewId(): Int {
       return R.layout.fragment_text
    }
}