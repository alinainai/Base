package trunk.doi.base.ui.fragment.mine.mvp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.https.IRepositoryManager;
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
import trunk.doi.base.bean.Beauty;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.bean.HttpResult;
import trunk.doi.base.https.CommonCache;
import trunk.doi.base.https.GankItemService;

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


    @Override
    public Beauty getBody() {
        Beauty beauty=new Beauty();
        beauty.setDesc("18519702340");
        return beauty;
    }
}
