package com.gas.app.ui.activity.main.di

import dagger.Component
import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.ActivityScope

import com.gas.app.ui.activity.main.di.MainModule

import com.gas.app.ui.activity.main.MainActivity

import com.gas.app.ui.activity.main.mvp.MainContract


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 07/22/2020 20:05
 * ================================================
 */

@ActivityScope
@Component(modules = [MainModule::class], dependencies = [AppComponent::class])
interface MainComponent {
    fun inject(activity: MainActivity)
}
