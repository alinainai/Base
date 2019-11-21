package trunk.doi.base.ui.fragment.classify.mvp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;
import timber.log.Timber;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.bean.HttpResult;
import trunk.doi.base.https.CommonCache;
import trunk.doi.base.https.GankItemService;

@FragmentScope
public class ClassifyModle extends BaseModel implements ClassifyContract.Model {

    @Inject
    public ClassifyModle(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<List<GankItemData>> getGankItemData(String suburl) {

        return Observable.just(mRepositoryManager.obtainRetrofitService(GankItemService.class).getGankItemData(suburl).map(HttpResult::getResults))
                .flatMap((Function<Observable<List<GankItemData>>, ObservableSource<List<GankItemData>>>) httpResultObservable ->
                        mRepositoryManager.obtainCacheService(CommonCache.class).getGankItemData(httpResultObservable, new DynamicKey(suburl), new EvictDynamicKey(true)).map(Reply::getData));
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Timber.d("Release Resource");
    }


}
