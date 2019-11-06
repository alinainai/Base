package com.base.lib.base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.base.lib.base.delegate.App;
import com.base.lib.base.delegate.AppDelegate;
import com.base.lib.base.delegate.AppLifecyclers;
import com.base.lib.di.component.AppComponent;

/**
 * 基于commonlib实现的 {@link Application}
 * <p>
 * 此类中必须进行{@link AppDelegate}的一些操作，如{@link AppDelegate#attachBaseContext(Context)}、
 * {@link AppDelegate#onCreate(Application)}和{@link AppDelegate#onTerminate(Application)}方法。
 * <p>
 * 实现{@link App#getAppComponent()}方法，把{@link AppComponent}暴露出去以供其他类或者对象获取
 * <p>
 */
public class BaseApplication extends Application implements App {

    private AppLifecyclers mAppDelegate;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mAppDelegate == null)
            this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mAppDelegate != null)
            this.mAppDelegate.onCreate(this);


    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        return ((App) mAppDelegate).getAppComponent();
    }


}
