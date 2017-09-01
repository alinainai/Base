package trunk.doi.base.ui.fragment.classify;


import java.util.List;

import rx.Observable;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.bean.HttpResult;
import trunk.doi.base.https.api.GankItemService;
import trunk.doi.base.https.net.NetManager;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:30
 */
public class GankItemModelImpl implements IGankItemModel {

    @Override
    public Observable<HttpResult<List<GankItemData>>> getGankItemData(String suburl) {
        GankItemService service = NetManager.getInstance().create(GankItemService.class);
        return service.getGankItemData(suburl);
    }
}
