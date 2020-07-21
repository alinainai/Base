package com.gas.zhihu.fragment.option.di

import dagger.Component
import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.FragmentScope
import com.gas.zhihu.fragment.option.OptionFragment


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/10/2020 22:52
 * ================================================
 */

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface OptionComponent {

    fun inject(fragment: OptionFragment)


}