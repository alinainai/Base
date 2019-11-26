package com.gas.beauty.article.mvp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;
import com.gas.beauty.article.mvp.ArticleContract;

import javax.inject.Inject;

import timber.log.Timber;

@FragmentScope
public class ArtileModle extends BaseModel implements ArticleContract.Model {

    @Inject
    public ArtileModle(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Timber.d("Release Resource");
    }


}
