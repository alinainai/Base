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
package com.base.lib.base.delegate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.base.lib.util.ArmsUtils;
import com.base.lib.util.EventBusManager;
import com.base.lib.util.LogUtils;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * ================================================
 * {@link FragmentDelegate} 默认实现类
 * <p>
 * Created by JessYan on 29/04/2017 16:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class FragmentDelegateImpl implements FragmentDelegate {
    private final String TAG="FragmentDelegateImpl";

    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private IFragment iFragment;
    private Unbinder mBinder;

    public FragmentDelegateImpl(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        this.mFragmentManager = fragmentManager;
        this.mFragment = fragment;
        this.iFragment = (IFragment) fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        LogUtils.debugInfo(TAG,"onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.debugInfo(TAG,"onCreate");
        iFragment.setupFragmentComponent(ArmsUtils.getAppComponent((Objects.requireNonNull(mFragment.getActivity()))));
        if (iFragment.useEventBus())//如果要使用eventbus请将此方法返回true
            EventBusManager.getInstance().register(mFragment);//注册到事件主线
    }

    @Override
    public void onCreateView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtils.debugInfo(TAG,"onCreateView");
        mBinder = ButterKnife.bind(mFragment, view);
        iFragment.initView(view,savedInstanceState);
    }

    @Override
    public void onActivityCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.debugInfo(TAG,"onActivityCreate");
        iFragment.initData(savedInstanceState);
    }

    @Override
    public void onStart() {
        LogUtils.debugInfo(TAG,"onStart");
    }

    @Override
    public void onResume() {
        LogUtils.debugInfo(TAG,"onResume");

    }

    @Override
    public void onPause() {
        LogUtils.debugInfo(TAG,"onPause");

    }

    @Override
    public void onStop() {
        LogUtils.debugInfo(TAG,"onStop");

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onDestroyView() {
        LogUtils.debugInfo(TAG,"onDestroyView");

        if (mBinder != null && mBinder != Unbinder.EMPTY) {
            try {
                mBinder.unbind();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                //fix Bindings already cleared
                Timber.w("onDestroyView: " + e.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        LogUtils.debugInfo(TAG,"onDestroy");
        if (iFragment != null && iFragment.useEventBus())//如果要使用eventbus请将此方法返回true
            EventBusManager.getInstance().unregister(mFragment);//注册到事件主线
        this.mBinder = null;
        this.mFragmentManager = null;
        this.mFragment = null;
        this.iFragment = null;
    }

    @Override
    public void onDetach() {
        LogUtils.debugInfo(TAG,"onDetach");
    }

    /**
     * Return true if the fragment is currently added to its activity.
     */
    @Override
    public boolean isAdded() {
        return mFragment != null && mFragment.isAdded();
    }
}
