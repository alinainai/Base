package com.gas.test.utils.fragment.lazyload.child

import com.gas.test.R
import com.gas.test.utils.lazyload.LazyLoadFragment
import com.lib.commonsdk.extension.app.debug

class CFragment : LazyLoadFragment() {

    companion object {
        fun newInstance() = CFragment()
    }


    override fun lazyInit() {
        super.lazyInit()
        debug("CFragment")
    }

    override fun getContentViewId(): Int {
       return R.layout.fragment_text
    }
}