package com.gas.zhihu.fragment.addmap.di

import dagger.Component
import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.FragmentScope
import com.gas.zhihu.fragment.addmap.di.AddMapModule
import com.gas.zhihu.fragment.addmap.mvp.AddMapContract
import com.gas.zhihu.fragment.addmap.AddMapFragment


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/21/2020 11:44
 * ================================================
 */

@FragmentScope
@Component(modules = arrayOf(AddMapModule::class), dependencies = arrayOf(AppComponent::class))
interface AddMapComponent {

    fun inject(fragment: AddMapFragment)


}
