package com.gas.zhihu.fragment.paper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.base.baseui.dialog.fiterpop.FilterPopupWindow
import com.base.baseui.dialog.itempop.ItemPopupWindow
import com.base.baseui.dialog.select.ISelectItem
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.paginate.interfaces.EmptyInterface
import com.base.paginate.interfaces.OnMultiItemClickListeners
import com.base.paginate.viewholder.PageViewHolder
import com.gas.zhihu.R
import com.gas.zhihu.app.ZhihuConstants.DEFAULT_TYPE
import com.gas.zhihu.app.ZhihuConstants.FILE_ZIP_FOLDER
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.bean.MapSelectShowBean
import com.gas.zhihu.bean.PaperShowBean
import com.gas.zhihu.bean.VoltageLevelBean
import com.gas.zhihu.fragment.addpaper.AddPaperFragment
import com.gas.zhihu.fragment.paper.di.DaggerPagerComponent
import com.gas.zhihu.fragment.paper.di.PagerModule
import com.gas.zhihu.fragment.paper.mvp.PagerContract
import com.gas.zhihu.fragment.paper.mvp.PagerPresenter
import com.gas.zhihu.ui.base.FragmentContainerActivity
import com.gas.zhihu.utils.OfficeHelper
import com.lib.commonsdk.utils.Utils
import kotlinx.android.synthetic.main.zhihu_fragment_pager.*
import java.io.File
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
    private var selectVoltageLevel:String =DEFAULT_TYPE
    private var selectMapKey:String =DEFAULT_TYPE


    private val filterMapPopupWindow:ItemPopupWindow<MapSelectShowBean> by lazy {

        val selectorModels: MutableList<MapSelectShowBean> = ArrayList<MapSelectShowBean>()

        mPresenter?.getValidMaps()?.takeIf { it.isNotEmpty() }?.apply {
            selectorModels.add(MapSelectShowBean(MapBean().apply {
                mapName="全部"
                keyName="-1"
            }))
            this.forEach{
                selectorModels.add(MapSelectShowBean(it))
            }
        }

        object :ItemPopupWindow<MapSelectShowBean>(activity, selectorModels){
            override fun onPositionClick(item: ISelectItem, position: Int) {

                item.apply {
                    if(id!=selectMapKey){
                        selectMapKey=id
                        tvTypeMap.text= if(id==DEFAULT_TYPE)"场站" else name
                        mPresenter?.getFilterData(selectVoltageLevel,selectMapKey)
                    }
                }
                dismiss()
            }

            override fun onPopDismiss() {
                tvTypeMap.isSelected=false
                imgTypeMap.isSelected=false
            }

        }


    }

    private val filterVoltagePop:FilterPopupWindow<VoltageLevelBean> by lazy {

        val selectorModels: MutableList<VoltageLevelBean> = ArrayList<VoltageLevelBean>()
        selectorModels.add(VoltageLevelBean(DEFAULT_TYPE, "全部"))
        selectorModels.addAll(VoltageLevelBean.voltageLevelItems)

        object :FilterPopupWindow<VoltageLevelBean>(activity, selectorModels){
            override fun onPositionClick(item: ISelectItem, position: Int) {

                item.apply {
                    if(id!=selectVoltageLevel){
                        selectVoltageLevel=id
                        tvTypeVoltage.text= if(id==DEFAULT_TYPE)"电压等级" else name
                        mPresenter?.getFilterData(selectVoltageLevel,selectMapKey)
                    }
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

        guideTitle.setOnRightListener {
            FragmentContainerActivity.startActivity(activity!!, AddPaperFragment::class.java,AddPaperFragment.setPagerArgs(0))
        }

        llTypeMap.setOnClickListener{
            showTypeMap()
        }
        llTypeVoltage.setOnClickListener{
            showTypeVoltage()
        }

        mAdapter.setOnMultiItemClickListener(object :OnMultiItemClickListeners<PaperShowBean?>{
            override fun onItemClick(viewHolder: PageViewHolder?, data: PaperShowBean?, position: Int, viewType: Int) {
                data?.let {
                    val path= Utils.getExternalFilesDir(activity!!);
                    val fileFile = File(path.path, FILE_ZIP_FOLDER+File.separator+data.filePath)
                    OfficeHelper.open(activity!!,fileFile.path)
                }
            }
        })
        itemRefresh.isEnabled=false
        ArmsUtils.configRecyclerView(itemRecycler,mLayoutManager)
        itemRecycler.adapter=mAdapter
        mAdapter.setEmptyView(EmptyInterface.STATUS_LOADING)
        mPresenter!!.initOriginData(mType)
        mPresenter!!.getFilterData(selectVoltageLevel,selectMapKey)

    }

    override fun setPaperData(list: List<PaperShowBean>){
        mAdapter.showDataDiff(list)
        if(list.isNotEmpty()){
            mAdapter.loadEnd()
        }else{
            mAdapter.setEmptyView(EmptyInterface.STATUS_EMPTY)
        }
    }

    private fun showTypeMap() {
        tvTypeMap.isSelected=true
        imgTypeMap.isSelected=true
        filterMapPopupWindow.showAsDropDown(llTypeVoltage,selectMapKey)
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
