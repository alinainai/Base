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

import io.reactivex.annotations.NonNull;

public class RxBindUtils {

    private RxBindUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 绑定 Activity 的指定生命周期
     *
     * @param view
     * @param event
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull final IView view,
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
     * @param view
     * @param event
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull final IView view,
                                                             final FragmentEvent event) {
        if (view instanceof FragmentIRxLifecycle) {
            return bindUntilEvent((FragmentIRxLifecycle) view, event);
        } else {
            throw new IllegalArgumentException("view isn't FragmentIRxLifecycle");
        }
    }

    public static <T, R> LifecycleTransformer<T> bindUntilEvent(@NonNull final IRxLifecycle<R> lifecycle,
                                                                final R event) {
        return RxLifecycle.bindUntilEvent(lifecycle.provideLifecycleSubject(), event);
    }

    /**
     * 绑定 Activity/Fragment 的生命周期
     *
     * @param view
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull IView view) {
        if (view instanceof IRxLifecycle) {
            return bindToLifecycle((IRxLifecycle) view);
        } else {
            throw new IllegalArgumentException("view isn't IRxLifecycle");
        }
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull IRxLifecycle lifecycle) {
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
    public static <T> LifecycleTransformer<T> bindUntilDestroy(@NonNull final IView view) {

        if (view instanceof ActivityIRxLifecycle) {
            return bindUntilEvent((ActivityIRxLifecycle) view, ActivityEvent.DESTROY);
        } else if (view instanceof FragmentIRxLifecycle) {
            return bindUntilEvent((FragmentIRxLifecycle) view, FragmentEvent.DESTROY);
        } else {
            throw new IllegalArgumentException("view isn't IRxLifecycle");
        }
    }


}
