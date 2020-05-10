package com.gas.zhihu.fragment.option.di

import com.base.lib.di.scope.FragmentScope
import com.gas.zhihu.fragment.option.mvp.OptionContract

import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/10/2020 22:52
 * ================================================
 */
@Module
//构建OptionModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class OptionModule(private val view: OptionContract.View) {


    @FragmentScope
    @Provides
    fun provideOptionView(): OptionContract.View {
        return this.view
    }

}
