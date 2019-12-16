package com.gas.app.ui.fragment.mine.mvp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import javax.inject.Inject;

import timber.log.Timber;

@FragmentScope
public class MineModle extends BaseModel implements MineContract.Model {

    @Inject
    public MineModle(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Timber.d("Release Resource");
    }



}
