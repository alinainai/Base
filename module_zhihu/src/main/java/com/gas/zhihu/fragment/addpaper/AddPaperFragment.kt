package com.gas.zhihu.fragment.addpaper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.baseui.dialog.select.ISelectItem
import com.base.baseui.dialog.select.OnItemOperateListener
import com.base.baseui.dialog.select.SelectBottomDialog
import com.base.baseui.dialog.select.SelectBottomDialog.Companion.MODE_CLICK
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.gas.zhihu.R
import com.gas.zhihu.bean.MapSelectShowBean
import com.gas.zhihu.bean.VoltageLevelBean
import com.gas.zhihu.fragment.addpaper.di.AddPaperModule
import com.gas.zhihu.fragment.addpaper.di.DaggerAddPaperComponent
import com.gas.zhihu.fragment.addpaper.mvp.AddPaperContract
import com.gas.zhihu.fragment.addpaper.mvp.AddPaperPresenter
import com.gas.zhihu.fragment.paper.PagerFragment
import kotlinx.android.synthetic.main.zhihu_fragment_add_paper.*


/**
 * ================================================
 * desc: 添加图纸经验集
 *
 * created by author ljx
 * date  2020/5/23
 * email 569932357@qq.com
 *
 * ================================================
 */
class AddPaperFragment : BaseFragment<AddPaperPresenter>(), AddPaperContract.View {
    companion object {

        const val TYPE = "type"

        fun newInstance(): AddPaperFragment {
            val fragment = AddPaperFragment()
            return fragment
        }

        fun setPagerArgs(type:Int): Bundle? {
            val args = Bundle()
            args.putInt(PagerFragment.TYPE, type)
            return args
        }
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerAddPaperComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .addPaperModule(AddPaperModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.zhihu_fragment_add_paper, container, false);
    }

    /**
     * 0:图纸
     * 1:经验集
     */
    private var mType: Int = 0;
    private var selectVoltageLevel: String? = ""
    private var selectMapKey: String = ""

    override fun initData(savedInstanceState: Bundle?) {

        mType = activity!!.intent.getIntExtra(PagerFragment.TYPE, 0)
        when (mType) {
            0 -> {
                titleView.titleText = "添加图纸"
            }
            1 -> {
                titleView.titleText = "添加消缺经验集"
            }
        }
        titleView.setOnBackListener { killMyself() }
        mapName.setOnClickListener { mPresenter?.showMapDialog() }
        voltageName.setOnClickListener { mPresenter?.showVoltageDialog() }
        btnCommit.setOnClickListener { Log.e("TAG",selectVoltageLevel) }

    }

    override fun showMapSelectDialog(maps: List<MapSelectShowBean>) {

        activity.let {
            SelectBottomDialog.getInstance(activity!!)
                    .setMode(MODE_CLICK)
                    .setCancelable(true)
                    .setList(maps)
                    .setOnItemOptionListener(object : OnItemOperateListener {
                        override fun onItemClickListener(itemId: ISelectItem) {
                            mapName.text = itemId.name
                            selectMapKey = itemId.id
                        }
                    }).show()
        }

    }

    override fun showVolSelectDialog(maps: List<VoltageLevelBean>) {

        activity.let {
            SelectBottomDialog.getInstance(activity!!)
                    .setMode(MODE_CLICK)
                    .setCancelable(true)
                    .setList(maps)
                    .setOnItemOptionListener(object : OnItemOperateListener {
                        override fun onItemClickListener(itemId: ISelectItem) {
                            voltageName.text = itemId.name
                            selectVoltageLevel = itemId.id
                        }
                    }).show()
        }

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
        activity?.finish()
    }
}
