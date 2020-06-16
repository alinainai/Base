package com.gas.zhihu.fragment.fileselect

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.paginate.interfaces.EmptyInterface
import com.base.paginate.interfaces.OnMultiItemClickListeners
import com.base.paginate.viewholder.PageViewHolder
import com.gas.zhihu.R
import com.gas.zhihu.app.ZhihuConstants
import com.gas.zhihu.bean.FileItemBean
import com.gas.zhihu.fragment.fileselect.di.DaggerFileSelectComponent
import com.gas.zhihu.fragment.fileselect.di.FileSelectModule
import com.gas.zhihu.fragment.fileselect.mvp.FileSelectContract
import com.gas.zhihu.fragment.fileselect.mvp.FileSelectPresenter
import kotlinx.android.synthetic.main.zhihu_fragment_add_paper.*
import kotlinx.android.synthetic.main.zhihu_fragment_file_select.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.zhihu_fragment_add_paper.titleView as titleView1


/**
 * ================================================
 * desc:选择文件
 *
 * created by author ljx
 * date  2020/5/24
 * email 569932357@qq.com
 *
 * ================================================
 */
class FileSelectFragment : BaseFragment<FileSelectPresenter>(), FileSelectContract.View {
    companion object {

        const val TYPE = "type"
        const val REQUEST_STORAGE_PERMISSION = 103

        fun newInstance(): FileSelectFragment {
            val fragment = FileSelectFragment()
            return fragment
        }

        fun setPagerArgs(type: String): Bundle? {
            val args = Bundle()
            args.putString(TYPE, type)
            return args
        }
    }

    @Inject
    lateinit var mAdapter: FileItemAdapter

    @Inject
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerFileSelectComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .fileSelectModule(FileSelectModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.zhihu_fragment_file_select, container, false);
    }

    private var mType: String? = ""

    override fun initData(savedInstanceState: Bundle?) {
        mType = activity!!.intent.getStringExtra(TYPE)
        when (mType) {
            ZhihuConstants.FILE_TYPE_WORD -> {
                titleView.titleText = "选择word文件"
            }
            ZhihuConstants.FILE_TYPE_PDF -> {
                titleView.titleText = "选择pdf文件"
            }
        }
        titleView.setOnBackListener { killMyself() }
        itemRefresh.isEnabled = false

        mAdapter.setOnMultiItemClickListener { _, data, _, _ ->
            data?.let {
                val intent=Intent()
                intent.putExtra("path",it.filePath)
                activity?.setResult(Activity.RESULT_OK,intent)
                killMyself()
            }
        }
        ArmsUtils.configRecyclerView(itemRecycler, mLayoutManager)
        itemRecycler.adapter = mAdapter
        mAdapter.setEmptyView(EmptyInterface.STATUS_LOADING)
        requestStoragePermission()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户同意，执行相应操作
                showFileItem()
            } else {
                // 用户不同意，向用户展示该权限作用
                showMessage("请开启权限，否则选择功能不可用")
                mAdapter.setEmptyView(EmptyInterface.STATUS_FAIL)
            }
        }
    }

    private fun requestStoragePermission() {

        val hasCameraPermission = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            showFileItem()
        } else {
            // 没有权限，向用户申请该权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_STORAGE_PERMISSION);
            }
        }

    }

    private fun showFileItem() {
        mPresenter?.getFileItem(mType)
    }

    override fun setPaperData(list: List<FileItemBean>) {
        mAdapter.setNewData(list)
        if (list.isNotEmpty()) {
            mAdapter.loadEnd()
        } else {
            mAdapter.setEmptyView(EmptyInterface.STATUS_EMPTY)
        }
    }

    override fun setData(data: Any?) {

    }

    override fun getWrapActivity(): Activity {
        return activity!!
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
