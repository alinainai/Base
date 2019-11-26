package com.gas.test.test.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.gas.test.R;
import com.lib.commonsdk.constants.RouterHub;

public class Ratio2ViewActivity extends BaseActivity {


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.test_activity_ratio_view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        Fragment fragment= ArticleFragment.newInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,fragment)
                    .commitNow();
        }




    }
}
