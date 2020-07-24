package com.lib.commonsdk.kotlin.extension.app

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.ArrayList

/**
 * Check if the calling context has a set of permissions.
 *
 * @param context the calling context.
 * @param perms   one ore more permissions, such as [Manifest.permission.CAMERA].
 * @return true if all permissions are already granted, false if at least one permission is not
 * yet granted.
 * @see Manifest.permission
 */
fun hasPermissions(context: Context?, vararg perms: String): Boolean {
    // Always return true for SDK < M, let the system deal with the permissions
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        // DANGER ZONE!!! Changing this will break the library.
        return true
    }

    // Null context may be passed if we have detected Low API (less than M) so getting
    // to this point with a null context should not be possible.
    requireNotNull(context) { "Can't check permissions for null context" }
    for (perm in perms) {
        if (ContextCompat.checkSelfPermission(context, perm)
                != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

//第一次进入App时请求权限的方法
fun requestPermissions(context: Activity?, requestCode: Int, vararg permissionNames: String) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return
    }
    val results = ArrayList<String>()
    //已经有的权限不在请求
    for (p in permissionNames) {
        if (ContextCompat.checkSelfPermission(context!!, p)
                != PackageManager.PERMISSION_GRANTED) {
            results.add(p)
        }
    }
    if (results.size > 0) {
        val permissions = arrayOfNulls<String>(results.size)
        for (i in results.indices) {
            permissions[i] = results[i]
        }
        ActivityCompat.requestPermissions(context!!,
                permissions,
                requestCode)
    }
}