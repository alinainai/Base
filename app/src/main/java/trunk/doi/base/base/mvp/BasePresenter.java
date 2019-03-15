package trunk.doi.base.base.mvp;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者：李佳星
 *
 */
public class BasePresenter<V> {
    public V mView;
    protected CompositeDisposable mCompositeDisposable;


    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入容器集中处理
    }


    public void attach(V view) {
        mView = view;
    }

    public void detach() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
        mView = null;
        mCompositeDisposable = null;
    }


}