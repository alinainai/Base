package com.gas.zhihu.ui.activity.login.di

import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.ActivityScope
import com.gas.zhihu.ui.activity.login.LoginActivity
import com.gas.zhihu.ui.activity.login.mvp.LoginContract
import dagger.BindsInstance
import dagger.Component

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpTemplate on 03/24/2020 20:50
 * ================================================
 */
@ActivityScope
@Component(dependencies = [AppComponent::class])
interface LoginComponent {
    fun inject(activity: LoginActivity?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: LoginContract.View?): Builder?
        fun appComponent(appComponent: AppComponent?): Builder?
        fun build(): LoginComponent?
    }
}