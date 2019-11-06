package trunk.doi.base.ui.activity.splash.mvp;

import android.app.Application;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import trunk.doi.base.util.GasUtils;

@ActivityScope
public class SplashPresenter extends BasePresenter<SplashContract.SplashModle, SplashContract.View> {

    @Inject
    Application mApplication;

    private Disposable mDisposable;

    @Inject
    public SplashPresenter(SplashContract.View rootView) {
        super(rootView);
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mDisposable = null;
        super.onDestroy();

    }

    /**
     * 强制跳转到Main
     */
    public void turnToMainForce() {

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mDisposable = null;
        mView.goMainActivity();

    }

    /**
     * 开始倒计时
     */
    public void autoTimeDown() {

        mDisposable = Flowable.intervalRange(0, 4, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> {
                            if (aLong != 3) {
                                mView.showTimeDown(3 - aLong);
                            }
                        }
                )
                .doOnComplete(() -> mView.goMainActivity())
                .subscribe();

    }

    /**
     * 显示版本号
     */
    public void showVersionCode() {
        mView.showVersionCode(GasUtils.getVersionName(mApplication));
    }


}
