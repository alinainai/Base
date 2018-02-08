package trunk.doi.base.base;

import android.app.Activity;
import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import trunk.doi.base.service.LocationService;
import trunk.doi.base.util.CrashHandler;
import trunk.doi.base.util.SDCardUtils;
import trunk.doi.base.util.SPUtils;


/**
 * Created by ly on 2016/5/27 11:39.
 * Application基类(没写完)
 */
public class BaseApplication extends MultiDexApplication {

    private static BaseApplication mInstance;

    public static final String DATAFILE= Environment
            .getExternalStorageDirectory()+File.separator+"mackjack"+ File.separator+"httpUrlfile";  //缓存文件夹
    public static final String AJYFILE_IMG= Environment.getExternalStorageDirectory()+File.separator+"mackjack"+ File.separator+"imagefile";  //图片文件夹
    public static final String AJYFILE_LOG= Environment.getExternalStorageDirectory()+File.separator+"mackjack"+ File.separator+"logfile";     //日志文件夹
    public static final String DATABASE= BaseApplication.DATAFILE + "/" + "address.sqlite";     //日志文件夹
    public static final String AJYFILE_LOG_TXT= "file_log_txt.txt";     //日志文件

    private int activityCount;//activity的count数
    public boolean isBackGround=false;//是否在前台
    //百度定位
    public LocationService locationService;


    @Override
    public void onCreate() {
        super.onCreate();

        if (!SDCardUtils.fileIsExists(DATAFILE)) {
            File file = new File(DATAFILE);
            file.mkdirs();
        }
        if (!SDCardUtils.fileIsExists(AJYFILE_IMG)) {
            File file = new File(AJYFILE_IMG);
            file.mkdirs();
        }
        if (!SDCardUtils.fileIsExists(AJYFILE_LOG)) {
            File file = new File(AJYFILE_LOG);
            file.mkdirs();
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

        //地址
        if (!(new File(DATABASE)).exists()) {
                // 获得封装testDatabase.db文件的InputStream对象
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                        AssetManager asset = getAssets();
                        InputStream is = asset.open("address.sqlite");
                        FileOutputStream fos = new FileOutputStream(DATABASE);
                        byte[] buffer = new byte[1024];
                        int count = 0;
                        // 开始复制testDatabase.db文件
                        while ((count = is.read(buffer)) > 0) {
                            fos.write(buffer, 0, count);
                        }
                        fos.close();
                        is.close();
                        }catch (IOException e){}}
                }).start();
        }


        //内存泄露检测框架
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        mInstance = this;//初始化appliacation


        //初始化腾讯x5内核
        // QbSdk 的预加载接口 initX5Environment ，可参考接入示例，第一个参数传入 context，第二个参数传入 callback，不需要 callback 的可以传入 null
        QbSdk.initX5Environment(mInstance, null);
        //百度地图定位
        locationService = new LocationService(getApplicationContext());


//        CrashHandler.getInstance().init(this);


        /**
         * activity的生命周期方法
         */
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }
            @Override
            public void onActivityStarted(Activity activity) {
                if(isBackGround){
                    if( System.currentTimeMillis()- SPUtils.loadLong(BaseApplication.this,"locktime",0)>1000*10){

//                        if(!"TestActivity".equals(activity.getClass().getSimpleName()) &&
//                                !"SplashActivity".equals(activity.getClass().getSimpleName()) &&
//                                !"LoginActivity".equals(activity.getClass().getSimpleName()) &&
//                                !"RegisterActivity".equals(activity.getClass().getSimpleName()) &&
//                                !"MineDetailActivity".equals(activity.getClass().getSimpleName()) &&
//                                !"GuesterActivity".equals(activity.getClass().getSimpleName())
//                                ){
//                            Intent intent = new Intent(activity, GuesterActivity.class);
//                            intent.putExtra("from",activity.getClass().getSimpleName());
//                            activity.startActivity(intent);
//                            activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
//                        }
                    }
                }
                activityCount++;
                isBackGround=false;
            }
            @Override
            public void onActivityResumed(Activity activity) {

            }
            @Override
            public void onActivityPaused(Activity activity) {

            }
            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                if(0==activityCount){
                    isBackGround=true;
                    SPUtils.saveLong(BaseApplication.this,"locktime",System.currentTimeMillis());
                }
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }
            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    //如果要像微信一样，所有字体都不允许随系统调节而发生大小变化，要怎么办呢？
    // 利用Android的Configuration类中的fontScale属性，其默认值为1，
    // 会随系统调节字体大小而发生变化，如果我们强制让其等于默认值，就可以实现字体不随调节改变，
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    public static Application getInstance() {
        return mInstance;
    }

}
