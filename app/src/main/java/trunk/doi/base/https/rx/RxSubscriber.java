package trunk.doi.base.https.rx;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;
import trunk.doi.base.base.BaseApplication;


/**
 * Author: Othershe
 * Time:  2016/8/11 17:45
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private boolean mIsShowLoading;//是否显示加载loading

    public RxSubscriber(boolean isShowLoading) {
        mContext = BaseApplication.instance;
        mIsShowLoading = isShowLoading;
    }

    @Override
    public void onCompleted() {
        cancelLoading();
    }

    @Override
    public void onError(Throwable e) {

        cancelLoading();
        //统一处理请求异常的情况
        if (e instanceof SocketTimeoutException) {
        } else if (e instanceof ConnectException) {
        } else if (e instanceof IOException) {
            Toast.makeText(mContext, "网络链接异常...", Toast.LENGTH_SHORT).show();
        } else {
            e.printStackTrace();
        }
        _onError();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading();
    }

    /**
     * 可在此处统一显示loading view
     */
    private void showLoading() {
        if (mIsShowLoading) {
        }
    }

    private void cancelLoading() {
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError();

}
