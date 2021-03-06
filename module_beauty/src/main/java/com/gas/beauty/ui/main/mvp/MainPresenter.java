package com.gas.beauty.ui.main.mvp;

import androidx.recyclerview.widget.RecyclerView;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.lib.commonsdk.rx.RxBindManager;
import com.base.paginate.base.SingleAdapter;
import com.base.paginate.interfaces.EmptyInterface;
import com.gas.beauty.bean.GankBaseResponse;
import com.gas.beauty.bean.GankItemBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.gas.beauty.app.GankConstants.NUMBER_OF_PAGE;

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {


    @Inject
    RecyclerView.Adapter mAdapter;
    private int lastPage=1;


    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    public void requestGirls(final boolean pullToRefresh) {
        if (pullToRefresh)
            lastPage = 1;//下拉刷新默认只请求第一页
        final SingleAdapter adapter=((SingleAdapter) mAdapter);
        RxBindManager.getInstance().doSubscribe(mModel.getGirlList(NUMBER_OF_PAGE,lastPage),
                new Observer<GankBaseResponse<List<GankItemBean>>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankBaseResponse<List<GankItemBean>> result) {
                        Timber.e("请求成功success");
                        mView.success();

                        if (null != result && null != result.getResults()&&!result.getResults().isEmpty()) {

                            if (lastPage == 1) {
                                adapter.setNewData(result.getResults());
                            } else {
                                adapter.setLoadMoreData(result.getResults());
                            }
                            if (result.getResults().size() < NUMBER_OF_PAGE) {//如果小于10个
                                adapter.loadEnd();
                            }
                            lastPage++;
                        }else {
                            if (lastPage > 1) {
                                adapter.showFooterFail();
                            } else {
                                adapter.setEmptyView(EmptyInterface.STATUS_FAIL);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
                        if (lastPage > 1) {
                            adapter.showFooterFail();
                        } else {
                            adapter.setEmptyView(EmptyInterface.STATUS_FAIL);
                        }
                    }
                    @Override
                    public void onComplete() {


                    }
                }, mView);



    }



    @Override
    public void onDestroy() {

    }


}
