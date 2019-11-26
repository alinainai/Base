package com.gas.beauty.ui.home;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.gas.beauty.R;
import com.gas.beauty.ui.article.ArticleFragment;
import com.gas.beauty.ui.home.di.DaggerHomeComponent;
import com.gas.beauty.ui.home.mvp.HomeContract;
import com.gas.beauty.ui.home.mvp.HomePresenter;
import com.lib.commonsdk.constants.RouterHub;

@Route(path = RouterHub.GANK_HOMEACTIVITY)
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.gank_activity_home;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        Fragment fragment = ArticleFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commitNow();
        fragment.setUserVisibleHint(true);

    }


    @Override
    public Activity getActivity() {
        return this;
    }


    @Override
    public void showMessage(@NonNull String message) {


    }


}
