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


import com.gas.app.BuildConfig;
import com.lib.commonsdk.utils.GasAppUtils;


public class AppLifecyclesImpl implements AppLifecyclers {

    @Override
    public void attachBaseContext(@NonNull Context base) {
//        MultiDex.install(base);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
    }

    @Override
    public void onCreate(@NonNull Application application) {
        if (!BuildConfig.IS_BUILD_MODULE) {
            GasAppUtils.init(application);
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }


}
