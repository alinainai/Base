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

import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.base.lib.base.delegate.AppLifecyclers
import com.base.lib.di.module.ConfigModule
import com.base.lib.integration.config.ClientConfigModule
import com.gas.zhihu.BuildConfig

/**
 * ================================================
 * 组件的全局配置信息在此配置, 需要将此实现类声明到 AndroidManifest 中
 * CommonSDK 中已有 [com.lib.commonsdk.core.GlobalConfiguration] 配置有所有组件都可公用的配置信息
 * 这里用来配置一些组件自身私有的配置信息
 *
 * @see com.base.lib.base.delegate.AppDelegate
 *
 * @see com.base.lib.integration.config.ManifestParser
 *
 * @see [ConfigModule wiki 官方文档](https://github.com/JessYanCoding/ArmsComponent/wiki.3.3)
 * Created by JessYan on 12/04/2017 17:25
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class GlobalConfiguration : ClientConfigModule {
    override fun applyOptions(context: Context, builder: ConfigModule.Builder) {}
    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecyclers>) {
        // AppLifecycles 的所有方法都会在基类 Application 的对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        // 可以根据不同的逻辑添加多个实现类
        lifecycles.add(AppLifecyclesImpl())
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: List<ActivityLifecycleCallbacks>) {}
    override fun injectFragmentLifecycle(context: Context, lifecycles: List<FragmentManager.FragmentLifecycleCallbacks>) {
        //当所有模块集成到宿主 App 时, 在 App 中已经执行了以下代码, 所以不需要再执行
        if (BuildConfig.IS_BUILD_MODULE) {
        }
    }
}