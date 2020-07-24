package com.gas.app.ui.activity.splash.di

import com.base.lib.di.scope.ActivityScope

import dagger.Module
import dagger.Provides
import com.gas.app.ui.activity.splash.mvp.SplashContract
import com.gas.app.ui.activity.splash.mvp.SplashModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 07/22/2020 20:29
 * ================================================
 */
@Module
//构建SplashModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class SplashModule(private val view: SplashContract.View) {


    @ActivityScope
    @Provides
    fun provideSplashView(): SplashContract.View {
        return this.view
    }


    @ActivityScope
    @Provides
    fun provideSplashModel(model: SplashModel): SplashContract.Model {
        return model
    }

}
