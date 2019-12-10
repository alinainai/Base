package com.gas.test.ui.fragment.rxjava;

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

import com.gas.test.ui.fragment.rxjava.di.DaggerRxJavaComponent;
import com.gas.test.ui.fragment.rxjava.mvp.RxJavaContract;
import com.gas.test.ui.fragment.rxjava.mvp.RxJavaPresenter;

import static com.base.lib.util.Preconditions.checkNotNull;

import com.gas.test.R;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 12/08/2019 15:56
 * ================================================
 */
public class RxJavaFragment extends BaseFragment<RxJavaPresenter> implements RxJavaContract.View {

    public static RxJavaFragment newInstance() {
        RxJavaFragment fragment = new RxJavaFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRxJavaComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment_rx_java, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
