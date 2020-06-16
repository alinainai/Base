package com.gas.zhihu.fragment.addpaper.di

import com.base.lib.di.scope.FragmentScope

import dagger.Module
import dagger.Provides
import com.gas.zhihu.fragment.addpaper.mvp.AddPaperContract
import com.gas.zhihu.fragment.addpaper.mvp.AddPaperModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/21/2020 11:45
 * ================================================
 */
@Module
//构建AddPaperModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class AddPaperModule(private val view: AddPaperContract.View) {


    @FragmentScope
    @Provides
    fun provideAddPaperModel(model: AddPaperModel): AddPaperContract.Model {
        return model
    }


    @FragmentScope
    @Provides
    fun provideAddPaperView(): AddPaperContract.View {
        return this.view
    }

}
