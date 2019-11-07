package com.gas.zhihu.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.gas.zhihu.R;
import com.gas.zhihu.main.mvp.MainContract;
import com.gas.zhihu.main.mvp.MainPresenter;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, SwipeRefreshLayout.OnRefreshListener {




    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.zhihu_activity_main;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @Override
    public void onError() {

    }
}
