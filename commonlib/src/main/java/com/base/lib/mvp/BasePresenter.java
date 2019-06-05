package com.base.lib.mvp;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.base.lib.util.EventBusManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * ================================================
 * 基类 Presenter
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.4">Presenter wiki 官方文档</a>
 * Created by JessYan on 4/28/2016
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter, LifecycleObserver {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;

    protected M mModel;
    protected V mView;

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     *
     * @param model IModel
     * @param view  IView
     */
    public BasePresenter(@NonNull M model, @NonNull V view) {
        this.mModel = model;
        this.mView = view;
        onStart();
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     *
     * @param view mView
     */
    public BasePresenter(@NonNull V view) {
        this.mView = view;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mView instanceof LifecycleOwner) {
            ((LifecycleOwner) mView).getLifecycle().addObserver(this);
            if (mModel instanceof LifecycleObserver) {
                ((LifecycleOwner) mView).getLifecycle().addObserver((LifecycleObserver) mModel);
            }
        }
        if (useEventBus())//如果要使用 EventBus 请将此方法返回 true
            EventBusManager.getInstance().register(this);//注册 EventBus

    }

    /**
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
     */
    @Override
    public void onDestroy() {
        if (useEventBus())//如果要使用 EventBus 请将此方法返回 true
            EventBusManager.getInstance().unregister(this);//注销 EventBus
        unDispose();//解除订阅
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mView = null;
    }

    public boolean useEventBus() {
        return true;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }

    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入容器集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }

}
