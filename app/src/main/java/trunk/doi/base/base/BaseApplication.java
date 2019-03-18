package trunk.doi.base.base;

import android.app.Application;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;

import java.io.File;


/**
 * Created by ly on 2016/5/27 11:39.
 * Application基类(没写完)
 */
public class BaseApplication extends MultiDexApplication {

    private static BaseApplication mInstance;

    public static final String DATAFILE = Environment
            .getExternalStorageDirectory() + File.separator + "mackjack" + File.separator + "httpUrlfile";  //缓存文件夹
    public static final String AJYFILE_IMG = Environment.getExternalStorageDirectory() + File.separator + "mackjack" + File.separator + "imagefile";  //图片文件夹
    public static final String AJYFILE_LOG = Environment.getExternalStorageDirectory() + File.separator + "mackjack" + File.separator + "logfile";     //日志文件夹
    public static final String DATABASE = BaseApplication.DATAFILE + "/" + "address.sqlite";     //日志文件夹
    public static final String AJYFILE_LOG_TXT = "file_log_txt.txt";     //日志文件


    @Override
    public void onCreate() {
        super.onCreate();

        //内存泄露检测框架
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
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
