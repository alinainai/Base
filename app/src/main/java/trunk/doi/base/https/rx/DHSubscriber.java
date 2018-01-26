package trunk.doi.base.https.rx;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import trunk.doi.base.bean.HttpResult;
import trunk.doi.base.dialog.QMUITipDialog;
import trunk.doi.base.util.ToastUtil;


public abstract class DHSubscriber<T> implements Observer<HttpResult<T>> {


    private Context mContext;
    private boolean mShowLoad;
    private boolean mShowToast;
    private QMUITipDialog loadDialog;

    public DHSubscriber(Context context, boolean showLoad, boolean showToast) {
        this.mContext = context;
        this.mShowLoad = showLoad;
        this.mShowToast = showToast;
        if (context instanceof Activity) {
            loadDialog = new QMUITipDialog.Builder(context)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("正在加载")
                    .create();
        }

    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mShowLoad) {
            if (loadDialog != null) {
                loadDialog.show();
                ToastUtil.cancel();
            }
        }
    }

    public abstract void _onNext(T t);

    public abstract void _onError(int code);


    /**
     * 完成
     */
    @Override
    public void onComplete() {

        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }

    }

    /**
     * 对错误进行统一处理
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {

        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        String showErrorContent;
        if (e instanceof SocketTimeoutException) {
            showErrorContent = "连接超时,请稍后重试";
        } else if (e instanceof ConnectException) {
            showErrorContent = "网络异常,请稍后重试";
        } else {
            showErrorContent = "网络异常,请稍后重试";
        }
        if (mShowToast) {
            if (!TextUtils.isEmpty(showErrorContent)) {
                ToastUtil.showCustomToast(mContext, showErrorContent, ToastUtil.TOAST_OF_ERROR);
            } else {
                ToastUtil.showCustomToast(mContext, "网络链接失败", ToastUtil.TOAST_OF_ERROR);
            }
        }
        _onError(999);

    }


    @Override
    public void onNext(HttpResult<T> result) {

        if (result != null) {
            if (result.getCode()==200) {//请求正确
                _onNext(result.getResults());
            }else{
                _onError(result.getCode());
                if (mShowToast) {
                    if (!TextUtils.isEmpty(result.getMsg())) {
                        ToastUtil.showCustomToast(mContext, result.getMsg(), ToastUtil.TOAST_OF_ERROR);
                    } else {
                        ToastUtil.showCustomToast(mContext, "数据格式错误", ToastUtil.TOAST_OF_ERROR);
                    }
                }
            }
            }
        }

}
