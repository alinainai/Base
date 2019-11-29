package com.gas.test.ui.activity.show.di;

import androidx.fragment.app.Fragment;

import com.gas.test.ui.activity.show.IShowConst;
import com.gas.test.ui.fragment.adapter.AdapterFragment;
import com.gas.test.ui.fragment.ratioview.RatioViewFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description: 提供测试界面需要展示的Fragment
 * <p>
 * Created by GasMvpTemplate on 11/28/2019 09:50
 * ================================================
 */
@Module
public abstract class ShowModule {

    @Provides
    @Named(IShowConst.RATIOVIEWFRAGMENT)
    static Fragment provideRatioViewFragment() {
        return RatioViewFragment.newInstance();
    }

    @Provides
    @Named(IShowConst.ADAPTERFRAGMENT)
    static Fragment provideAdapterViewFragment() {
        return AdapterFragment.newInstance();
    }

}