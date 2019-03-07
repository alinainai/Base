package trunk.doi.base.https.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import trunk.doi.base.BuildConfig;


/**
 * Author: Othershe
 * Time:  2016/8/11 17:45
 */
public abstract class RxSubscriber<T> implements Observer<T> {


    public RxSubscriber() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    /**
     * 完成
     */
    @Override
    public void onComplete() {


    }


    @Override
    public void onError(Throwable e) {

        _onError(999);
        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }


    protected abstract void _onNext(T t);

    protected abstract void _onError(int code);

}
