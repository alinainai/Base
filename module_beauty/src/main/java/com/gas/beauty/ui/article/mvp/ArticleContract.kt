package com.gas.beauty.ui.article.mvp

import android.content.Context
import androidx.fragment.app.Fragment
import com.base.lib.mvp.IView

interface ArticleContract {
    interface View : IView {
        val wrapContext: Context
        val currentFragment: Fragment
    }

    interface Model
}