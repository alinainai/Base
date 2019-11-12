package com.test.module_beauty.main.mvp;

import android.app.Activity;

import com.base.lib.mvp.IModel;
import com.base.lib.mvp.IView;
import com.test.module_beauty.bean.GankBaseResponse;
import com.test.module_beauty.bean.GankItemBean;

import java.util.List;

import io.reactivex.Observable;

public interface MainContract {


    interface View extends IView {
        Activity getActivity();
        void success();
    }


    interface Model extends IModel{
        Observable<GankBaseResponse<List<GankItemBean>>> getGirlList(int num, int page);
    }

}
