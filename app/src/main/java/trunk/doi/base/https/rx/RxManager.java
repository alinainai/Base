package trunk.doi.base.https.rx;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import trunk.doi.base.base.mvp.IBaseView;

/**
 *
 */
public class RxManager {


    private RxManager() { }

    public static RxManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RxManager INSTANCE = new RxManager();
    }

    /**
     * @param observable
     * @param observer
     * @param <T>
     */
    public <T> void doSubscribe(Observable<T> observable, Observer<T> observer, IBaseView iView)  {
        observable
                .subscribeOn(Schedulers.io())
//                .compose(iView.bindLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }
    /**
     * @param observable
     * @param observer
     * @param <T>
     */
    public <T> void doSubscribe(Observable<T> observable, Observer<T> observer)  {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }


}
