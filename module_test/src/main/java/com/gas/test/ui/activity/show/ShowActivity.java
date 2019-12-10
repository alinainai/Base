package com.gas.test.ui.activity.show;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.gas.test.R;
import com.gas.test.ui.activity.show.di.DaggerShowComponent;
import com.gas.test.ui.activity.show.mvp.ShowContract;
import com.gas.test.ui.activity.show.mvp.ShowPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;

import static com.base.lib.util.Preconditions.checkNotNull;
import static com.gas.test.ui.activity.show.IShowConst.ADAPTERFRAGMENT;
import static com.gas.test.ui.activity.show.IShowConst.RATIOVIEWFRAGMENT;
import static com.gas.test.ui.activity.show.IShowConst.RETROFITFRAGMENT;
import static com.gas.test.ui.activity.show.IShowConst.SHOWFRAGMENTTYPE;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 11/28/2019 09:50
 * ================================================
 */

public class ShowActivity extends BaseActivity<ShowPresenter> implements ShowContract.View {


    @Inject
    @Named(RATIOVIEWFRAGMENT)
    Fragment mRatioFragment;
    @Inject
    @Named(ADAPTERFRAGMENT)
    Lazy<Fragment> mAdapterFragment;
    @Inject
    @Named(RETROFITFRAGMENT)
    Lazy<Fragment> mRetrofitFragment;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShowComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        return R.layout.test_activity_show;

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        //默认的
        Fragment fragment = mRatioFragment;

        final String type = getIntent().getStringExtra(SHOWFRAGMENTTYPE);

        switch (type) {

            case RATIOVIEWFRAGMENT:
                fragment = mRatioFragment;
                break;
            case ADAPTERFRAGMENT:
                fragment = mAdapterFragment.get();
                break;
            case RETROFITFRAGMENT:
                fragment = mRetrofitFragment.get();
                break;

        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow();

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
