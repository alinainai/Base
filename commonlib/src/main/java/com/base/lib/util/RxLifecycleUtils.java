/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.base.lib.util;


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

/**
 * ================================================
 * 使用此类操作 RxLifecycle 的特性
 * <p>
 * Created by JessYan on 26/08/2017 17:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */

public class RxLifecycleUtils {

    private RxLifecycleUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 绑定 Activity 的指定生命周期
     *
     * @param view  Activity
     * @param event ActivityEvent
     * @param <T>   T
     * @return LifecycleTransformer
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
     * @param view  IView
     * @param event event
     * @param <T>   T
     * @return LifecycleTransformer
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
     * @param view IView
     * @param <T>  T
     * @return LifecycleTransformer
     */
    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull IView view) {
        if (view instanceof IRxLifecycle) {
            return bindToLifecycle((IRxLifecycle) view);
        } else {
            throw new IllegalArgumentException("view isn't IRxLifecycle");
        }
    }

    /**
     * @param lifecycle IRxLifecycle
     * @param <T>       T
     * @return LifecycleTransformer
     */
    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull IRxLifecycle lifecycle) {
        if (lifecycle instanceof ActivityIRxLifecycle) {
            return RxLifecycleAndroid.bindActivity(((ActivityIRxLifecycle) lifecycle).provideLifecycleSubject());
        } else if (lifecycle instanceof FragmentIRxLifecycle) {
            return RxLifecycleAndroid.bindFragment(((FragmentIRxLifecycle) lifecycle).provideLifecycleSubject());
        } else {
            throw new IllegalArgumentException("IRxLifecycle not match");
        }
    }
}
