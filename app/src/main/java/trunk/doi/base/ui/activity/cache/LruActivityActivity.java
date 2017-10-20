package trunk.doi.base.ui.activity.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import trunk.doi.base.R;
import trunk.doi.base.util.MD5;

public class LruActivityActivity extends AppCompatActivity {


    private String http="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=527104467,3538968323&fm=27&gp=0.jpg";
    private ImageView img;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_activity);
        img=(ImageView)findViewById(R.id.img_iv);
        tv=(TextView)findViewById(R.id.tv_img);
        Log.e("TAG",MD5.GetMD5Code(http));
        if(CacheUtils.getBitmapLruCache(MD5.GetMD5Code(http))!=null){
            tv.setText("读取缓存");
            img.setImageBitmap(CacheUtils.getBitmapLruCache(MD5.GetMD5Code(http)));
        }else{
            tv.setText("读取网络并缓存");
            new DownLoadBitmap().execute(http);
        }

    }

    /**
     * 根据图片路径下载图片Bitmap
     *
     * @param imageUrl 图片网络路径
     * @return
     */
    public Bitmap getBitmapByImageUrl(String imageUrl) {
        Bitmap bitamp = null;
        HttpURLConnection con = null;
        try {
            URL url = new URL(imageUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(10 * 1000);
            con.setReadTimeout(10 * 1000);
            bitamp = BitmapFactory.decodeStream(con.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return bitamp;
    }

    class DownLoadBitmap extends AsyncTask<String,Integer,Bitmap> {

        private String http_img;
        @Override
        protected Bitmap doInBackground(String... params) {
            http_img=params[0];
            return getBitmapByImageUrl(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                Log.e("TAG",MD5.GetMD5Code(http_img));
                CacheUtils.addBitmapLruCache(MD5.GetMD5Code(http_img),result);
                img.setImageBitmap(result);
            }
        }
    }

}
