package com.gas.zhihu.fragment.fileselect.di

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.lib.di.scope.FragmentScope
import com.gas.zhihu.fragment.fileselect.FileItemAdapter
import com.gas.zhihu.fragment.fileselect.mvp.FileSelectContract
import com.gas.zhihu.fragment.fileselect.mvp.FileSelectModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/24/2020 11:32
 * ================================================
 */
@Module
//构建FileSelectModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class FileSelectModule(private val view: FileSelectContract.View) {


    @FragmentScope
    @Provides
    fun provideFileSelectModel(model: FileSelectModel): FileSelectContract.Model {
        return model
    }


    @FragmentScope
    @Provides
    fun provideFileSelectView(): FileSelectContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this.view.getWrapActivity())
    }

    @FragmentScope
    @Provides
    fun providePagerAdapter(): FileItemAdapter {
        val adapter = FileItemAdapter(this.view.getWrapActivity())
        return adapter
    }

}
