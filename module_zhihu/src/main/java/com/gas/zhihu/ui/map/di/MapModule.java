package com.gas.zhihu.ui.map.di;

import androidx.fragment.app.FragmentActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.base.lib.di.scope.ActivityScope;
import com.gas.zhihu.ui.map.mvp.MapContract;
import com.gas.zhihu.ui.map.mvp.MapModel;
import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/24/2020 21:09
 * ================================================
 */
@Module
public abstract class MapModule {

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(MapContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }

    @Binds
    abstract MapContract.Model bindMaapModel(MapModel model);


}