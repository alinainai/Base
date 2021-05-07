package com.gas.zhihu.ui.fragment.mine;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;
import com.gas.zhihu.R;
import com.gas.zhihu.ui.fragment.mine.di.DaggerMineComponent;
import com.gas.zhihu.ui.fragment.mine.mvp.MineContract;
import com.gas.zhihu.ui.fragment.mine.mvp.MinePresenter;

/**
 * Author:
 * Time: 2016/8/12 14:28
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {

    public static final String TAG = "MineFragment";


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineComponent.builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.zhihu_fragment_mine, container, false);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


    }

    @Override
    public void setData(@Nullable Object data) {

    }


    public static MineFragment newInstance() {
        return new MineFragment();
    }


    @Override
    public Context getWrapContext() {
        return mContext;
    }


    @Override
    public void showMessage(@NonNull String message) {

    }
}
