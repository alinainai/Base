package com.gas.beauty.ui.article.classify.mvp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;
import com.gas.beauty.bean.GankBaseResponse;
import com.gas.beauty.bean.GankItemBean;
import com.gas.beauty.http.CommonCache;
import com.gas.beauty.http.GankService;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;
import timber.log.Timber;


@FragmentScope
public class ClassifyModle extends BaseModel implements ClassifyContract.Model {

    @Inject
    public ClassifyModle(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<List<GankItemBean>> getGankItemData(String suburl) {

        return Observable.just(mRepositoryManager.obtainRetrofitService(GankService.class).getGankItemData(suburl).map(GankBaseResponse::getResults))
                .flatMap((Function<Observable<List<GankItemBean>>, ObservableSource<List<GankItemBean>>>) httpResultObservable ->
                        mRepositoryManager.obtainCacheService(CommonCache.class).getGankItemData(httpResultObservable, new DynamicKey(suburl), new EvictDynamicKey(true)).map(Reply::getData));
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Timber.d("Release Resource");
    }


}
