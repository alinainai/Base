package trunk.doi.base.ui.fragment.blank;


import java.util.List;

import io.reactivex.Observable;
import trunk.doi.base.bean.BeautyResult;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.https.api.GankItemService;
import trunk.doi.base.https.net.NetManager;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:30
 */
public class BlankModelImpl implements IBlankModel {

    @Override
    public Observable<BeautyResult<List<GankItemData>>> getGankItemData(String suburl) {
        GankItemService service = NetManager.getInstance().create(GankItemService.class);
        return service.getBeautyData(suburl);
    }
}
