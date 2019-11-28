package com.gas.beauty.ui.classify;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.lib.base.LazyLoadFragment;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.base.paginate.interfaces.EmptyInterface;
import com.gas.beauty.R;
import com.gas.beauty.R2;
import com.gas.beauty.ui.classify.di.DaggerClassifyComponent;
import com.gas.beauty.ui.classify.mvp.ClassifyContract;
import com.gas.beauty.ui.classify.mvp.ClassifyPresenter;
import com.lib.commonsdk.constants.Constants;
import com.lib.commonsdk.constants.RouterHub;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author:
 * Time: 2016/8/12 14:28
 */
public class ClassifyFragment extends LazyLoadFragment<ClassifyPresenter> implements ClassifyContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String SUB_TYPE = "SUB_TYPE";

    private String mSubtype;//分类


    @BindView(R2.id.type_item_recycler)
    RecyclerView mRecyclerView;
    @BindView(R2.id.type_item_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;//进度条

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    ClassifyAdapter mClassifyAdapter;


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

        DaggerClassifyComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gank_article_item, container, false);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        assert getArguments() != null;
        mSubtype = getArguments().getString(SUB_TYPE);
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        //刷新控件
        mSwipeRefreshLayout.setColorSchemeResources(R.color.public_black);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.public_white));

        //条目点击
        mClassifyAdapter.setOnMultiItemClickListener((viewHolder, data, position, viewType) -> {

                    ARouter.getInstance()
                            .build(RouterHub.APP_WEBVIEWACTIVITY)
                            .withString(Constants.PUBLIC_TITLE, data.getDesc())
                            .withString(Constants.PUBLIC_URL, data.getUrl())
                            .navigation(mContext);

                }
        );
        mClassifyAdapter.setOnReloadListener(() -> {
            mClassifyAdapter.setEmptyView(EmptyInterface.STATUS_LOADING);
            loadData(true);
        });
        mClassifyAdapter.setOnLoadMoreListener(isReload -> loadData(false));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setAdapter(mClassifyAdapter);
        mClassifyAdapter.setEmptyView(EmptyInterface.STATUS_LOADING);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void loadData(boolean refresh) {
        assert mPresenter != null;
        mPresenter.getGankItemData(mSubtype, refresh);
    }


    public static ClassifyFragment newInstance(String subtype) {
        ClassifyFragment fragment = new ClassifyFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SUB_TYPE, subtype);
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void loadEnd() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public Context getWrapContext() {
        return mContext;
    }


    @Override
    protected void lazyLoadData() {
        loadData(true);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
