package com.gas.test.utils.fragment.lazyload.child

import com.gas.test.R
import com.gas.test.utils.lazyload.LazyLoadFragment
import com.lib.commonsdk.kotlin.extension.app.debug

class EFragment : LazyLoadFragment() {

    companion object {
        fun newInstance() = EFragment()
    }


    override fun lazyInit() {
        super.lazyInit()
        debug("EFragment")
    }

    override fun getContentViewId(): Int {
       return R.layout.fragment_text
    }
}