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
package trunk.doi.base.config;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.base.lib.base.config.ClientConfigModule;
import com.base.lib.base.delegate.AppLifecyclers;
import com.base.lib.di.module.ConfigModule;
import com.base.lib.https.log.RequestInterceptor;

import java.util.List;

import trunk.doi.base.BuildConfig;


public final class GlobalConfiguration implements ClientConfigModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull ConfigModule.Builder builder) {
        if (!BuildConfig.LOG_DEBUG) { //Release 时, 让框架不再打印 Http 请求和响应的信息
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        }

        builder.gsonConfiguration((context1, gsonBuilder) -> {//这里可以自己自定义配置 Gson 的参数
            gsonBuilder.serializeNulls()//支持序列化值为 null 的参数
                    .enableComplexMapKeySerialization();//支持将序列化 key 为 Object 的 Map, 默认只能序列化 key 为 String 的 Map
        }).okhttpConfiguration((context1, okhttpBuilder) ->
                okhttpBuilder.addInterceptor(OkhttpConfig.getBaseHeader)
                        .hostnameVerifier(OkhttpConfig.notVerifyHostName())
                        .sslSocketFactory(OkhttpConfig.notVerifySSL(), OkhttpConfig.getX509TrustManager())
        ).rxCacheConfiguration((context1, rxCacheBuilder) -> {
            //设置成true,会在数据为空或者发生错误时,忽视EvictProvider为true或者缓存过期的情况,继续使用缓存(前提是之前请求过有缓存)
            rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
            return null;
        });

    }

    @Override
    public void injectAppLifecycle(@NonNull Context context, @NonNull List<AppLifecyclers> lifecycles) {
        //AppLifecycles 中的所有方法都会在基类 Application 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(@NonNull Context context, @NonNull List<Application.ActivityLifecycleCallbacks> lifecycles) {

    }

    @Override
    public void injectFragmentLifecycle(@NonNull Context context, @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        //FragmentLifecycleCallbacks 中的所有方法都会在 Fragment (包括三方库) 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(new FragmentLifecycleCallbacksImpl());
    }
}
