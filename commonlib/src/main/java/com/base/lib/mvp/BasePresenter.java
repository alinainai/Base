package com.base.lib.mvp;


/**
 * BasePresenter
 * 绑定View层和解绑View层
 * 在 {@link BaseMvpFragment} 和 {@link BaseMvpActivity}中实现
 * 对 {@link IView} 的 绑定 attach 和 解绑 detach 操作
 */
public class BasePresenter<V> {
    public V mView;

    public void attach(V view) {
        mView = view;
    }

    public void detach() {
        mView = null;
    }


}