package com.gas.beauty.ui.article.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.base.lib.di.scope.FragmentScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class ArticleModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ArticleContract.Model {
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Timber.d("Release Resource")
    }
}