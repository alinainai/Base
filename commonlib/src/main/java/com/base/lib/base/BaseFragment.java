package com.base.lib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.lib.base.delegate.App;
import com.base.lib.base.delegate.fragment.IFragment;
import com.base.lib.cache.Cache;
import com.base.lib.cache.CacheType;
import com.base.lib.di.component.AppComponent;
import com.base.lib.lifecycle.FragmentIRxLifecycle;
import com.base.lib.mvp.IPresenter;
import com.base.lib.util.ArmsUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by
 * fragment基类
 */
public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IFragment, FragmentIRxLifecycle {

    protected final String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;


    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null

    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return lifecycleSubject;
    }



    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.getAppComponent(Objects.requireNonNull(getActivity())).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(initLayoutId(), container, false);
    }


    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
