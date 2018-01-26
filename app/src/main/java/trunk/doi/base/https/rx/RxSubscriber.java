package trunk.doi.base.https.rx;

import android.app.Activity;
import android.content.Context;
import android.os.Debug;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;


import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;
import trunk.doi.base.BuildConfig;
import trunk.doi.base.dialog.QMUITipDialog;
import trunk.doi.base.util.ToastUtil;


/**
 * Author: Othershe
 * Time:  2016/8/11 17:45
 */
public abstract class RxSubscriber<T> implements Observer<T> {
    private Context mContext;
    private boolean mShowLoading;//是否显示加载loading
    private boolean mShowToast;//是否显示加载loading
    private QMUITipDialog loadDialog;

    public RxSubscriber(Context context, boolean showLoading,boolean showToast) {
        mContext = context;
        mShowLoading = showLoading;
        mShowToast = showToast;
        if (context instanceof Activity) {
            loadDialog = new QMUITipDialog.Builder(context)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("正在加载")
                    .create();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mShowLoading) {
            if (loadDialog != null) {
                loadDialog.show();
                ToastUtil.cancel();
            }
        }
    }

    /**
     * 完成
     */
    @Override
    public void onComplete() {

        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }

    }


    @Override
    public void onError(Throwable e) {
        String errorMsg="";
        if (e instanceof SocketTimeoutException) {
            errorMsg="连接超时";
        } else if (e instanceof JsonSyntaxException) {
            errorMsg="数据解析错误";
        } else if (e instanceof HttpException) {
            errorMsg="网络链接异常";
        } else {
            errorMsg="连接异常";
        }
        if(!TextUtils.isEmpty(errorMsg)&&mShowToast){
            ToastUtil.show(mContext, errorMsg);
        }
        _onError(999);
        if(BuildConfig.DEBUG){
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
