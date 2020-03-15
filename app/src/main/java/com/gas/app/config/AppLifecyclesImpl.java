/*
 * Copyright 2017 JessYan
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
package com.gas.app.config;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.base.lib.base.delegate.AppLifecyclers;
import com.base.lib.integration.cache.IntelligentCache;
import com.base.lib.util.ArmsUtils;


import com.gas.app.BuildConfig;


public class AppLifecyclesImpl implements AppLifecyclers {

    @Override
    public void attachBaseContext(@NonNull Context base) {
//        MultiDex.install(base);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
    }

    @Override
    public void onCreate(@NonNull Application application) {

//        if (LeakCanary.isInAnalyzerProcess(application)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        ArmsUtils.obtainAppComponentFromContext(application).extras().put(IntelligentCache.getKeyOfKeep(RefWatcher.class.getName())
//                , BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }


}
