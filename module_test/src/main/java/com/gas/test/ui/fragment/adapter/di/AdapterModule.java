package com.gas.test.ui.fragment.adapter.di;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.lib.di.scope.FragmentScope;
import com.gas.test.ui.fragment.adapter.SimpleAdapter;
import com.gas.test.ui.fragment.adapter.mvp.AdapterContract;
import com.gas.test.ui.fragment.adapter.mvp.AdapterModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/29/2019 16:48
 * ================================================
 */
@Module
public abstract class AdapterModule {

    @Binds
    abstract AdapterContract.Model bindAdapterModel(AdapterModel model);


    @FragmentScope
    @Provides
    static SimpleAdapter provideSimpleAdapter(AdapterContract.View view) {
        return new SimpleAdapter(view.getWrapContent());
    }

    @FragmentScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(AdapterContract.View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getWrapContent());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        return layoutManager;
    }


}