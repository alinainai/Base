package trunk.doi.base.ui.activity.cache;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import trunk.doi.base.R;
import trunk.doi.base.ui.activity.cache.cacheutils.DiskLruCache;
import trunk.doi.base.util.MD5;


public class DiskLruCacheActivity extends AppCompatActivity {


    private Context mContext;
    private String img_url = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
    private String key = MD5.GetMD5Code(img_url).toLowerCase();
    private DiskLruCache mDiskLruCache;
    private Button btn_save;
    private Button btn_catch;
    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_disk_lru_cache);
//        try {
//            File cacheDir = getDiskCacheDir(mContext, "bitmap");
//            if (!cacheDir.exists()) {
//                cacheDir.mkdirs();
//            }
//            /**
//             * open()方法接收四个参数
//             * 第一个参数指定的是数据的缓存地址，
//             * 第二个参数指定当前应用程序的版本号，
//             * 第三个参数指定同一个key可以对应多少个缓存文件，基本都是传1，
//             * 第四个参数指定最多可以缓存多少字节的数据。
//             */
//            mDiskLruCache = DiskLruCache.open(cacheDir, AppUtils.getVersionCode(mContext), 1, 10 * 1024 * 1024);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_catch = (Button) findViewById(R.id.btn_catch);
        tv_show = (TextView) findViewById(R.id.tv_show);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_catch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mDiskLruCache.flush();
        }catch (IOException e){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mDiskLruCache.close();
        }catch (IOException e){

        }
    }

    /**
     * 写入缓存
     *
     * @param urlString
     * @param outputStream
     * @return
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 可以看到，当SD卡存在或者SD卡不可被移除的时候，
     * 就调用getExternalCacheDir()方法来获取缓存路径，
     * 否则就调用getCacheDir()方法来获取缓存路径。
     * 前者获取到的就是 /sdcard/Android/data/<application package>/cache 这个路径，
     * 而后者获取到的是 /data/data/<application package>/cache 这个路径。
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
