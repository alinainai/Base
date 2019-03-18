package com.base.lib.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.lib.base.BaseActivity;


public abstract class BaseMvpActivity<V, P extends BasePresenter<V>> extends BaseActivity {
    protected P mPresenter;

    protected abstract P initPresenter();

    protected abstract void fetchData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        //noinspection unchecked
        mPresenter.attach((V) this);

        fetchData();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detach();//释放资源
        this.mPresenter = null;
        super.onDestroy();
    }
}
