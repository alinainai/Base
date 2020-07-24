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
package com.gas.zhihu.app

import android.app.Application
import android.content.Context
import com.base.lib.base.delegate.AppLifecyclers
import com.gas.zhihu.BuildConfig
import com.lib.commonsdk.kotlin.utils.AppUtils

/**
 * ================================================
 * 展示 [AppLifecyclers] 的用法
 *
 *
 * Created by JessYan on 04/09/2017 17:12
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class AppLifecyclesImpl : AppLifecyclers {
    override fun attachBaseContext(base: Context) {}
    override fun onCreate(application: Application) {
        //使用 RetrofitUrlManager 切换 BaseUrl
//        RetrofitUrlManager.getInstance().putDomain(Api.ZHIHU_DOMAIN_NAME, Api.ZHIHU_DOMAIN)
        //当所有模块集成到宿主 App 时, 在 App 中已经执行了以下代码
        if (BuildConfig.IS_BUILD_MODULE) {
            AppUtils.init(application)
        }
    }

    override fun onTerminate(application: Application) {}
}