package com.base.lib.mvp;

import android.os.Bundle;

import com.base.lib.base.BaseFragment;


/**
 * 继承于 {@link BaseFragment} 并实现MVP架构
 * <p>
 * 通过方法判定
 * setUserVisibleHint
 * onActivityCreated
 * 并实现懒加载功能
 *
 * @param <V> {@link IView}
 * @param <P> {@link BasePresenter}
 */
public abstract class BaseMvpFragment<V, P extends BasePresenter<V>> extends BaseFragment {

    protected P mPresenter;

    protected abstract P initPresenter();

    protected abstract void fetchData();

    protected boolean mIsViewInitiated;
    protected boolean mIsVisibleToUser;
    protected boolean mIsDataInitiated;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        initFetchData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = initPresenter();
        //noinspection unchecked
        mPresenter.attach((V) this);
        mIsViewInitiated = true;
        initFetchData();
    }

    /**
     * 懒加载请求数据的接口
     */
    protected void initFetchData() {
        if (mIsVisibleToUser && mIsViewInitiated && !mIsDataInitiated) {
            fetchData();
            mIsDataInitiated = true;
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }
}