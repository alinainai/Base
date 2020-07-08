package com.gas.app.ui.fragment.mine.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.base.lib.di.scope.FragmentScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class MineModle @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MineContract.Model {
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Timber.d("Release Resource")
    }
}