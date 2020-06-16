package com.gas.zhihu.ui.map.di;

import androidx.fragment.app.FragmentActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.base.lib.di.scope.ActivityScope;
import com.gas.zhihu.ui.map.SearchRecordAdapter;
import com.gas.zhihu.ui.map.mvp.MapContract;
import com.gas.zhihu.ui.map.mvp.MapModel;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
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


    @Binds
    abstract MapContract.Model bindMaapModel(MapModel model);

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(MapContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }

    @ActivityScope
    @Provides
    static FlexboxLayoutManager provideFlexboxLayoutManager(MapContract.View view) {
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager( view.getActivity());
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        return flexboxLayoutManager;
    }

    @ActivityScope
    @Provides
    static SearchRecordAdapter provideSearchAdapter(MapContract.View view) {
        return new SearchRecordAdapter(view.getActivity());
    }




}