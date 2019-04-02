package com.base.lib.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.subjects.Subject;
import timber.log.Timber;

/**
 * ================================================
 * 配合 {@link FragmentIRxLifecycle} 使用,使 {@link Fragment} 具有 {@link RxLifecycle} 的特性
 * <p>
 * Created by JessYan on 26/08/2017 16:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Singleton
public class FragmentLifecycleForRxLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    @Inject
    public FragmentLifecycleForRxLifecycle() {
    }

    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm,@NonNull Fragment f,@NonNull Context context) {
        if (f instanceof FragmentIRxLifecycle) {
            obtainSubject(f).onNext(FragmentEvent.ATTACH);
        }
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm,@NonNull Fragment f, Bundle savedInstanceState) {
        if (f instanceof FragmentIRxLifecycle) {
            obtainSubject(f).onNext(FragmentEvent.CREATE);
        }
    }

    @Override
    public void onFragmentViewCreated(@NonNull FragmentManager fm,@NonNull Fragment f,@NonNull View v, Bundle savedInstanceState) {
        if (f instanceof FragmentIRxLifecycle) {
            obtainSubject(f).onNext(FragmentEvent.CREATE_VIEW);
        }
        Timber.tag("FragmentLifecycle").e("onFragmentViewCreated");
    }

    @Override
    public void onFragmentStarted(@NonNull FragmentManager fm,@NonNull Fragment f) {
        if (f instanceof FragmentIRxLifecycle) {
            obtainSubject(f).onNext(FragmentEvent.START);
        }
    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        if (f instanceof FragmentIRxLifecycle) {
            obtainSubject(f).onNext(FragmentEvent.RESUME);
        }
    }

    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm,@NonNull Fragment f) {
        if (f instanceof FragmentIRxLifecycle) {
            obtainSubject(f).onNext(FragmentEvent.PAUSE);
        }
    }

    @Override
    public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
        if (f instanceof FragmentIRxLifecycle) {
            obtainSubject(f).onNext(FragmentEvent.STOP);
        }
    }

    @Override
    public void onFragmentViewDestroyed(@NonNull FragmentManager fm,@NonNull Fragment f) {
        if (f instanceof FragmentIRxLifecycle) {
            obtainSubject(f).onNext(FragmentEvent.DESTROY_VIEW);
        }
    }

    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm,@NonNull Fragment f) {
        if (f instanceof FragmentIRxLifecycle) {
            obtainSubject(f).onNext(FragmentEvent.DESTROY);
        }
    }

    @Override
    public void onFragmentDetached(@NonNull FragmentManager fm,@NonNull Fragment f) {
        if (f instanceof FragmentIRxLifecycle) {
            obtainSubject(f).onNext(FragmentEvent.DETACH);
        }
    }

    private Subject<FragmentEvent> obtainSubject(Fragment fragment) {
        return ((FragmentIRxLifecycle) fragment).provideLifecycleSubject();
    }
}
