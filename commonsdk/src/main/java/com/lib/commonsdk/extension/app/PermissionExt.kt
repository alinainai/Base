package com.lib.commonsdk.extension.app

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.ArrayList

/**
 * Check if the calling context has a set of permissions.
 */
fun hasPermissions(context: Context?, vararg perms: String): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return true
    }
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