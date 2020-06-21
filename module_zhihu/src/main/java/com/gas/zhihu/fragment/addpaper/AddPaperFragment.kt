package com.gas.zhihu.fragment.addpaper

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.base.baseui.dialog.QMUITipDialog
import com.base.baseui.dialog.select.ISelectItem
import com.base.baseui.dialog.select.OnItemOperateListener
import com.base.baseui.dialog.select.SelectBottomDialog
import com.base.baseui.dialog.select.SelectBottomDialog.Companion.MODE_CLICK
import com.base.baseui.dialog.select.SelectItem
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.gas.zhihu.R
import com.gas.zhihu.app.MapConstants
import com.gas.zhihu.app.MapConstants.Companion.MAP_ID
import com.gas.zhihu.app.MapConstants.Companion.MAP_NAME
import com.gas.zhihu.app.ZhihuConstants
import com.gas.zhihu.bean.MapSelectShowBean
import com.gas.zhihu.bean.PaperBean
import com.gas.zhihu.bean.VoltageLevelBean
import com.gas.zhihu.dialog.TipShowDialog
import com.gas.zhihu.fragment.addpaper.di.AddPaperModule
import com.gas.zhihu.fragment.addpaper.di.DaggerAddPaperComponent
import com.gas.zhihu.fragment.addpaper.mvp.AddPaperContract
import com.gas.zhihu.fragment.addpaper.mvp.AddPaperPresenter
import com.gas.zhihu.fragment.fileselect.FileSelectFragment
import com.gas.zhihu.fragment.mapshow.MapShowFragment
import com.gas.zhihu.ui.base.FragmentContainerActivity
import com.gas.zhihu.utils.MapBeanDbUtils
import com.gas.zhihu.utils.PagerBeanDbUtils
import com.lib.commonsdk.utils.FileUtils
import com.lib.commonsdk.utils.Utils
import kotlinx.android.synthetic.main.zhihu_fragment_add_paper.*
import java.io.File


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
        const val REQUEST_PICK_FILE = 101
        const val REQUEST_STORAGE_PERMISSION = 103
        const val REQUEST_MAP_INFO = 104
        const val PAPER_MODIFY_ID = "paper_modify_id"

        fun newInstance(): AddPaperFragment {
            val fragment = AddPaperFragment()
            return fragment
        }

        fun setPagerArgs(type: Int, paperId: Int = -1): Bundle? {
            val args = Bundle()
            args.putInt(TYPE, type)
            args.putInt(PAPER_MODIFY_ID, paperId)
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
    private var mType: Int = 0
    private var mPaperId: Int = -1
    private var selectVoltageLevel: String = ""
    private var selectMapKey: String = ""
    private var selectFileName: String = ""
    private var selectFilePathName: String = ""
    private var mPaperBean: PaperBean? = null

    override fun initData(savedInstanceState: Bundle?) {
        mType = activity!!.intent.getIntExtra(TYPE, 0)
        mPaperId = activity!!.intent.getIntExtra(PAPER_MODIFY_ID, -1)
        when (mType) {
            0 -> {
                if (mPaperId == -1) {
                    titleView.titleText = "添加图纸"
                } else {
                    titleView.titleText = "修改图纸信息"
                    mPaperBean = PagerBeanDbUtils.queryDataById(mPaperId)
                    if (mPaperBean == null) {
                        killMyself()
                        return
                    }
                    updateView(mPaperBean)
                }
            }
            1 -> {
                if (mPaperId == -1) {
                    titleView.titleText = "添加消缺经验集"
                } else {
                    titleView.titleText = "修改消缺经验集"
                    mPaperBean = PagerBeanDbUtils.queryDataById(mPaperId)
                    if (mPaperBean == null) {
                        killMyself()
                        return
                    }
                    updateView(mPaperBean)
                }
            }
        }
        titleView.setOnBackListener { killMyself() }
        mapName.setOnClickListener { mPresenter?.showMapDialog() }
        voltageName.setOnClickListener { mPresenter?.showVoltageDialog() }
        //选择文件
        imgFileHolder.setOnClickListener { requestStoragePermission() }
        tvFilePath.setOnClickListener { requestStoragePermission() }
        btnCommit.setOnClickListener { commit() }
    }

    private fun updateView(bean: PaperBean?) {
        bean?.let {
            etAddressLon.setText(it.fileName)
            mapName.text = MapBeanDbUtils.queryData(it.mapKey)?.mapName ?: ""
            voltageName.text = VoltageLevelBean.getVoltageName(it.voltageLevel.toString())
            val path = Utils.getExternalFilesDir(activity!!);
            val fileFile = File(path.path, ZhihuConstants.FILE_ZIP_FOLDER + File.separator + it.pathName)
            tvFilePath.text = fileFile.path
        }
    }

    override fun showMapSelectDialog(maps: List<MapSelectShowBean>) {

//        activity?.let {
//            SelectBottomDialog.getInstance()
//                    .setMode(MODE_CLICK)
//                    .setCancelable(true)
//                    .setList(maps)
//                    .setOnItemOptionListener(object : OnItemOperateListener {
//                        override fun onItemClickListener(itemId: ISelectItem) {
//                            mapName.text = itemId.name
//                            selectMapKey = itemId.id
//                        }
//                    }).show(it)
//        }
        FragmentContainerActivity.startActivityForResult(activity!!, MapShowFragment::class.java,
                MapShowFragment.setPagerArgs(MapConstants.PAPER_TYPE_DEFAULT, MapConstants.MAP_OPTION_SELECT),
                REQUEST_MAP_INFO)

    }

    override fun showVolSelectDialog(maps: List<VoltageLevelBean>) {
        activity?.let {
            SelectBottomDialog.getInstance()
                    .setMode(MODE_CLICK)
                    .setCancelable(true)
                    .setList(maps)
                    .setOnItemOptionListener(object : OnItemOperateListener {
                        override fun onItemClickListener(itemId: ISelectItem) {
                            voltageName.text = itemId.name
                            selectVoltageLevel = itemId.id
                        }
                    }).show(it)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户同意，执行相应操作
                selectFileDialog()
            } else {
                // 用户不同意，向用户展示该权限作用
                showMessage("请开启权限，否则图片选择功能不可用")
            }
        }
    }

    private fun requestStoragePermission() {
        val hasCameraPermission = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            selectFileDialog()
        } else {
            // 没有权限，向用户申请该权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_STORAGE_PERMISSION);
            }
        }
    }

    private fun selectFileDialog() {
        val items = mutableListOf<SelectItem>()
        items.add(SelectItem("0", "Word文件"))
        items.add(SelectItem("1", "PDF文件"))
        activity?.let {
            SelectBottomDialog.getInstance()
                    .setMode(MODE_CLICK)
                    .setCancelable(true)
                    .setList(items)
                    .setOnItemOptionListener(object : OnItemOperateListener {
                        override fun onItemClickListener(itemId: ISelectItem) {
                            selectFile(itemId.id)
                        }
                    }).show(it)
        }
    }

    private fun commit() {
        if (mPaperBean == null) {
            if (selectVoltageLevel.isBlank()) {
                showMessage("请选择电压")
                return
            }
            if (selectMapKey.isBlank()) {
                showMessage("请选择厂站信息")
                return
            }
            if (selectFileName.isBlank()) {
                showMessage("请选择文件")
                return
            }
            if (etAddressLon.text.isBlank()) {
                showMessage("文件名不能为空")
                return
            }
            val bean = PaperBean()
            bean.fileName = etAddressLon.text.toString()
            bean.mapKey = selectMapKey
            bean.pathName = selectFilePathName
            bean.type = mType
            bean.voltageLevel = selectVoltageLevel.toInt()
            mPresenter?.addPaperToDatabase(bean, selectFileName)
        } else {


            if (etAddressLon.text.isNullOrBlank()) {
                showMessage("请选择电压")
                return
            }
            if (mapName.text.isNullOrBlank()) {
                showMessage("请选择厂站信息")
                return
            }

            var modify = false
            if (etAddressLon.text.toString() != mPaperBean!!.fileName) {
                modify = true
                mPaperBean?.fileName = etAddressLon.text.toString()
            }

            if (mapName.text.toString() != MapBeanDbUtils.queryData(mPaperBean?.mapKey)?.mapName ?: "") {
                modify = true
                mPaperBean?.mapKey = selectMapKey
            }

            if (voltageName.text.toString() != VoltageLevelBean.getVoltageName(mPaperBean?.voltageLevel.toString())) {
                modify = true
                mPaperBean?.voltageLevel = selectVoltageLevel.toInt()
            }

            if (selectFilePathName.isNotBlank()) {
                modify = true
                mPaperBean?.pathName = selectFilePathName
            }
            if (modify) {
                if (selectFilePathName.isNotBlank()) {
                    mPresenter?.addPaperToDatabase(mPaperBean!!, selectFileName)
                } else {
                   if(mPresenter?.updatePaperInfo(mPaperBean!!) == true) {
                       showCommitSuccess()
                   }else{
                       showMessage("保存失败")
                   }

                }
            } else {
                showMessage("信息未修改")
            }


        }
    }

    override fun showCommitSuccess() {
        TipShowDialog.show(activity!!, "提示", "保存成功", {
            mPaperBean?.let {
                val intent = Intent()
                intent.putExtra("modify", 1)
                activity?.setResult(RESULT_OK, intent)
            }
            killMyself() })
    }

    private fun selectFile(type: String) {
        val typeName: String = when (type) {
            "0" -> {
                ZhihuConstants.FILE_TYPE_WORD
            }
            "1" -> {
                ZhihuConstants.FILE_TYPE_PDF
            }
            else -> {
                ""
            }
        }
        FragmentContainerActivity.startActivityForResult(activity!!,
                FileSelectFragment::class.java,
                FileSelectFragment.setPagerArgs(typeName),
                REQUEST_PICK_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_PICK_FILE -> {
                    data?.let {
                        it.getStringExtra("path")?.apply {
                            tvFilePath.text = this
                            selectFileName = this
                            when (this.substring(this.lastIndexOf('.'))) {
                                ".pdf" -> {
                                    imgFileHolder.setImageResource(R.mipmap.zhihu_file_type_pdf)
                                }
                                ".docx", ".doc" -> {
                                    imgFileHolder.setImageResource(R.mipmap.zhihu_file_type_word)
                                }
                                else -> {
                                    imgFileHolder.setImageResource(R.mipmap.zhihu_file_type_unknown)
                                }
                            }
                            val filePath = FileUtils.getFileName(this)
                            selectFilePathName = filePath
                            etAddressLon.setText(filePath)
                        }
                    }
                }
                REQUEST_MAP_INFO -> {
                    data?.let { intent ->
                        val name = intent.getStringExtra(MAP_NAME)
                        val id = intent.getStringExtra(MAP_ID)
                        if (name.isNullOrBlank() || id.isNullOrBlank())
                            return
                        mapName.text = name
                        selectMapKey = id
                    }
                }

            }
        }
    }

    private val loadDialog: QMUITipDialog by lazy {
        QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create()
    }

    override fun setData(data: Any?) {

    }

    override fun showLoading() {
        loadDialog.show()
    }

    override fun hideLoading() {
        loadDialog.dismiss()
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
