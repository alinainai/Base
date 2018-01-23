package trunk.doi.base.https.rx;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;


import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;
import trunk.doi.base.base.BaseApplication;
import trunk.doi.base.util.ToastUtils;


/**
 * Author: Othershe
 * Time:  2016/8/11 17:45
 */
public abstract class RxSubscriber<T> implements Observer<T> {
    private Context mContext;
    private boolean mIsShowLoading;//是否显示加载loading

    public RxSubscriber(boolean isShowLoading) {
        mContext = BaseApplication.getInstance();
        mIsShowLoading = isShowLoading;
    }

    @Override
    public void onSubscribe(Disposable d) {
        showLoading();
    }

    @Override
    public void onComplete() {
        cancelLoading();
    }

    @Override
    public void onError(Throwable e) {
        cancelLoading();
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            ToastUtils.showShort(mContext, "连接超时...");
        } else if (e instanceof JsonSyntaxException) {
            ToastUtils.showShort(mContext, "数据解析错误...");
        } else if (e instanceof HttpException) {
            ToastUtils.showShort(mContext, TextUtils.isEmpty(e.toString()) ? "网络链接异常..." : e.getMessage());
        } else {
            ToastUtils.showShort(mContext, "连接异常");
        }
        _onError();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
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
