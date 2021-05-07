package com.gas.beauty.ui.article.mvp;

import android.app.Application;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;

import javax.inject.Inject;

@FragmentScope
public class ArticlePresenter extends BasePresenter<ArtileModle, ArticleContract.View> {


    @Inject
    Application mApplication;


    @Inject
    public ArticlePresenter(ArtileModle model, ArticleContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        mApplication = null;
        super.onDestroy();
    }

}
