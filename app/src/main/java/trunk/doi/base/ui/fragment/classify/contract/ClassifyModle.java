package trunk.doi.base.ui.fragment.classify.contract;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.di.scope.FragmentScope;
import com.base.lib.https.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.bean.HttpResult;
import trunk.doi.base.https.GankItemService;

@FragmentScope
public class ClassifyModle extends BaseModel implements ClassifyContract.Model {

    @Inject
    public ClassifyModle(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<HttpResult<List<GankItemData>>> getGankItemData(String suburl) {
        return mRepositoryManager
                .obtainRetrofitService(GankItemService.class)
                .getGankItemData(suburl);

//        Observable.just(

//                .flatMap(new Function<Observable<List<User>>, ObservableSource<List<User>>>() {
//                    @Override
//                    public ObservableSource<List<User>> apply(@NonNull Observable<List<User>> listObservable) throws Exception {
//                        return mRepositoryManager.obtainCacheService(CommonCache.class)
//                                .getUsers(listObservable
//                                        , new DynamicKey(lastIdQueried)
//                                        , new EvictDynamicKey(update))
//                                .map(listReply -> listReply.getData());
//                    }
//                });
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Timber.d("Release Resource");
    }


}
