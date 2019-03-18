package trunk.doi.base.ui.fragment.classify;

import com.base.lib.base.mvp.BasePresenter;
import com.base.lib.https.rx.RxManager;

import java.util.List;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.bean.HttpResult;
import trunk.doi.base.https.RxObserver;

public class GankItemPresenter extends BasePresenter<IGankItem.GankItemView> {

    private IGankItem.IGankItemModel mModel;

    public GankItemPresenter() {
        mModel = new GankItemModelImpl();
    }

    public void getGankItemData(String suburl) {
       RxManager.getInstance().doSubscribe(mModel.getGankItemData(suburl), new RxObserver<HttpResult<List<GankItemData>>>(){


           @Override
           public void _onNext(HttpResult<List<GankItemData>> result) {

               if(null==result||null==result.getResults()){
                   mView.onError();
                   return;
               }
               mView.onSuccess(result.getResults());
           }

           @Override
           public void _onError(int code) {
               mView.onError();
           }

        },mView);
    }

}
