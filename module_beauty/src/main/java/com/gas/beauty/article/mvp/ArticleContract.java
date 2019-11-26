package com.gas.beauty.article.mvp;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.base.lib.mvp.IView;

public interface ArticleContract {


    interface View extends IView {

        Context getWrapContext();
        Fragment getCurrentFragment();

    }

    interface Model {

    }


}
