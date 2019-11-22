package com.gas.beauty.test;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import javax.inject.Inject;

import timber.log.Timber;

@FragmentScope
public class ClassifyModle extends BaseModel implements ClassifyContract.Model {

    @Inject
    public ClassifyModle(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Timber.d("Release Resource");
    }


}
