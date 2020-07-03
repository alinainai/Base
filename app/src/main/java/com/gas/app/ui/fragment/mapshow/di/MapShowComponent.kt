package com.gas.app.ui.fragment.mapshow.di

import dagger.Component
import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.FragmentScope
import com.gas.app.ui.fragment.mapshow.MapShowFragment

@FragmentScope
@Component(modules = [MapShowModule::class], dependencies = [AppComponent::class])
interface MapShowComponent {
    fun inject(fragment: MapShowFragment)
}
