package com.gas.zhihu.ui.activity.login.mvp

import com.base.lib.di.scope.ActivityScope
import com.base.lib.mvp.BasePresenter
import com.base.lib.mvp.IModel
import javax.inject.Inject

@ActivityScope
class LoginPresenter @Inject constructor(rootView: LoginContract.View?) : BasePresenter<IModel?, LoginContract.View?>(rootView) {
    override fun onDestroy() {
        super.onDestroy()
    }
}