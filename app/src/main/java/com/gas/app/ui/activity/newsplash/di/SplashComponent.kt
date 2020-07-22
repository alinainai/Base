package com.gas.app.ui.activity.newsplash.di

import dagger.Component
import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.ActivityScope

import com.gas.app.ui.activity.newsplash.di.SplashModule

import com.gas.app.ui.activity.newsplash.SplashActivity

import com.gas.app.ui.activity.newsplash.mvp.SplashContract


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 07/22/2020 20:29
 * ================================================
 */

@ActivityScope
@Component(
        modules = arrayOf(SplashModule::class),
        dependencies = arrayOf(AppComponent::class))
interface SplashComponent {

    fun inject(activity: SplashActivity)


}
