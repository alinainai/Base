package trunk.doi.base.ui.fragment.blank;


import java.util.List;

import io.reactivex.Observable;
import trunk.doi.base.bean.BeautyResult;
import trunk.doi.base.bean.GankItemData;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:30
 */
public interface IBlankModel {
    Observable<BeautyResult<List<GankItemData>>> getGankItemData(String suburl);
}
