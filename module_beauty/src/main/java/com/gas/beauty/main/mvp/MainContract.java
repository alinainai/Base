package com.gas.beauty.main.mvp;

import android.app.Activity;

import com.base.lib.mvp.IModel;
import com.base.lib.mvp.IView;
import com.gas.beauty.bean.GankBaseResponse;
import com.gas.beauty.bean.GankItemBean;

import java.util.List;

import io.reactivex.Observable;

public interface MainContract {


    interface View extends IView {
        Activity getActivity();
        void success();
        void onError();
    }


    interface Model extends IModel{
        Observable<GankBaseResponse<List<GankItemBean>>> getGirlList(int num, int page);
    }

}
