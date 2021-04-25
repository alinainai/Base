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

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.multidex.MultiDex
import butterknife.ButterKnife
import com.alibaba.android.arouter.launcher.ARouter
import com.base.lib.base.delegate.AppLifecyclers
import com.base.lib.di.module.ConfigModule
import com.base.lib.https.log.RequestInterceptor
import com.base.lib.integration.config.ClientConfigModule
import com.google.gson.GsonBuilder
import com.jakewharton.threetenabp.AndroidThreeTen
import com.lib.commonsdk.BuildConfig
import com.lib.commonsdk.glide.GlideImageLoaderStrategy
import com.lib.commonsdk.http.Api
import com.lib.commonsdk.http.SSLSocketClient
import io.rx_cache2.internal.RxCache
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * ================================================
 * App 的全局配置信息在此配置, 需要将此实现类声明到 AndroidManifest 中
 * ConfigModule 的实现类可以有无数多个, 在 Application 中只是注册回调, 并不会影响性能 (多个 ConfigModule 在多 Module 环境下尤为受用)
 * ConfigModule 接口的实现类对象是通过反射生成的, 这里会有些性能损耗
 *
 * @see AppDelegate
 *
 * @see ManifestParser
 *
 * @see [请配合官方 Wiki 文档学习本框架](https://github.com/JessYanCoding/MVPArms/wiki)
 *
 * @see [更新日志, 升级必看!](https://github.com/JessYanCoding/MVPArms/wiki/UpdateLog)
 *
 * @see [常见 Issues, 踩坑必看!](https://github.com/JessYanCoding/MVPArms/wiki/Issues)
 *
 * @see [MVPArms 官方组件化方案 ArmsComponent, 进阶指南!](https://github.com/JessYanCoding/ArmsComponent/wiki)
 * Created by JessYan on 12/04/2017 17:25
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class GlobalConfiguration : ClientConfigModule {
    override fun applyOptions(context: Context, builder: ConfigModule.Builder) {
        if (!BuildConfig.LOG_DEBUG) //Release 时,让框架不再打印 Http 请求和响应的信息
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE)
        builder.baseurl(Api.APP_DOMAIN)
                .globalHttpHandler(GlobalHttpHandlerImpl(context))
                .gsonConfiguration { context1: Context?, gsonBuilder: GsonBuilder ->  //这里可以自己自定义配置Gson的参数
                    gsonBuilder
                            .serializeNulls() //支持序列化null的参数
                            .enableComplexMapKeySerialization() //支持将序列化key为object的map,默认只能序列化key为string的map
                }
                .okhttpConfiguration { context, builder ->
                    builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getTrustManager())
                    builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                    //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl. 详细使用请方法查看 https://github.com/JessYanCoding/RetrofitUrlManager
                    RetrofitUrlManager.getInstance().with(builder)
                }
                .imageLoaderStrategy(GlideImageLoaderStrategy())
                .rxCacheConfiguration { context1: Context?, rxCacheBuilder: RxCache.Builder ->  //这里可以自己自定义配置RxCache的参数
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true)
                    null
                }
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecyclers>) {
        // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(object : AppLifecyclers {
            override fun attachBaseContext(base: Context) {
                MultiDex.install(base)
            }

            override fun onCreate(application: Application) {
                if (BuildConfig.LOG_DEBUG) { //Timber日志打印
                    Timber.plant(DebugTree())
                    ButterKnife.setDebug(true)
                    ARouter.openLog() // 打印日志
                    ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
                    RetrofitUrlManager.getInstance().setDebug(true)
                }
                AndroidThreeTen.init(application)
                ARouter.init(application) // 尽可能早,推荐在Application中初始化
            }

            override fun onTerminate(application: Application) {}
        })
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>) {
        lifecycles.add(ActivityLifecycleCallbacksImpl())
    }

    override fun injectFragmentLifecycle(context: Context, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>) {
        lifecycles.add(FragmentLifecycleCallbacksImpl())
    }
}