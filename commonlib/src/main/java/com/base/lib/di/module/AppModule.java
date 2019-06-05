package com.base.lib.di.module;

import android.app.Application;

import androidx.fragment.app.FragmentManager;

import com.base.lib.base.delegate.activity.ActivityLifecycle;
import com.base.lib.base.delegate.fragment.FragmentLifecycle;
import com.base.lib.cache.Cache;
import com.base.lib.cache.CacheType;
import com.base.lib.https.IRepositoryManager;
import com.base.lib.https.RepositoryManager;
import com.base.lib.lifecycle.ActivityLifecycleForRxLifecycle;
import com.base.lib.util.AppManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * 提供一些框架必须的实例的 {@link Module}
 * <p>
 * Created by JessYan on 8/4/2016.
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Module
public abstract class AppModule {

    @Binds
    @Named("ActivityLifecycleForRxLifecycle")
    abstract Application.ActivityLifecycleCallbacks bindActivityLifecycleForRxLifecycle(ActivityLifecycleForRxLifecycle activityLifecycleForRxLifecycle);

    @Binds
    abstract IRepositoryManager bindRepositoryManager(RepositoryManager repositoryManager);

    @Singleton
    @Provides
    static Cache<String, Object> provideExtras(Cache.Factory cacheFactory) {
        return cacheFactory.build(CacheType.EXTRAS);
    }

    @Binds
    @Named("ActivityLifecycle")
    abstract Application.ActivityLifecycleCallbacks bindActivityLifecycle(ActivityLifecycle activityLifecycle);

    @Binds
    abstract FragmentManager.FragmentLifecycleCallbacks bindFragmentLifecycle(FragmentLifecycle fragmentLifecycle);

    @Singleton
    @Provides
    static List<FragmentManager.FragmentLifecycleCallbacks> provideFragmentLifecycles() {
        return new ArrayList<>();
    }

    @Singleton
    @Provides
    static AppManager provideAppManager(Application application) {
        return AppManager.getAppManager().init(application);
    }


}
