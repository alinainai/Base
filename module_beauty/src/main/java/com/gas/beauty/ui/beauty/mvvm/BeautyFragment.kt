package com.gas.beauty.ui.beauty.mvvm

import android.os.Bundle
import com.gas.beauty.R
import com.lib.commonsdk.extension.app.debug
import com.lib.commonsdk.mvvm.fragment.BaseVMFragment

class BeautyFragment : BaseVMFragment<BeautyViewModel>() {

    override fun layoutId(): Int {
        return R.layout.gank_fragment_beauty
    }

    override fun initData(savedInstanceState: Bundle?) {
        mViewModel.beautiesLiveData.observe(this){
            debug(it.size)
        }
        mViewModel.getBeauties()
    }

    companion object {
        @JvmStatic
        fun newInstance(): BeautyFragment {
            return BeautyFragment()
        }
    }
}