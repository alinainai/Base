package com.gas.test.ui.fragment.ratioview;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;

import com.gas.test.ui.fragment.ratioview.di.DaggerRatioViewComponent;
import com.gas.test.ui.fragment.ratioview.mvp.RatioViewContract;
import com.gas.test.ui.fragment.ratioview.mvp.RatioViewPresenter;

import static com.base.lib.util.Preconditions.checkNotNull;

import com.gas.test.R;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/28/2019 09:58
 * ================================================
 */
public class RatioViewFragment extends BaseFragment<RatioViewPresenter> implements RatioViewContract.View {

    public static RatioViewFragment newInstance() {
        RatioViewFragment fragment = new RatioViewFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRatioViewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment_ratio_view, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

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

    }
}
