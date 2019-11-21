package com.gas.zhihu.main.mvp;

import android.app.Activity;
import android.content.Context;

import com.base.lib.mvp.IModel;
import com.base.lib.mvp.IView;
import com.gas.zhihu.bean.DailyListBean;

import java.util.List;

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
