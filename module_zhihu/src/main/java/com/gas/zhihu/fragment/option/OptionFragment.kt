package com.gas.zhihu.fragment.option

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.IPresenter
import com.base.lib.util.ArmsUtils

import com.gas.zhihu.fragment.option.di.DaggerOptionComponent
import com.gas.zhihu.fragment.option.mvp.OptionContract

import com.gas.zhihu.R
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/10/2020 22:52
 * ================================================
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @FragmentScope(請注意命名空間) class NullObjectPresenterByFragment
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */

@FragmentScope
class NullObjectPresenterByFragment
@Inject constructor() : IPresenter {
    override fun onStart() {
    }
    override fun onDestroy() {
    }
}
class OptionFragment : BaseFragment<NullObjectPresenterByFragment>(), OptionContract.View {
    companion object {
        fun newInstance(): OptionFragment {
            val fragment = OptionFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerOptionComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this)
    }
    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.zhihu_fragment_option, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {

    }


    override fun setData(data: Any?) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }
}
