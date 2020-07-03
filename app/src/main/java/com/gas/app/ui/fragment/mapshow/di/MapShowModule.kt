package com.gas.app.ui.fragment.mapshow.di

import com.base.lib.di.scope.FragmentScope
import com.gas.app.ui.fragment.mapshow.mvp.MapShowContract
import com.gas.app.ui.fragment.mapshow.mvp.MapShowModel

import dagger.Module
import dagger.Provides

@Module
//构建MapShowModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class MapShowModule(private val view: MapShowContract.View) {

    @FragmentScope
    @Provides
    fun provideMapShowModel(model: MapShowModel): MapShowContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun provideMapShowView(): MapShowContract.View {
        return this.view
    }

}
