package com.gas.zhihu.map;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;


import com.gas.zhihu.map.di.DaggerMapComponent;
import com.gas.zhihu.map.mvp.MapContract;
import com.gas.zhihu.map.mvp.MapPresenter;


import static com.base.lib.util.Preconditions.checkNotNull;

import com.gas.zhihu.R;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/24/2020 21:09
 * ================================================
 */

public class MapActivity extends BaseActivity<MapPresenter> implements MapContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMapComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        return R.layout.zhihu_activity_map;

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
