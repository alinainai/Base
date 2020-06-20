package com.gas.zhihu.fragment.papersearch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.paginate.interfaces.EmptyInterface

import com.gas.zhihu.fragment.papersearch.di.DaggerPaperSearchComponent
import com.gas.zhihu.fragment.papersearch.di.PaperSearchModule
import com.gas.zhihu.fragment.papersearch.mvp.PaperSearchContract
import com.gas.zhihu.fragment.papersearch.mvp.PaperSearchPresenter

import com.gas.zhihu.R
import com.gas.zhihu.app.ZhihuConstants
import com.gas.zhihu.bean.PaperShowBean
import com.gas.zhihu.fragment.paper.PagerFragment
import com.gas.zhihu.fragment.paper.PaperAdapter
import com.gas.zhihu.utils.OfficeHelper
import com.lib.commonsdk.utils.Utils
import kotlinx.android.synthetic.main.zhihu_fragment_map_show.*
import kotlinx.android.synthetic.main.zhihu_fragment_map_show.etSearch
import kotlinx.android.synthetic.main.zhihu_fragment_pager.*
import kotlinx.android.synthetic.main.zhihu_fragment_pager.itemRecycler
import kotlinx.android.synthetic.main.zhihu_fragment_pager.itemRefresh
import kotlinx.android.synthetic.main.zhihu_fragment_paper_search.*
import java.io.File
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 06/20/2020 17:46
 * ================================================
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @FragmentScope(請注意命名空間) class NullObjectPresenterByFragment
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class PaperSearchFragment : BaseFragment<PaperSearchPresenter>(), PaperSearchContract.View {
    companion object {

        const val TYPE = "type"

        fun setStartArgs(type: Int): Bundle? {
            val args = Bundle()
            args.putInt(TYPE, type)
            return args
        }

        fun newInstance(): PaperSearchFragment {
            val fragment = PaperSearchFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerPaperSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .paperSearchModule(PaperSearchModule(this))
                .build()
                .inject(this)
    }

    private var mType = 0

    @Inject
    lateinit var mAdapter: PaperAdapter

    @Inject
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.zhihu_fragment_paper_search, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        mType = activity!!.intent.getIntExtra(PagerFragment.TYPE, 0)

        searchTitle.setOnBackListener { killMyself() }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mPresenter?.startSearch(s?.let { s.toString().trim() } ?: "")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        mAdapter.setOnMultiItemClickListener { _, data, _, _ ->
            data?.let {
                val path = Utils.getExternalFilesDir(activity!!);
                val fileFile = File(path.path, ZhihuConstants.FILE_ZIP_FOLDER + File.separator + data.filePath)
                OfficeHelper.open(activity!!, fileFile.path)
            }
        }
        itemRefresh.isEnabled = false
        ArmsUtils.configRecyclerView(itemRecycler, mLayoutManager)
        itemRecycler.adapter = mAdapter
        mPresenter!!.initOriginData(mType)
    }


    override fun setPaperData(list: List<PaperShowBean>) {
        mAdapter.showDataDiff(list)
        if (list.isEmpty()) {
            mAdapter.setEmptyView(EmptyInterface.STATUS_EMPTY)
        }
    }

    override fun getWrapActivity(): Activity {
        return activity!!
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
