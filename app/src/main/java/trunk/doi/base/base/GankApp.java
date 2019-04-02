package trunk.doi.base.base;

import android.app.Application;

import com.base.lib.base.BaseApp;
import com.squareup.leakcanary.LeakCanary;

import butterknife.ButterKnife;
import timber.log.Timber;
import trunk.doi.base.BuildConfig;


/**
 * Created by ly on 2016/5/27 11:39.
 * Application基类(没写完)
 */
public class GankApp extends BaseApp {

    private static GankApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

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


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }


    public static Application getInstance() {
        return mInstance;
    }

}
