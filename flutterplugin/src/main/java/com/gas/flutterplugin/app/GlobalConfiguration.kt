package com.gas.flutterplugin.app

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.base.lib.base.delegate.AppLifecyclers
import com.base.lib.di.module.ConfigModule
import com.base.lib.integration.config.ClientConfigModule

class GlobalConfiguration : ClientConfigModule {
    override fun applyOptions(context: Context, builder: ConfigModule.Builder) {}
    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecyclers>) {
        // AppLifecycles 的所有方法都会在基类 Application 的对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        // 可以根据不同的逻辑添加多个实现类
        lifecycles.add(AppLifecyclesImpl())
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: List<Application.ActivityLifecycleCallbacks>) {}
    override fun injectFragmentLifecycle(context: Context, lifecycles: List<FragmentManager.FragmentLifecycleCallbacks>) {}
}