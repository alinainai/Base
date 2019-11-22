package com.gas.beauty.test;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.base.baseui.base.GasLazyLoadFragment;
import com.base.lib.di.component.AppComponent;
import com.gas.beauty.R;


/**
 * Author:
 * Time: 2016/8/12 14:28
 */
public class ClassifyFragment extends GasLazyLoadFragment<ClassifyPresenter> implements ClassifyContract.View, SwipeRefreshLayout.OnRefreshListener {


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

        DaggerClassifyComponent.builder().appComponent(appComponent).view(this).build();

    }

    @Override
    protected int initLayoutId() {
        return R.layout.gank_activity_main;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


    }


    public static ClassifyFragment newInstance(String subtype) {
        ClassifyFragment fragment = new ClassifyFragment();

        return fragment;
    }


    @Override
    public Context getWrapContext() {
        return mContext;
    }


    @Override
    protected void lazyLoadData() {

    }

    @Override
    public void onRefresh() {

    }


    @Override
    public void showMessage(@NonNull String message) {

    }
}
