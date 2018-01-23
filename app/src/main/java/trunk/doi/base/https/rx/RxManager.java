package trunk.doi.base.https.rx;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Othershe
 * Time:  2016/8/11 17:53
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
     * @param subscriber
     * @param <T>
     */
    public <T> void doSubscribe(Observable<T> observable, Observer<T> subscriber) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber);
    }


}
