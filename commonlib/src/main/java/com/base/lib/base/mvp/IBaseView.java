package com.base.lib.base.mvp;


/**
 *
 */
public interface IBaseView {
    void onError();
    default void showLoadView(){}
}
