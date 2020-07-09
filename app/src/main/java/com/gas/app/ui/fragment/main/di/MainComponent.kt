package com.gas.app.ui.fragment.main.di

import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.FragmentScope
import com.gas.app.ui.fragment.main.MainFragment
import com.gas.app.ui.fragment.main.mvp.MainContract
import dagger.BindsInstance
import dagger.Component

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/30/2019 15:27
 * ================================================
 */
@FragmentScope
@Component(modules = [MainModule::class], dependencies = [AppComponent::class])
interface MainComponent {
    fun inject(fragment: MainFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: MainContract.View): Builder
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): MainComponent
    }
}