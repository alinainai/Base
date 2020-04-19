package com.gas.zhihu.ui.add

import android.content.Intent
import android.os.Bundle
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.lib.util.Preconditions
import com.gas.zhihu.R
import com.gas.zhihu.ui.add.di.DaggerAddComponent
import com.gas.zhihu.ui.add.mvp.AddContract
import com.gas.zhihu.ui.add.mvp.AddPresenter

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpTemplate on 04/19/2020 09:24
 * ================================================
 */
class AddActivity : BaseActivity<AddPresenter?>(), AddContract.View {
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerAddComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        return R.layout.zhihu_activity_add
    }

    override fun initData(savedInstanceState: Bundle?) {}
    override fun showMessage(message: String) {
        Preconditions.checkNotNull(message)
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        Preconditions.checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }
}