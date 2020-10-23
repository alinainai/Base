package com.gas.beauty.ui.main;

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
import com.base.paginate.base.SingleAdapter;
import com.base.paginate.interfaces.EmptyInterface;
import com.gas.beauty.R;
import com.gas.beauty.R2;
import com.gas.beauty.ui.main.di.DaggerMainComponent;
import com.gas.beauty.ui.main.mvp.MainContract;
import com.gas.beauty.ui.main.mvp.MainPresenter;
import com.lib.commonsdk.constants.RouterHub;

import javax.inject.Inject;

import butterknife.BindView;

@Route(path = RouterHub.GANK_MAINACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView mRecyclerView;
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
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.gank_activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mRecyclerView = findViewById(R.id.type_item_recyclerview);
        mSwipeRefreshLayout = findViewById(R.id.type_item_swipfreshlayout);
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

        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.requestGirls(true);


    }


    @Override
    public void onRefresh() {
        mPresenter.requestGirls(true);
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


    @Override
    public void showMessage(@NonNull String message) {

    }
}
