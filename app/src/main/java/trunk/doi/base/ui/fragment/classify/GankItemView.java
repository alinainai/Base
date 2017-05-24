package trunk.doi.base.ui.fragment.classify;

import java.util.List;

import trunk.doi.base.base.mvp.IBaseView;


/**
 * Author: Othershe
 * Time: 2016/8/12 14:31
 */
public interface GankItemView extends IBaseView {
    void onSuccess(List<GankItemData> data);
}
