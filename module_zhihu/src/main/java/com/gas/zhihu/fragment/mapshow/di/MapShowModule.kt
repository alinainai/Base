package com.gas.zhihu.fragment.mapshow.di

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.lib.di.scope.FragmentScope
import com.gas.zhihu.fragment.mapshow.MapShowAdapter

import dagger.Module
import dagger.Provides
import com.gas.zhihu.fragment.mapshow.mvp.MapShowContract
import com.gas.zhihu.fragment.mapshow.mvp.MapShowModel
import com.gas.zhihu.fragment.paper.PaperAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/31/2020 15:45
 * ================================================
 */
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

    @FragmentScope
    @Provides
    fun provideLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this.view.getWrapActivity())
    }

    @FragmentScope
    @Provides
    fun provideMapShowAdapter(): MapShowAdapter {
        val adapter = MapShowAdapter(this.view.getWrapActivity())
        return adapter
    }

}
