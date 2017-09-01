package trunk.doi.base.ui.fragment.classify;


import java.util.List;

import rx.Observable;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.bean.HttpResult;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:30
 */
public interface IGankItemModel {
    Observable<HttpResult<List<GankItemData>>> getGankItemData(String suburl);
}
