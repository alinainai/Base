package com.gas.beauty.ui.classify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.base.LazyLoadFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.paginate.interfaces.EmptyInterface
import com.base.paginate.viewholder.PageViewHolder
import com.gas.beauty.R
import com.gas.beauty.bean.GankItemBean
import com.gas.beauty.ui.classify.di.DaggerClassifyComponent
import com.gas.beauty.ui.classify.mvp.ClassifyContract
import com.gas.beauty.ui.classify.mvp.ClassifyPresenter
import com.lib.commonsdk.constants.Constants
import com.lib.commonsdk.constants.RouterHub
import javax.inject.Inject

/**
 * Author:
 * Time: 2016/8/12 14:28
 */
class ClassifyFragment : LazyLoadFragment<ClassifyPresenter?>(), ClassifyContract.View, SwipeRefreshLayout.OnRefreshListener {
    private var mSubtype //分类
            : String? = null
    var mRecyclerView: RecyclerView? = null
    var mSwipeRefreshLayout //进度条
            : SwipeRefreshLayout? = null

    @Inject
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    @Inject
    lateinit var mClassifyAdapter: ClassifyAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = requireView().findViewById(R.id.type_item_recycler)
        mSwipeRefreshLayout = requireView().findViewById(R.id.type_item_refresh)
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerClassifyComponent
                .builder()
                .appComponent(appComponent)?.view(this)?.build()?.inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.gank_article_item, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mSubtype = requireArguments().getString(SUB_TYPE)
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager)
        //刷新控件
        mSwipeRefreshLayout!!.setColorSchemeResources(R.color.public_black)
        mSwipeRefreshLayout!!.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.public_white))

        //条目点击
        mClassifyAdapter!!.setOnMultiItemClickListener { viewHolder: PageViewHolder?, data: GankItemBean, position: Int, viewType: Int ->
            ARouter.getInstance()
                    .build(RouterHub.APP_WEBVIEWACTIVITY)
                    .withString(Constants.PUBLIC_TITLE, data.desc)
                    .withString(Constants.PUBLIC_URL, data.url)
                    .navigation(mContext)
        }
        mClassifyAdapter!!.setOnReloadListener {
            mClassifyAdapter!!.setEmptyView(EmptyInterface.STATUS_LOADING)
            loadData(true)
        }
        mClassifyAdapter!!.setOnLoadMoreListener { isReload: Boolean -> loadData(false) }
        mSwipeRefreshLayout!!.setOnRefreshListener(this)
        mRecyclerView!!.adapter = mClassifyAdapter
        mClassifyAdapter!!.setEmptyView(EmptyInterface.STATUS_LOADING)
    }

    override fun setData(data: Any?) {}
    private fun loadData(refresh: Boolean) {
        mPresenter!!.getGankItemData(mSubtype, refresh)
    }

    override fun loadEnd() {
        if (mSwipeRefreshLayout!!.isRefreshing) {
            mSwipeRefreshLayout!!.isRefreshing = false
        }
    }

    override val wrapContext: Context?
        get() = mContext

    override fun lazyLoadData() {
        loadData(true)
    }

    override fun onRefresh() {
        loadData(true)
    }

    override fun showMessage(message: String) {}

    companion object {
        private const val SUB_TYPE = "SUB_TYPE"
        fun newInstance(subtype: String?): ClassifyFragment {
            val fragment = ClassifyFragment()
            val arguments = Bundle()
            arguments.putString(SUB_TYPE, subtype)
            fragment.arguments = arguments
            return fragment
        }
    }
}