package com.gas.zhihu.fragment.addmap.di

import com.base.lib.di.scope.FragmentScope

import dagger.Module
import dagger.Provides
import com.gas.zhihu.fragment.addmap.mvp.AddMapContract
import com.gas.zhihu.fragment.addmap.mvp.AddMapModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/21/2020 11:44
 * ================================================
 */
@Module
//构建AddMapModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class AddMapModule(private val view: AddMapContract.View) {


    @FragmentScope
    @Provides
    fun provideAddMapModel(model: AddMapModel): AddMapContract.Model {
        return model
    }


    @FragmentScope
    @Provides
    fun provideAddMapView(): AddMapContract.View {
        return this.view
    }

}
