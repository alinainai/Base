package com.base.lib.integration.lifecycle;

import androidx.annotation.NonNull;


import io.reactivex.subjects.Subject;

/**
 * 参考 JessYan MvpArms 实现 使用 rxlifecycle2 对 RxJava2进行生命周期解绑
 * 具体方式参考 {@link com.base.lib.util.RxLifecycleUtils}
 *
 * @param <E>
 */
public interface IRxLifecycle<E> {
    @NonNull
    Subject<E> provideLifecycleSubject();
}
