package trunk.doi.base.ui.fragment.blank;

import java.util.List;

import trunk.doi.base.base.mvp.IBaseView;
import trunk.doi.base.bean.GankItemData;


/**
 * Author: Othershe
 * Time: 2016/8/12 14:31
 */
public interface BlankView extends IBaseView {
    void onSuccess(List<GankItemData> data);
}
