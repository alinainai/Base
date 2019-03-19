package com.base.lib.https.rx;


import com.base.lib.lifecycle.ActivityLifecycleable;
import com.base.lib.lifecycle.FragmentLifecycleable;
import com.base.lib.lifecycle.Lifecycleable;
import com.base.lib.mvp.IBaseView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


/**
 *
 */
public class RxManager {


    private RxManager() {
    }

    public static RxManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RxManager INSTANCE = new RxManager();
    }

    /**
     * MVP模式使用
     *
     * @param observable Retrofit 返回的observable
     * @param observer   接口处理
     * @param <T>        数据泛型
     */
    public <T> void doSubscribe(Observable<T> observable, Observer<T> observer, IBaseView iView) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilDestroy(iView))
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

    /**
     * 在View结束时
     *
     * @param view View层
     * @param <T>  数据泛型
     * @return LifecycleTransformer返回给RxLifecycle使用
     */
    private <T> LifecycleTransformer<T> bindUntilDestroy(@NonNull final IBaseView view) {

        if (view instanceof ActivityLifecycleable) {
            return bindUntilEvent((ActivityLifecycleable) view, ActivityEvent.DESTROY);
        } else if (view instanceof FragmentLifecycleable) {
            return bindUntilEvent((FragmentLifecycleable) view, FragmentEvent.DESTROY);
        } else {
            throw new IllegalArgumentException("view isn't Lifecycleable");
        }
    }


    private <T, R> LifecycleTransformer<T> bindUntilEvent(@NonNull final Lifecycleable<R> lifecycleable,
                                                          final R event) {
        return RxLifecycle.bindUntilEvent(lifecycleable.provideLifecycleSubject(), event);
    }


}
