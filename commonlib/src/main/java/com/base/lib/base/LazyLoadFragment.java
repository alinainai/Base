package com.base.lib.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import com.base.lib.mvp.IPresenter;

import java.util.List;

/**
 * 子类覆写{@link LazyLoadFragment}lazyLoadData可快速实现Fragment懒加载
 */
public abstract class LazyLoadFragment<P extends IPresenter> extends BaseFragment<P> {

    protected boolean mIsViewInitiated;
    protected boolean mIsVisibleToUser;
    protected boolean mIsDataInitiated;

    protected abstract void lazyLoadData();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsViewInitiated = true;
        tryLoadData();
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        tryLoadData();
    }


    public void tryLoadData() {
        if (mIsViewInitiated && mIsVisibleToUser && !mIsDataInitiated) {
            lazyLoadData();
            mIsDataInitiated = true;
        }
    }


}
