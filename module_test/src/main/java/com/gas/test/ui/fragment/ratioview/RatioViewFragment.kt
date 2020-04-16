package com.gas.test.ui.fragment.ratioview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.lib.util.Preconditions
import com.gas.test.R
import com.gas.test.ui.fragment.ratioview.di.DaggerRatioViewComponent
import com.gas.test.ui.fragment.ratioview.mvp.RatioViewContract
import com.gas.test.ui.fragment.ratioview.mvp.RatioViewPresenter
import kotlinx.android.synthetic.main.test_fragment_ratio_view.*

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/28/2019 09:58
 * ================================================
 */
class RatioViewFragment : BaseFragment<RatioViewPresenter?>(), RatioViewContract.View {
    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerRatioViewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.test_fragment_ratio_view, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        imgClick.setOnClickListener {
            waveView.setWaveStart(true)
        }

    }


    override fun setData(data: Any?) {

    }
    override fun showMessage(message: String) {
        Preconditions.checkNotNull(message)
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        Preconditions.checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {}

    companion object {
        fun newInstance(): RatioViewFragment {
            return RatioViewFragment()
        }
    }
}