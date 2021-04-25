/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lib.commonsdk.core

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.lib.commonsdk.statusbar.StatusBarManager
import timber.log.Timber

//import timber.log.Timber;
/**
 * ================================================
 * Created by JessYan on 02/04/2018 15:15
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {


        //允许使用 Ver_5.0 转换动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //5.0
            activity.window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        }
        // 不需要toolbar
        if (activity is AppCompatActivity) {
            activity.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        //设置状态栏图标黑色模式
        if (StatusBarManager.setStatusBarLightMode(activity)) {
            StatusBarManager.setStatusBarColor(activity, Color.parseColor("#FFFFFF"))
        }
        Timber.i("$activity - onActivityCreated")
    }

    override fun onActivityStarted(activity: Activity) {
        Timber.i("$activity - onActivityStarted")
    }

    override fun onActivityResumed(activity: Activity) {
        Timber.i("$activity - onActivityResumed")
    }

    override fun onActivityPaused(activity: Activity) {
        Timber.i("$activity - onActivityPaused")
    }

    override fun onActivityStopped(activity: Activity) {
        Timber.i("$activity - onActivityStopped")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Timber.i("$activity - onActivitySaveInstanceState")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Timber.i("$activity - onActivityDestroyed")
    }
}