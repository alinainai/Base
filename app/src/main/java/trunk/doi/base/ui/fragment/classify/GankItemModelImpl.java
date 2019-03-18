package trunk.doi.base.ui.fragment.classify;

import com.base.lib.https.net.NetManager;

import java.util.List;

import io.reactivex.Observable;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.bean.HttpResult;
import trunk.doi.base.https.GankItemService;

public class GankItemModelImpl implements IGankItem.IGankItemModel {

    @Override
    public Observable<HttpResult<List<GankItemData>>> getGankItemData(String suburl) {
        GankItemService service = NetManager.getInstance().create(GankItemService.class);
        return service.getGankItemData(suburl);
    }
}
