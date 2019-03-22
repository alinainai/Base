package trunk.doi.base.ui.fragment.classify;

import com.base.lib.mvp.IView;

import java.util.List;

import io.reactivex.Observable;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.bean.HttpResult;

public interface IGankItem {

    interface IGankItemModel {
        Observable<HttpResult<List<GankItemData>>> getGankItemData(String suburl);
    }

    interface GankItemView extends IView {
        void onSuccess(List<GankItemData> data);
    }

}
