package com.base.lib.util;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 1、
 2、PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者。
 3、ofType操作符只发射指定类型的数据，其内部就是filter+cast
 */

public class RxBus {

    private static volatile RxBus mInstance;
    private final Subject<Object> bus;

    private RxBus() {
       // Subject同时充当了Observer和Observable的角色，Subject是非线程安全的，要避免该问题，需要将 Subject转换为一个 SerializedSubject，上述RxBus类中把线程非安全的PublishSubject包装成线程安全的Subject。
        bus = PublishSubject.create().toSerialized();
    }

    /**
     * 单例RxBus
     * @return
     */
    public static RxBus getDefault() {
        RxBus rxBus = mInstance;
        if (mInstance == null) {
            synchronized (RxBus.class) {
                rxBus = mInstance;
                if (mInstance == null) {
                    rxBus = new RxBus();
                    mInstance = rxBus;
                }
            }
        }
        return rxBus;
    }

    /**
     * 发送一个新事件
     *
     * @param o
     */
    public void post(Object o) {
        bus.onNext(o);
    }

    /**
     * 返回特定类型的被观察者
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

}
