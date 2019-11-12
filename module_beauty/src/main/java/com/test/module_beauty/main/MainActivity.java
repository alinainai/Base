package com.test.module_beauty.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.base.lib.view.TitleView;
import com.base.paginate.base.SingleAdapter;
import com.base.paginate.interfaces.EmptyInterface;
import com.lib.commonsdk.constants.RouterHub;
import com.test.module_beauty.R;
import com.test.module_beauty.R2;
import com.test.module_beauty.main.di.DaggerMainComponent;
import com.test.module_beauty.main.mvp.MainContract;
import com.test.module_beauty.main.mvp.MainPresenter;

import javax.inject.Inject;

import butterknife.BindView;

@Route(path = RouterHub.GANK_MAINACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R2.id.type_item_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R2.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public int initLayoutId() {
        return R.layout.gank_activity_main;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

        mSwipeRefreshLayout.setColorSchemeResources(R.color.public_white);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.public_black));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);


        final SingleAdapter singleAdapter = ((SingleAdapter) mAdapter);
        singleAdapter.setOnReloadListener(() -> {
            singleAdapter.setEmptyView(EmptyInterface.STATUS_LOADING);
            mPresenter.requestGirls(true);
        });
        singleAdapter.setOnLoadMoreListener(isReload -> mPresenter.requestGirls(false));
        singleAdapter.setEmptyView(EmptyInterface.STATUS_LOADING);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.requestGirls(true);
    }

    @Override
    public void onRefresh() {
        mPresenter.requestGirls(true);
    }

    @Override
    public void getTitleView(TitleView titleView) {
        titleView.setCloseHide(true);
        titleView.setTitleText("干货模块页面");
        titleView.setOnBackListener(v -> finish());
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void success() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError() {
        mSwipeRefreshLayout.setRefreshing(false);
    }


}
