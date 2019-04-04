package trunk.doi.base.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.base.lib.base.delegate.App;
import com.base.lib.base.delegate.AppDelegate;
import com.base.lib.base.delegate.AppLifecyclers;
import com.base.lib.di.component.AppComponent;
import com.squareup.leakcanary.LeakCanary;

import butterknife.ButterKnife;
import timber.log.Timber;
import trunk.doi.base.BuildConfig;


/**
 * Created by ly on 2016/5/27 11:39.
 * Application基类(没写完)
 */
public class GankApp extends Application implements App {


    private AppLifecyclers mAppDelegate;
    private static GankApp mInstance;

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
        if (BuildConfig.LOG_DEBUG) {//初始化

            Timber.plant(new Timber.DebugTree());
            ButterKnife.setDebug(true);
            //内存泄露检测框架
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);

        }
        mInstance = this;//初始化appliacation
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

    public static Application getInstance() {
        return mInstance;
    }

}
