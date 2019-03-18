package trunk.doi.base.ui.fragment.classify;

import com.base.lib.base.mvp.IBaseView;

import java.util.List;

import io.reactivex.Observable;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.bean.HttpResult;

public interface IGankItem {

    interface IGankItemModel {
        Observable<HttpResult<List<GankItemData>>> getGankItemData(String suburl);
    }

    interface GankItemView extends IBaseView {
        void onSuccess(List<GankItemData> data);
    }

}
