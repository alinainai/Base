package com.gas.beauty.ui.main

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.paginate.interfaces.EmptyInterface
import com.gas.beauty.R
import com.gas.beauty.ui.main.di.DaggerMainComponent
import com.gas.beauty.ui.main.mvp.MainContract
import com.gas.beauty.ui.main.mvp.MainPresenter
import com.lib.commonsdk.constants.RouterHub
import javax.inject.Inject

@Route(path = RouterHub.GANK_MAINACTIVITY)
class MainActivity : BaseActivity<MainPresenter>(), MainContract.View, SwipeRefreshLayout.OnRefreshListener {
    lateinit var mRecyclerView: RecyclerView
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    @Inject
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    @Inject
    lateinit var mAdapter: MainAdapter
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)!!
                .view(this)!!
                .build()!!
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.gank_activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
        mRecyclerView = findViewById(R.id.type_item_recyclerview)
        mSwipeRefreshLayout = findViewById(R.id.type_item_swipfreshlayout)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.public_white)
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.public_black))
        mSwipeRefreshLayout.setOnRefreshListener(this)
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager)
        mAdapter.setOnReloadListener {
            mAdapter.setEmptyView(EmptyInterface.STATUS_LOADING)
            mPresenter!!.requestGirls(true)
        }
        mAdapter.setOnLoadMoreListener { isReload: Boolean -> mPresenter!!.requestGirls(false) }
        mAdapter.setEmptyView(EmptyInterface.STATUS_LOADING)
        mRecyclerView.setAdapter(mAdapter)
        mSwipeRefreshLayout.setRefreshing(true)
        mPresenter?.requestGirls(true)
    }

    override fun onRefresh() {
        mPresenter?.requestGirls(true)
    }

    override val activity: Activity
        get() = this

    override fun success() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun onError() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun showMessage(message: String) {}
}