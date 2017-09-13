package trunk.doi.base.base;

import android.app.Activity;
import android.app.Application;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import trunk.doi.base.util.AppUtils;
import trunk.doi.base.util.SDCardUtils;
import trunk.doi.base.util.SPUtils;
import trunk.doi.base.util.ToastUtils;


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


        //内存泄露检测框架
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        instance = this;//初始化appliacation



        //初始化腾讯x5内核
        // QbSdk 的预加载接口 initX5Environment ，可参考接入示例，第一个参数传入 context，第二个参数传入 callback，不需要 callback 的可以传入 null
        QbSdk.initX5Environment(instance,null);


        //封装阿里热修复
        // initialize最好放在attachBaseContext最前面
        SophixManager.getInstance().setContext(this)
                .setAppVersion(String.valueOf(AppUtils.getVersionName(BaseApplication.instance)))
                .setAesKey(null)
                .setEnableDebug(false) //正式发布该参数必须为false, false会对补丁做签名校验, 否则就可能存在安全漏洞风险
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            ToastUtils.showShort(instance,"补丁加载完成");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
                            // 1.3.2.3 killProcessSafely方法
                            // 可以在PatchLoadStatusListener监听到CODE_LOAD_RELAUNCH后在合适的时机，调用此方法杀死进程。注意，不可以直接Process.killProcess(Process.myPid())来杀进程，
                            // 这样会扰乱Sophix的内部状态。因此如果需要杀死进程，建议使用这个方法，它在内部做一些适当处理后才杀死本进程。

                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                            // SophixManager.getInstance().cleanPatches();
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                    }
                }).initialize();


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
