package com.gas.zhihu.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.gas.zhihu.R;
import com.gas.zhihu.R2;
import com.gas.zhihu.main.di.DaggerMainComponent;
import com.gas.zhihu.main.mvp.MainContract;
import com.gas.zhihu.main.mvp.MainPresenter;
import com.lib.commonsdk.core.RouterHub;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = RouterHub.ZHIHU_HOMEACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R2.id.type_item_recyclerview)
    RecyclerView typeItemRecyclerview;
    @BindView(R2.id.type_item_swipfreshlayout)
    SwipeRefreshLayout typeItemSwipfreshlayout;

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
