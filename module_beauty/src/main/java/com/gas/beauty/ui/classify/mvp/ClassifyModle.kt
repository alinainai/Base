package com.gas.beauty.ui.classify.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.base.lib.di.scope.FragmentScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import com.gas.beauty.bean.GankResponse
import com.gas.beauty.bean.BeautyBean
import com.gas.beauty.http.CommonCache
import com.gas.beauty.http.GankService
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.Reply
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class ClassifyModle @Inject constructor(repositoryManager: IRepositoryManager?) : BaseModel(repositoryManager), ClassifyContract.Model {
    override fun getGankItemData(suburl: String): Observable<List<BeautyBean>> {
        return Observable.just(mRepositoryManager.obtainRetrofitService(GankService::class.java)
                .getGankItemData(suburl)
                .map<List<BeautyBean>>(GankResponse<List<BeautyBean>>::results))
                .flatMap { httpResultObservable: Observable<List<BeautyBean>> ->
                    mRepositoryManager.obtainCacheService(CommonCache::class.java)
                            .getGankItemData(httpResultObservable, DynamicKey(suburl), EvictDynamicKey(true))
                            .map { obj: Reply<List<BeautyBean>> -> obj.data }
                }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Timber.d("Release Resource")
    }
}