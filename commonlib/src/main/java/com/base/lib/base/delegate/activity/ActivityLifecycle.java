package com.base.lib.base.delegate.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.base.lib.base.BaseFragment;
import com.base.lib.base.delegate.fragment.FragmentDelegate;
import com.base.lib.cache.Cache;
import com.base.lib.cache.IntelligentCache;
import com.base.lib.util.AppManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;


/**
 * ================================================
 * {@link Application.ActivityLifecycleCallbacks} 默认实现类
 * 通过 {@link ActivityDelegate} 管理 {@link Activity}
 *
 * @see <a href="http://www.jianshu.com/p/75a5c24174b2">ActivityLifecycleCallbacks 分析文章</a>
 * Created by JessYan on 21/02/2017 14:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Singleton
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {


    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    Lazy<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycle;


    @Inject
    public ActivityLifecycle() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //如果 intent 包含了此字段,并且为 true 说明不加入到 list 进行统一管理

//            mAppManager.addActivity(activity);

        //配置ActivityDelegate
        if (activity instanceof IActivity) {
            ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
            if (activityDelegate == null) {
                Cache<String, Object> cache = getCacheFromActivity((IActivity) activity);
                activityDelegate = new ActivityDelegateImpl(activity);
                //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
                //否则存储在 LRU 算法的存储空间中, 前提是 Activity 使用的是 IntelligentCache (框架默认使用)
                cache.put(IntelligentCache.getKeyOfKeep(ActivityDelegate.ACTIVITY_DELEGATE), activityDelegate);
            }
            activityDelegate.onCreate(savedInstanceState);
        }

        registerFragmentCallbacks(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
//        mAppManager.setCurrentActivity(activity);

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
//        if (mAppManager.getCurrentActivity() == activity) {
//            mAppManager.setCurrentActivity(null);
//        }

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
//        mAppManager.removeActivity(activity);

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onDestroy();
            getCacheFromActivity((IActivity) activity).clear();
        }
    }

    /**
     * 给每个 Activity 的所有 Fragment 设置监听其生命周期, Activity 可以通过 {@link IActivity#useFragment()}
     * 设置是否使用监听,如果这个 Activity 返回 false 的话,这个 Activity 下面的所有 Fragment 将不能使用 {@link FragmentDelegate}
     * 意味着 {@link BaseFragment} 也不能使用
     *
     * @param activity
     */
    private void registerFragmentCallbacks(Activity activity) {

        boolean useFragment = (activity instanceof IActivity) && ((IActivity) activity).useFragment();
        if (activity instanceof FragmentActivity && useFragment) {
            //mFragmentLifecycle 为 Fragment 生命周期实现类, 用于框架内部对每个 Fragment 的必要操作, 如给每个 Fragment 配置 FragmentDelegate
            //注册框架内部已实现的 Fragment 生命周期逻辑
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle.get(), true);
        }
    }

    private ActivityDelegate fetchActivityDelegate(Activity activity) {
        ActivityDelegate activityDelegate = null;
        if (activity instanceof IActivity) {
            Cache<String, Object> cache = getCacheFromActivity((IActivity) activity);
            activityDelegate = (ActivityDelegate) cache.get(IntelligentCache.getKeyOfKeep(ActivityDelegate.ACTIVITY_DELEGATE));
        }
        return activityDelegate;
    }

    @NonNull
    private Cache<String, Object> getCacheFromActivity(IActivity activity) {
        return activity.provideCache();
    }
}
