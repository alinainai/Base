package trunk.doi.base.base;

import android.app.Activity;
import android.app.Application;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import trunk.doi.base.util.SDCardUtils;
import trunk.doi.base.util.SPUtils;


/**
 * Created by ly on 2016/5/27 11:39.
 * Application基类(没写完)
 */
public class BaseApplication extends MultiDexApplication {

    public static BaseApplication instance;

    public static final String AJYFILE= Environment
            .getExternalStorageDirectory()+File.separator+"mackjack"+ File.separator+"httpUrlfile";  //缓存文件夹
    public static final String AJYFILE_IMG= Environment.getExternalStorageDirectory()+File.separator+"mackjack"+ File.separator+"imagefile";  //图片文件夹
    public static final String AJYFILE_LOG= Environment.getExternalStorageDirectory()+File.separator+"mackjack"+ File.separator+"logfile";     //日志文件夹
    public static final String DATABASE= BaseApplication.AJYFILE + "/" + "address.sqlite";     //日志文件夹
    public static final String AJYFILE_LOG_TXT= "file_log_txt.txt";     //日志文件

    private int activityCount;//activity的count数
    public boolean isBackGround=false;//是否在前台

    @Override
    public void onCreate() {
        super.onCreate();

        if (!SDCardUtils.fileIsExists(AJYFILE)) {
            File file = new File(AJYFILE);
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

        instance = this;//初始化appliacation
        //极光推送
      //  JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
       // JPushInterface.init(this);     		// 初始化 JPush


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



}
