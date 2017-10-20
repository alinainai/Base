package trunk.doi.base.ui.activity.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 作者：Mr.Lee on 2017-9-25 14:23
 * 邮箱：569932357@qq.com
 */

public class CacheUtils {

    private static LruCache<String, String> mJsonCache= mJsonCache = new LruCache<String, String>(1 * 1024 * 1024);
    private static LruCache<String, Bitmap> mBitmapCache=mBitmapCache = new LruCache<String, Bitmap>(5 * 1024 * 1024);
    int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    public CacheUtils() {
    }

    public static void addJsonLruCache(String key, String value) {
        mJsonCache.put(key, value);
    }

    public static void addBitmapLruCache(String key, Bitmap value) {
        mBitmapCache.put(key, value);
    }

    public static String getJsonLruCache(String key) {
        return mJsonCache.get(key);
    }

    public static Bitmap getBitmapLruCache(String key) {
        return mBitmapCache.get(key);
    }

}
