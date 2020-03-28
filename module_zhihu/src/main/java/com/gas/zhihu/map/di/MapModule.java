package com.gas.zhihu.map.di;

import dagger.Binds;
import dagger.Module;

import com.gas.zhihu.map.mvp.MapContract;
import com.gas.zhihu.map.mvp.MapModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/24/2020 21:09
 * ================================================
 */
@Module
public abstract class MapModule {

    @Binds
    abstract MapContract.Model bindMaapModel(MapModel model);


}