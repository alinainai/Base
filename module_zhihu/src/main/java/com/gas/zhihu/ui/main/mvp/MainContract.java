package com.gas.zhihu.ui.main.mvp;

import android.app.Activity;

import com.base.lib.mvp.IModel;
import com.base.lib.mvp.IView;
import com.gas.zhihu.bean.DailyListBean;

import io.reactivex.Observable;

public interface MainContract {


    interface View extends IView {
        Activity getActivity();
        void success();
        void onError();
    }


    interface Model extends IModel{
        Observable<DailyListBean> getDailyList();
    }

}
