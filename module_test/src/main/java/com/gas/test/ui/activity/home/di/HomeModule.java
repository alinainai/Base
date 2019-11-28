package com.gas.test.ui.activity.home.di;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.lib.di.scope.ActivityScope;
import com.gas.test.ui.activity.home.HomeAdapter;
import com.gas.test.ui.activity.home.mvp.HomeContract;
import com.gas.test.ui.activity.home.mvp.HomeModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 11/27/2019 09:23
 * ================================================
 */
@Module
public abstract class HomeModule {

    @Binds
    abstract HomeContract.Model bindHomeModel(HomeModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(HomeContract.View view) {
        return new LinearLayoutManager(view.getWrapContext());
    }

    @ActivityScope
    @Provides
    static HomeAdapter provideHomeAdapter(HomeContract.View view) {
        return new HomeAdapter(view.getWrapContext());
    }


}