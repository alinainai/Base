package trunk.doi.base.ui.fragment.blank;


import java.util.List;

import rx.Observable;
import trunk.doi.base.bean.BeautyResult;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.https.api.BeautyService;
import trunk.doi.base.https.net.NetManager;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:30
 */
public class BlankModelImpl implements IBlankModel {

    @Override
    public Observable<BeautyResult<List<GankItemData>>> getGankItemData(String suburl) {
        BeautyService service = NetManager.getInstance().create(BeautyService.class);
        return service.getBeautyData(suburl);
    }
}
