package com.base.lib.base.mvp;



/**
 * 作者：李佳星
 *
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