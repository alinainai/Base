package trunk.doi.base.ui.fragment.classify.mvp;

import android.content.Context;

import com.base.lib.mvp.IView;

import java.util.List;

import io.reactivex.Observable;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.bean.HttpResult;

public interface ClassifyContract {


    interface View extends IView {
        void onSuccess(List<GankItemData> data);
        Context getWrapContext();
    }

    interface Model {
        Observable<List<GankItemData>> getGankItemData(String suburl);
    }


}
