package com.base.lib.di.component;

import android.app.Application;

import com.base.lib.base.delegate.AppDelegate;
import com.base.lib.cache.Cache;
import com.base.lib.di.module.AppModule;
import com.base.lib.di.module.ClientModule;
import com.base.lib.di.module.ConfigModule;
import com.base.lib.https.IRepositoryManager;
import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import io.rx_cache2.internal.RxCache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, ClientModule.class, ConfigModule.class})
public interface AppComponent {

    Application application();
    OkHttpClient okHttpClient();
    File cacheFile();
    Retrofit retrofitProvide();

    Cache<String, Object> extras();
    Cache.Factory cacheFactory();

    /**
     * 用于管理网络请求层, 以及数据缓存层
     *
     * @return {@link IRepositoryManager}
     */
    IRepositoryManager repositoryManager();
    void inject(AppDelegate delegate);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        Builder configModule(ConfigModule configModule);
        AppComponent build();
    }
}
