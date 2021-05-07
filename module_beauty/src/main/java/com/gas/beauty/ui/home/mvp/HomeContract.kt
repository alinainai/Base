package com.gas.beauty.ui.home.mvp;

import android.app.Activity;

import com.base.lib.mvp.IModel;
import com.base.lib.mvp.IView;
import com.gas.beauty.bean.GankBaseResponse;
import com.gas.beauty.bean.GankItemBean;

import java.util.List;

import io.reactivex.Observable;

public interface HomeContract {

    interface View extends IView {
        Activity getActivity();
    }

}
