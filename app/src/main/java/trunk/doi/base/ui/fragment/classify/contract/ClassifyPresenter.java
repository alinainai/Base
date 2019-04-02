package trunk.doi.base.ui.fragment.classify.contract;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.rx.RxBindManager;

import java.util.List;

import javax.inject.Inject;

import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.bean.HttpResult;
import trunk.doi.base.https.RxObserver;

@FragmentScope
public class ClassifyPresenter extends BasePresenter<ClassifyModle,ClassifyContract.View> {

    @Inject
    public ClassifyPresenter(ClassifyModle model, ClassifyContract.View rootView) {
        super(model, rootView);
    }

    public void getGankItemData(String suburl) {
       RxBindManager.getInstance().doSubscribe(mModel.getGankItemData(suburl),
               new RxObserver<HttpResult<List<GankItemData>>>(){

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

        }, mView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
