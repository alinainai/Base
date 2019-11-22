package com.gas.beauty.test;

import android.content.Context;

import com.base.lib.mvp.IView;

public interface ClassifyContract {


    interface View extends IView {

        Context getWrapContext();

    }

    interface Model {

    }


}
