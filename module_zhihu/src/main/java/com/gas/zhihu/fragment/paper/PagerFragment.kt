package com.gas.zhihu.fragment.paper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.base.baseui.dialog.fiterpop.FilterPopupWindow
import com.base.baseui.dialog.select.ISelectItem
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.paginate.interfaces.EmptyInterface
import com.gas.zhihu.R
import com.gas.zhihu.bean.VoltageLevelBean
import com.gas.zhihu.fragment.paper.di.DaggerPagerComponent
import com.gas.zhihu.fragment.paper.di.PagerModule
import com.gas.zhihu.fragment.paper.mvp.PagerContract
import com.gas.zhihu.fragment.paper.mvp.PagerPresenter
import kotlinx.android.synthetic.main.zhihu_fragment_pager.*
import java.util.*
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/17/2020 10:01
 * ================================================
 */
class PagerFragment : BaseFragment<PagerPresenter>(), PagerContract.View {
    companion object {

       const val TYPE="type"

        fun newInstance(): PagerFragment {
            val fragment = PagerFragment()
            return fragment
        }

        fun setPagerArgs(type:Int): Bundle? {
            val args = Bundle()
            args.putInt(TYPE, type)
            return args
        }

    }


    @Inject
    lateinit var mAdapter: PaperAdapter

    @Inject
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    /**
     * 0:图纸
     * 1:经验集
     */
    private var mType:Int =0;
    private var selectVoltageLevel:String ="-1"
    private var selectMapKey:String ="-1"


    private val filterVoltagePop:FilterPopupWindow<VoltageLevelBean> by lazy {

        val selectorModels: MutableList<VoltageLevelBean> = ArrayList<VoltageLevelBean>()
        selectorModels.add(VoltageLevelBean("-1", "全部"))
        selectorModels.addAll(VoltageLevelBean.voltageLevelItems)

        object :FilterPopupWindow<VoltageLevelBean>(activity, selectorModels){
            override fun onPositionClick(item: ISelectItem, position: Int) {

                item.apply {
                    selectVoltageLevel=id
                    tvTypeVoltage.text= if(id=="-1")"电压等级" else name
                }
                dismiss()
            }

            override fun onPopDismiss() {
                tvTypeVoltage.isSelected=false
                imgTypeVoltage.isSelected=false
            }

        }

    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerPagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .pagerModule(PagerModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.zhihu_fragment_pager, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {

        mType=activity!!.intent.getIntExtra(TYPE,0)
        when(mType){
            0->{guideTitle.titleText="图纸"}
            1->{guideTitle.titleText="消缺经验集"}
        }
        guideTitle.setOnBackListener {
            activity?.finish()
        }

        llTypeMap.setOnClickListener{

        }
        llTypeVoltage.setOnClickListener{
            showTypeVoltage()
        }

        itemRefresh.isEnabled=false
        ArmsUtils.configRecyclerView(itemRecycler,mLayoutManager)
        itemRecycler.adapter=mAdapter
        mAdapter.setEmptyView(EmptyInterface.STATUS_LOADING)


    }

    private fun showTypeMap() {
        tvTypeMap.isSelected=true
        imgTypeMap.isSelected=true
        filterVoltagePop.showAsDropDown(llTypeVoltage,selectVoltageLevel)
    }

    private fun showTypeVoltage() {

        tvTypeVoltage.isSelected=true
        imgTypeVoltage.isSelected=true
        filterVoltagePop.showAsDropDown(llTypeVoltage,selectVoltageLevel)

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

    override fun getWrapActivity():Activity{
        return activity!!
    }
}
