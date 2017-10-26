package trunk.doi.base.util.glideutils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.File;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;


/**
 * A {@link GlideModule} implementation to replace Glide's default
 * {@link java.net.HttpURLConnection} based {@link com.bumptech.glide.load.model.ModelLoader} with an OkHttp based
 * {@link com.bumptech.glide.load.model.ModelLoader}.
 * <p/>
 * <p>
 * If you're using gradle, you can include this module simply by depending on the aar, the module will be merged
 * in by manifest merger. For other build systems or for more more information, see
 * {@link GlideModule}.
 * </p>
 */
public class SimpleGlideModule implements GlideModule
{
    @Override
    public void applyOptions(final Context context, GlideBuilder builder)
    {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //内存缓存
        MemorySizeCalculator memorySizeCalculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = memorySizeCalculator.getMemoryCacheSize();
        int defalutBitmapPoolSize = memorySizeCalculator.getBitmapPoolSize();
        builder.setMemoryCache(new LruResourceCache((int) (defalutBitmapPoolSize * 1.2)));//内部
        builder.setBitmapPool(new LruBitmapPool((int) (defalutBitmapPoolSize * 1.2)));

        //磁盘缓存
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, 1024 * 1024 * 10));//内部磁盘缓存
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, 10 * 1024 * 1024));//磁盘缓存到外部存储
        //指定缓存目录1
        String downLoadPath = Environment.getDownloadCacheDirectory().getPath();
        builder.setDiskCache(new DiskLruCacheFactory(downLoadPath, defaultMemoryCacheSize));
        //指定缓存目录2
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                File cacheLocation = new File(context.getExternalCacheDir(), "cache_dir");
                cacheLocation.mkdirs();

                return DiskLruCacheWrapper.get(cacheLocation, 1024 * 1024 * 10);
            }
        });
    }

    @Override
    public void registerComponents(Context context, Glide glide)
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .sslSocketFactory(overlockCard().getSocketFactory())
                .hostnameVerifier(new HostnameVerifier()
                {
                    @Override
                    public boolean verify(String hostname, SSLSession session)
                    {
                        return true;
                    }
                });
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(builder.build()));
    }

    /**
     * 忽略所有https证书
     */
    private SSLContext overlockCard()
    {
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager()
        {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws
                    CertificateException
            {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws
                    CertificateException
            {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers()
            {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        }};
        try
        {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext;
        }
        catch (Exception e)
        {
            Log.e(SimpleGlideModule.class.getSimpleName(), "ssl出现异常");
            return null;
        }
    }

//    @Override
//    public void registerComponents(Context context, Glide glide) {
//        glide.register(CustomImageSizeModel.class, InputStream.class,new CustomImageSizeModelFactory());
//    }


}
