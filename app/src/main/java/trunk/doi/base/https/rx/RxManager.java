package trunk.doi.base.https.rx;


import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import trunk.doi.base.https.HttpResult;

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

    public <T> Subscription doSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public <T> Subscription doSubscribe1(Observable<HttpResult<T>> observable, Subscriber<T> subscriber) {
        return observable
                .map(new Func1<HttpResult<T>, T>() {
                    @Override
                    public T call(HttpResult<T> httpResult) {

                        //如果
                        if (httpResult.getCode()!=0000) {


                        }
                        return httpResult.getResults();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
