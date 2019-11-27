package com.gas.test.ui.test;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.gas.test.R;
import com.gas.test.R2;

import butterknife.OnClick;


public class RatioViewActivity extends BaseActivity {


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.test_activity_ratio_view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


    }

    @OnClick({R2.id.ratio_rv, R2.id.ratio_rv_2})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.ratio_rv) {
            ArmsUtils.snackbarText("我点击了第一个条目");
        } else if (id == R.id.ratio_rv_2) {
            ArmsUtils.snackbarText("我点击了第二个条目");
        }
    }
}
