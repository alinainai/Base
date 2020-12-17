package com.gas.test.utils.fragment.lazyload.child

import com.gas.test.R
import com.gas.test.utils.lazyload.LazyLoadFragment
import com.lib.commonsdk.extension.app.debug

class DFragment : LazyLoadFragment() {

    companion object {
        fun newInstance() = DFragment()
    }


    override fun lazyInit() {
        super.lazyInit()
        debug("DFragment")
    }

    override fun getContentViewId(): Int {
       return R.layout.fragment_text
    }
}