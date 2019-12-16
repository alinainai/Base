package com.gas.app.ui.main.di;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.base.lib.di.scope.ActivityScope;
import com.lib.commonsdk.adapter.AdapterViewPager;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import com.gas.app.ui.fragment.info.InfoFragment;
import com.gas.app.ui.fragment.main.MainFragment;
import com.gas.app.ui.fragment.mine.MineFragment;
import com.gas.app.ui.fragment.product.ProductFragment;
import com.gas.app.ui.main.mvp.MainContract;

@Module
public abstract class MainModule {


    @ActivityScope
    @Provides
    static AdapterViewPager providePagerAdapter(MainContract.View view, List<Fragment> fragments) {
        return new AdapterViewPager(((AppCompatActivity) view.getWrapContext()).getSupportFragmentManager(), fragments);
    }

    @ActivityScope
    @Provides
    static List<Fragment> provideFragments() {
        List<Fragment> mFragments = new ArrayList<>(4);
        mFragments.add(MainFragment.newInstance());
        mFragments.add(InfoFragment.newInstance());
        mFragments.add(ProductFragment.newInstance());
        mFragments.add(MineFragment.newInstance());
        return mFragments;
    }


}
