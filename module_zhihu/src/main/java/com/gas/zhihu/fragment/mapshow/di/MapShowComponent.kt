package com.gas.zhihu.fragment.mapshow.di

import dagger.Component
import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.FragmentScope
import com.gas.zhihu.fragment.mapshow.di.MapShowModule
import com.gas.zhihu.fragment.mapshow.mvp.MapShowContract
import com.gas.zhihu.fragment.mapshow.MapShowFragment


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/31/2020 15:45
 * ================================================
 */

@FragmentScope
@Component(modules = arrayOf(MapShowModule::class), dependencies = arrayOf(AppComponent::class))
interface MapShowComponent {

    fun inject(fragment: MapShowFragment)


}
