package com.gas.zhihu.ui.show.di

import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.ActivityScope
import com.gas.zhihu.ui.show.ShowActivity
import com.gas.zhihu.ui.show.mvp.ShowContract
import dagger.BindsInstance
import dagger.Component

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpTemplate on 03/28/2020 21:18
 * ================================================
 */
@ActivityScope
@Component(modules = [ShowModule::class], dependencies = [AppComponent::class])
interface ShowComponent {
    fun inject(activity: ShowActivity?)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: ShowContract.View?): Builder?

        fun appComponent(appComponent: AppComponent?): Builder?
        fun build(): ShowComponent?
    }
}