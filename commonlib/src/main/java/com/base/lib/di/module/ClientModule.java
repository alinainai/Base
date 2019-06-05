package com.base.lib.di.module;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.lib.https.GlobalHttpHandler;
import com.base.lib.https.log.RequestInterceptor;
import com.base.lib.util.DataHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ================================================
 * 提供一些三方库客户端实例的 {@link Module}
 * <p>
 * Created by JessYan on 2016/3/14.
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Module
public abstract class ClientModule {

    private static final int TIME_OUT = 10;

    @Singleton
    @Provides
    static Gson provideGson(Application application, @Nullable ClientModule.GsonConfiguration configuration) {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null)
            configuration.configGson(application, builder);
        return builder.create();
    }


    /**
     * @param application   Application
     * @param configuration RetrofitConfiguration
     * @param builder       Retrofit.Builder
     * @param client        OkHttpClient
     * @param baseUrl       baseUrl
     * @param gson          gson
     * @return Retrofit
     */
    @Singleton
    @Provides
    static Retrofit provideRetrofit(Application application,
                                    @Nullable RetrofitConfiguration configuration,
                                    Retrofit.Builder builder,
                                    OkHttpClient client,
                                    String baseUrl,
                                    Gson gson) {
        builder.baseUrl(baseUrl)//域名
                .client(client);//设置 OkHttp

        if (configuration != null)
            configuration.configRetrofit(application, builder);

        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用 RxJava
                .addConverterFactory(GsonConverterFactory.create(gson));//使用 Gson
        return builder.build();
    }

    @Singleton
    @Provides
    static OkHttpClient provideClient(Application application, @Nullable OkhttpConfiguration configuration,
                                      OkHttpClient.Builder builder, Interceptor intercept,
                                      @Nullable List<Interceptor> interceptors, @Nullable GlobalHttpHandler handler,
                                      ExecutorService executorService) {
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(intercept);

        if (handler != null)
            builder.addInterceptor(chain -> chain.proceed(handler.onHttpRequestBefore(chain, chain.request())));

        //如果外部提供了 Interceptor 的集合则遍历添加
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        //为 OkHttp 设置默认的线程池
        builder.dispatcher(new Dispatcher(executorService));
        if (configuration != null)
            configuration.configOkhttp(application, builder);
        return builder.build();
    }

    /**
     * 为okhttp设置打印日志的Interceptor
     *
     * @param interceptor {@link RequestInterceptor}
     * @return Interceptor
     */
    @Binds
    abstract Interceptor bindInterceptor(RequestInterceptor interceptor);

    @Singleton
    @Provides
    static Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    static OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    static RxCache provideRxCache(Application application, @Nullable RxCacheConfiguration configuration
            , @Named("RxCacheDirectory") File cacheDirectory, Gson gson) {
        RxCache.Builder builder = new RxCache.Builder();
        RxCache rxCache = null;
        if (configuration != null) {
            rxCache = configuration.configRxCache(application, builder);
        }
        if (rxCache != null) return rxCache;
        return builder.persistence(cacheDirectory, new GsonSpeaker(gson));
    }


    /**
     * 需要单独给 {@link RxCache} 提供子缓存文件
     *
     * @param cacheDir 框架缓存文件
     * @return {@link File}
     */
    @Singleton
    @Provides
    @Named("RxCacheDirectory")
    static File provideRxCacheDirectory(File cacheDir) {
        File cacheDirectory = new File(cacheDir, "RxCache");
        return DataHelper.makeDirs(cacheDirectory);
    }


    public interface RetrofitConfiguration {
        void configRetrofit(@NonNull Context context, @NonNull Retrofit.Builder builder);
    }

    public interface OkhttpConfiguration {
        void configOkhttp(@NonNull Context context, @NonNull OkHttpClient.Builder builder);
    }


    public interface RxCacheConfiguration {
        RxCache configRxCache(@NonNull Context context, @NonNull RxCache.Builder builder);
    }

    public interface GsonConfiguration {
        void configGson(@NonNull Context context, @NonNull GsonBuilder builder);
    }


}
