package com.base.lib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.lib.lifecycle.FragmentIRxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by
 * fragment基类
 */
public abstract class BaseFragment extends Fragment implements FragmentIRxLifecycle {
    protected Context mContext;
    public View rootView;
    private Unbinder mBinder;
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();
    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return lifecycleSubject;
    }

    protected abstract int initLayoutId();

    public abstract void initView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState);


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(initLayoutId(), container, false);
        mBinder = ButterKnife.bind(this, rootView);
        initView(inflater, container, savedInstanceState);
        return rootView;
    }


    @Override
    public void onDestroy() {
        if (mBinder != null && mBinder != Unbinder.EMPTY)
            mBinder.unbind();
        this.mBinder = null;
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }


    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }


}
