package com.base.baseui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.lib.base.BaseFragment;
import com.base.lib.mvp.IPresenter;

public abstract class GasFragment<P extends IPresenter> extends BaseFragment<P> {

    protected abstract int initLayoutId();

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(initLayoutId(), container, false);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    //默认不使用eventBug
    @Override
    public boolean useEventBus() {
        return false;
    }
}
