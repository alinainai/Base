package com.base.lib.rx;


import com.base.lib.lifecycle.ActivityIRxLifecycle;
import com.base.lib.lifecycle.FragmentIRxLifecycle;
import com.base.lib.lifecycle.IRxLifecycle;
import com.base.lib.mvp.IView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


/**
 *
 */
public class RxBindManager {


    private RxBindManager() {
    }

    public static RxBindManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RxBindManager INSTANCE = new RxBindManager();
    }

    /**
     * MVP模式使用
     * 在destroy时解绑
     *
     * @param observable Retrofit 返回的observable
     * @param observer   接口处理
     * @param <T>        数据泛型
     */
    public <T> void doSubscribe(Observable<T> observable, Observer<T> observer, IView iView) {
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
     * 绑定 Activity 的指定生命周期
     *
     * @param view Activity
     * @param event ActivityEvent
     * @param <T> T
     * @return LifecycleTransformer
     */
    public  <T> LifecycleTransformer<T> bindUntilEvent(@NonNull final IView view,
                                                             final ActivityEvent event) {

        if (view instanceof ActivityIRxLifecycle) {
            return bindUntilEvent((ActivityIRxLifecycle) view, event);
        } else {
            throw new IllegalArgumentException("view isn't ActivityIRxLifecycle");
        }
    }

    /**
     * 绑定 Fragment 的指定生命周期
     *
     * @param view IView
     * @param event event
     * @param <T>  T
     * @return LifecycleTransformer
     */
    public  <T> LifecycleTransformer<T> bindUntilEvent(@NonNull final IView view,
                                                             final FragmentEvent event) {
        if (view instanceof FragmentIRxLifecycle) {
            return bindUntilEvent((FragmentIRxLifecycle) view, event);
        } else {
            throw new IllegalArgumentException("view isn't FragmentIRxLifecycle");
        }
    }

    public  <T, R> LifecycleTransformer<T> bindUntilEvent(@NonNull final IRxLifecycle<R> lifecycle,
                                                                final R event) {
        return RxLifecycle.bindUntilEvent(lifecycle.provideLifecycleSubject(), event);
    }

    /**
     * 绑定 Activity/Fragment 的生命周期
     *
     * @param view IView
     * @param <T> T
     * @return LifecycleTransformer
     */
    public  <T> LifecycleTransformer<T> bindToLifecycle(@NonNull IView view) {
        if (view instanceof IRxLifecycle) {
            return bindToLifecycle((IRxLifecycle) view);
        } else {
            throw new IllegalArgumentException("view isn't IRxLifecycle");
        }
    }

    /**
     *
     * @param lifecycle IRxLifecycle
     * @param <T> T
     * @return LifecycleTransformer
     */
    public  <T> LifecycleTransformer<T> bindToLifecycle(@NonNull IRxLifecycle lifecycle) {
        if (lifecycle instanceof ActivityIRxLifecycle) {
            return RxLifecycleAndroid.bindActivity(((ActivityIRxLifecycle) lifecycle).provideLifecycleSubject());
        } else if (lifecycle instanceof FragmentIRxLifecycle) {
            return RxLifecycleAndroid.bindFragment(((FragmentIRxLifecycle) lifecycle).provideLifecycleSubject());
        } else {
            throw new IllegalArgumentException("IRxLifecycle not match");
        }
    }

    /**
     * 在View结束时
     *
     * @param view View层
     * @param <T>  数据泛型
     * @return LifecycleTransformer返回给RxLifecycle使用
     */
    public  <T> LifecycleTransformer<T> bindUntilDestroy(@NonNull final IView view) {

        if (view instanceof ActivityIRxLifecycle) {
            return bindUntilEvent((ActivityIRxLifecycle) view, ActivityEvent.DESTROY);
        } else if (view instanceof FragmentIRxLifecycle) {
            return bindUntilEvent((FragmentIRxLifecycle) view, FragmentEvent.DESTROY);
        } else {
            throw new IllegalArgumentException("view isn't IRxLifecycle");
        }
    }


}
