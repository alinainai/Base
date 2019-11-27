package com.gas.test.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.gas.test.R;
import com.gas.test.R2;
import com.gas.test.ui.activity.home.di.DaggerHomeComponent;
import com.gas.test.ui.activity.home.mvp.HomeContract;
import com.gas.test.ui.activity.home.mvp.HomePresenter;
import com.gas.test.ui.test.RatioViewActivity;
import com.lib.commonsdk.constants.RouterHub;

import butterknife.OnClick;

import static com.base.lib.util.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 11/27/2019 09:23
 * ================================================
 */
@Route(path = RouterHub.TEST_HOMEACTIVITY)
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        return R.layout.test_activity_home;
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


    @OnClick({R2.id.test_btn_ratio_view, R2.id.test_btn_two})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.test_btn_ratio_view) {
            ArmsUtils.startActivity(RatioViewActivity.class);
        } else if (id == R.id.test_btn_two) {
        }
    }
}
