package com.gas.app.ui.fragment.info.di

import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.FragmentScope
import com.gas.app.ui.fragment.info.InfoFragment
import com.gas.app.ui.fragment.info.mvp.InfoContract
import dagger.BindsInstance
import dagger.Component

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/30/2019 14:54
 * ================================================
 */
@FragmentScope
@Component(modules = [InfoModule::class], dependencies = [AppComponent::class])
interface InfoComponent {
    fun inject(fragment: InfoFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: InfoContract.View): Builder
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): InfoComponent
    }
}