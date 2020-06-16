package com.lib.commonsdk.rx;


import com.base.lib.integration.lifecycle.ActivityIRxLifecycle;
import com.base.lib.integration.lifecycle.FragmentIRxLifecycle;
import com.base.lib.mvp.IView;
import com.base.lib.util.RxLifecycleUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


/**
 * ================================================
 * desc: RxJava事件解绑工具类
 *
 * created by author ljx
 * Date  2020/3/14
 * email 569932357@qq.com
 *
 * ================================================
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
     * 在View结束时
     *
     * @param view View层
     * @param <T>  数据泛型
     * @return LifecycleTransformer返回给RxLifecycle使用
     */
    public  <T> LifecycleTransformer<T> bindUntilDestroy(@NonNull final IView view) {

        if (view instanceof ActivityIRxLifecycle) {
            return RxLifecycleUtils.bindUntilEvent((ActivityIRxLifecycle) view, ActivityEvent.DESTROY);
        } else if (view instanceof FragmentIRxLifecycle) {
            return RxLifecycleUtils.bindUntilEvent((FragmentIRxLifecycle) view, FragmentEvent.DESTROY);
        } else {
            throw new IllegalArgumentException("view isn't IRxLifecycle");
        }
    }


}
