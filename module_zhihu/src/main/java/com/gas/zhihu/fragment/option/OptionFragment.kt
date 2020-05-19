package com.gas.zhihu.fragment.option

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.IPresenter
import com.base.lib.util.ArmsUtils
import com.gas.zhihu.R
import com.gas.zhihu.app.ZhihuConstants.FILE_ZIP_FOLDER
import com.gas.zhihu.fragment.option.di.DaggerOptionComponent
import com.gas.zhihu.fragment.option.mvp.OptionContract
import com.gas.zhihu.fragment.paper.PagerFragment
import com.gas.zhihu.ui.base.FragmentContainerActivity
import com.gas.zhihu.ui.map.MapActivity
import com.gas.zhihu.utils.OfficeHelper
import com.lib.commonsdk.utils.Utils
import kotlinx.android.synthetic.main.zhihu_fragment_option.*
import java.io.File
import javax.inject.Inject


/**
 * ================================================
 * desc: 功能选项卡
 *
 * created by author ljx
 * date  2020/5/16
 * email 569932357@qq.com
 *
 * ================================================
 */
@FragmentScope
class NullObjectPresenterByFragment
@Inject constructor() : IPresenter {
    override fun onStart() {
    }

    override fun onDestroy() {
    }
}

class OptionFragment : BaseFragment<NullObjectPresenterByFragment>(), OptionContract.View, View.OnClickListener {
    companion object {
        fun newInstance(): OptionFragment {
            val fragment = OptionFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerOptionComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.zhihu_fragment_option, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {

        btnGuide.setOnClickListener(this)
        btnGraphPager.setOnClickListener(this)
        btnExperience.setOnClickListener(this)


    }


    override fun setData(data: Any?) {

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

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btnGuide -> {
                startActivity(Intent(activity, MapActivity::class.java))
            }
            R.id.btnGraphPager -> {

                FragmentContainerActivity.startActivity(activity!!,PagerFragment::class.java,PagerFragment.setPagerArgs(0))

//                SelectBottomDialog.getInstance(context!!)
//                        .setCancelable(true)
//                        .setList(VoltageLevelBean.getVoltageLevelItems())
//                        .setMode(MODE_CLICK)
//                        .setOnItemOptionListener(object : OnItemOperateListener{
//                            override fun onItemClickListener(itemId: ISelectItem) {
//                               Log.e("Tag",itemId.name)
//                            }
//                        })
//                        .show()


//                val path= Utils.getExternalFilesDir(activity!!);
//                val fileFile = File(path.path, FILE_ZIP_FOLDER+File.separator+"kp001.pdf")
//                OfficeHelper.open(activity!!,fileFile.path,1);
            }
            R.id.btnExperience -> {
                FragmentContainerActivity.startActivity(activity!!,PagerFragment::class.java,PagerFragment.setPagerArgs(1))
            }
        }

    }
}
