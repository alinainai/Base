package trunk.doi.base.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by user on 2017/4/20.
 */

public class PermissionUtils {

//  对应危险权限（即权限组）参考如下：

//    权限组	权限
//    CALENDAR
//            READ_CALENDAR
//            WRITE_CALENDAR
//    CAMERA
//            CAMERA
//    CONTACTS
//            READ_CONTACTS
//            WRITE_CONTACTS
//            GET_ACCOUNTS
//    LOCATION
//            ACCESS_FINE_LOCATION
//            ACCESS_COARSE_LOCATION
//    MICROPHONE
//            RECORD_AUDIO
//    PHONE
//            READ_PHONE_STATE
//            CALL_PHONE
//            READ_CALL_LOG
//            WRITE_CALL_LOG
//            ADD_VOICEMAIL
//            USE_SIP
//            PROCESS_OUTGOING_CALLS
//    SENSORS
//            BODY_SENSORS
//    SMS
//            SEND_SMS
//            RECEIVE_SMS
//            READ_SMS
//            RECEIVE_WAP_PUSH
//            RECEIVE_MMS
//    STORAGE
//            READ_EXTERNAL_STORAGE
//            WRITE_EXTERNAL_STORAGE


    /**
     * Check if the calling context has a set of permissions.
     *
     * @param context the calling context.
     * @param perms   one ore more permissions, such as {@link Manifest.permission#CAMERA}.
     * @return true if all permissions are already granted, false if at least one permission is not
     * yet granted.
     * @see Manifest.permission
     */
    public static boolean hasPermissions(Context context, @NonNull String... perms) {
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // DANGER ZONE!!! Changing this will break the library.
            return true;
        }

        // Null context may be passed if we have detected Low API (less than M) so getting
        // to this point with a null context should not be possible.
        if (context == null) {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }

        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }


    /**
     *  第一次进入App时请求权限的方法
     * @param context
     * @param requestCode 请求吗
     * @param permissionNames 权限
     */
    public static void requestAppPermissions(Activity context, int requestCode,String... permissionNames) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return ;
        }
        ArrayList<String> results = new ArrayList<>();
        //已经有的权限不在请求
        for (String p : permissionNames) {
            if (ContextCompat.checkSelfPermission(context, p)
                    != PackageManager.PERMISSION_GRANTED) {
                results.add(p);
            }
        }

        if(results.size()>0){
            String[] permissions=new String[results.size()];
            for (int i=0;i<results.size();i++){
                permissions[i]=results.get(i);
            }
            ActivityCompat.requestPermissions(context,
                    permissions,
                    requestCode);
        }
    }

    /**
     * 请求设备权限
     * @param activity
     * @param permissions
     * @param requestCode
     */
    public static void requestPermissions(Activity activity, int requestCode ,String... permissions) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // DANGER ZONE!!! Changing this will break the library.
            return ;
        }
        if(activity!=null && permissions!=null && permissions.length>0){
            ActivityCompat.requestPermissions(activity,
                    permissions,
                    requestCode);
        }
    }



}
