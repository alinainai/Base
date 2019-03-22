package com.base.lib.rx;


import com.base.lib.mvp.IView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 *
 */
public class RxRetrofitManager {


    private RxRetrofitManager() {
    }

    public static RxRetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RxRetrofitManager INSTANCE = new RxRetrofitManager();
    }

    /**
     * MVP模式使用
     * 在destroy时解绑
     * {@link RxBindUtils#bindUntilDestroy(IView)}
     *
     * @param observable Retrofit 返回的observable
     * @param observer   接口处理
     * @param <T>        数据泛型
     */
    public <T> void doSubscribe(Observable<T> observable, Observer<T> observer, IView iView) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxBindUtils.bindUntilDestroy(iView))
                .subscribe(observer);
    }

    /**
     * 一般模式使用 普通的Activity或者Fragment
     *
     * @param observable Retrofit 返回的observable
     * @param observer   接口处理
     * @param <T>        数据泛型
     *                   获取 Observer提供的Disposable帮助Activity/Fragment取消订阅
     */
    public <T> void doSubscribe(Observable<T> observable, Observer<T> observer) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
