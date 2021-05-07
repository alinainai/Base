package com.gas.beauty.ui.article.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import javax.inject.Inject

@FragmentScope
class ArticlePresenter @Inject constructor(model: ArticleModel, rootView: ArticleContract.View) : BasePresenter<ArticleModel, ArticleContract.View>(model, rootView) {
    override fun onDestroy() {
        super.onDestroy()
    }
}