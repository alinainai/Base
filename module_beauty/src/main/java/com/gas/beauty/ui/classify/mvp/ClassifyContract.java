package com.gas.beauty.ui.classify.mvp;

import android.content.Context;

import com.base.lib.mvp.IView;
import com.gas.beauty.bean.GankItemBean;

import java.util.List;

import io.reactivex.Observable;

public interface ClassifyContract {


    interface View extends IView {
        void loadEnd();
        Context getWrapContext();

    }

    interface Model {
        Observable<List<GankItemBean>> getGankItemData(String suburl);
    }


}
