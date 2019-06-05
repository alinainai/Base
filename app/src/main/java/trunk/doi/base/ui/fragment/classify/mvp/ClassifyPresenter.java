package trunk.doi.base.ui.fragment.classify.mvp;

import android.app.Application;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.rx.RxBindManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import trunk.doi.base.bean.GankItemData;

@FragmentScope
public class ClassifyPresenter extends BasePresenter<ClassifyModle, ClassifyContract.View> {


    @Inject
    Application mApplication;


    @Inject
    public ClassifyPresenter(ClassifyModle model, ClassifyContract.View rootView) {
        super(model, rootView);
    }

    public void getGankItemData(String suburl) {
        RxBindManager.getInstance().doSubscribe(mModel.getGankItemData(suburl),
                new Observer<List<GankItemData>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GankItemData> result) {

                        if (null == result || result.size() == 0) {
                            mView.onError();
                            return;
                        }
                        mView.onSuccess(result);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                }, mView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
