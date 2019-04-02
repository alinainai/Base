package com.base.lib.lifecycle;

import android.support.annotation.NonNull;

import com.base.lib.rx.RxBindManager;

import io.reactivex.subjects.Subject;

/**
 * 参考 JessYan MvpArms 实现 使用 rxlifecycle2 对 RxJava2进行生命周期解绑
 * 具体方式参考 {@link RxBindManager}
 *
 * @param <E>
 */
public interface IRxLifecycle<E> {
    @NonNull
    Subject<E> provideLifecycleSubject();
}
