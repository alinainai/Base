package com.gas.zhihu.ui.office


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.gas.zhihu.R
import com.gas.zhihu.ui.office.di.DaggerOfficeComponent
import com.gas.zhihu.ui.office.di.OfficeModule
import com.gas.zhihu.ui.office.mvp.OfficeContract
import com.gas.zhihu.ui.office.mvp.OfficePresenter
import com.gas.zhihu.utils.OfficeHelper.FILEPATH
import com.tencent.smtt.sdk.TbsReaderView
import kotlinx.android.synthetic.main.zhihu_activity_office.*
import java.io.File


class OfficeActivity : BaseActivity<OfficePresenter>(), OfficeContract.View {


    private lateinit var tbsReaderView: TbsReaderView
    private var fileName:String=""

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerOfficeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .officeModule(OfficeModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        return R.layout.zhihu_activity_office

    }


    override fun initData(savedInstanceState: Bundle?) {



        intent?.apply {
            fileName= getStringExtra(FILEPATH)?:""
        }

        tbsReaderView = TbsReaderView(this, object : TbsReaderView.ReaderCallback {
            override fun onCallBackAction(p0: Int?, p1: Any?, p2: Any?) {

            }
        })

        tbsContainer.addView(tbsReaderView, FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        val result: Boolean = tbsReaderView.preOpen(parseFormat(File(fileName).name), false)

        val bundle = Bundle()
        bundle.putString("filePath", fileName)
        bundle.putString("tempPath", File(fileName).parent)

        if (result) {
            tbsReaderView.openFile(bundle)
        }
    }

    private fun parseFormat(fileName: String): String? {
        return fileName.substring(fileName.lastIndexOf(".") + 1)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }
}
