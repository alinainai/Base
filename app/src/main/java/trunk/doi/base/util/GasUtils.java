package trunk.doi.base.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

/**
 * 跟App相关的辅助类
 */
public class GasUtils {
    private GasUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 解析String 类型的arrays.xml元素
     *
     * @param context
     * @param arrayId
     * @return
     */
    public static List<String> stringArrayToList(Context context, int arrayId) {
        return Arrays.asList(context.getResources().getStringArray(arrayId));
    }

    /**
     * 格式化double类型，使其保留2位小数
     *
     * @param number
     * @return
     */
    public static String formatNumber2(double number) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(number);
    }

    /**
     * 格式化double类型，不保留小数
     *
     * @param number
     * @return
     */
    public static String formatNumber(double number) {
        DecimalFormat df = new DecimalFormat("#,##0");
        return df.format(number);
    }


    /**
     * 手机号校验
     */
    public static final String ISPHONENUM = "^[1][3,4,5,7,8][0-9]{9}$";
    /**
     * 请输入6-20位字母和数字组合的登录密码
     * 密码校验
     */
    public static final String ISPWD = "([0-9](?=[0-9]*?[a-zA-Z])\\w{5,19})|([a-zA-Z](?=[a-zA-Z]*?[0-9])\\w{5,19})";


    /**
     * 由数字、26个英文字母或下划线组成的字符串
     */
    public static final String ISWORD = "^\\\\w+$";

    /**
     * 校验E-Mail 地址
     * 同密码一样，下面是E-mail地址合规性的正则检查语句。
     */
    public static final String ISEMAIL = "[\\\\w!#$%&'*+/=?^_`{|}~-]+(?:\\\\.[\\\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\\\w](?:[\\\\w-]*[\\\\w])?\\\\.)+[\\\\w](?:[\\\\w-]*[\\\\w])?";

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * [获取应用程序版本名称信息]
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取设备ID
     */
    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            TelephonyManager tm = (TelephonyManager) context.getApplicationContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getDeviceId();
            if (!TextUtils.isEmpty(deviceId))
                return deviceId;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String imei = tm.getImei();
                if (!TextUtils.isEmpty(imei))
                    return imei;
                String meid = tm.getMeid();
                return TextUtils.isEmpty(meid) ? "" : meid;
            }
            return tm.getDeviceId();
        }
        return "";

    }


    /**
     * Try to return the absolute file path from the given Uri  兼容了file:///开头的 和 content://开头的情况
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    /**
     * drawable转化为 bitmap
     *
     * @param drawable drawable
     * @return bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 获取屏幕宽度 px
     *
     * @param context Context
     * @return 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度 px
     *
     * @param context Context
     * @return px值
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 将px转换为dp
     *
     * @param px
     * @return
     */
    public static int pxTodp(Context context, float px) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5);
    }

    /**
     * 将dp转换为px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dpTopx(Context context, float dp) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5);
    }

    /**
     * 初始化recyclerView
     *
     * @param recyclerView  RecyclerView
     * @param layoutManager LayoutManager
     */
    public static void initRecycler(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


}
