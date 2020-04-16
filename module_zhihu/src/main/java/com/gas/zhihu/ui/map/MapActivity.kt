package com.gas.zhihu.ui.map

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.base.baseui.dialog.CommonDialog
import com.base.baseui.dialog.QMUITipDialog
import com.base.baseui.utils.ViewUtils
import com.base.baseui.view.TitleView
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.lib.util.Preconditions
import com.base.paginate.interfaces.OnMultiItemClickListeners
import com.base.paginate.viewholder.PageViewHolder
import com.gas.zhihu.R
import com.gas.zhihu.R2
import com.gas.zhihu.dialog.TipShowDialog
import com.gas.zhihu.ui.detial.DetailsActivity
import com.gas.zhihu.ui.map.di.DaggerMapComponent
import com.gas.zhihu.ui.map.mvp.MapContract
import com.gas.zhihu.ui.map.mvp.MapPresenter
import com.gas.zhihu.ui.show.ShowActivity.Companion.launchActivity
import com.google.android.flexbox.FlexboxLayoutManager
import com.lib.commonsdk.utils.GasAppUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.zhihu_activity_map.*
import java.util.*
import javax.inject.Inject

/**
 * ================================================
 * desc: 地图搜索功能
 *
 *
 * created by author ljx
 * date  2020/3/28
 * email 569932357@qq.com
 *
 *
 * ================================================
 */
class MapActivity : BaseActivity<MapPresenter?>(), MapContract.View {



    private var mContext: Context? = null

    @Inject
    lateinit var  mRxPermissions: RxPermissions
    @Inject
    lateinit var mManager: FlexboxLayoutManager
    @Inject
    lateinit var mAdapter: SearchRecordAdapter

    private val loadDialog: QMUITipDialog by lazy {
        QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create()
    }


    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMapComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int { //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        mContext = this
        return R.layout.zhihu_activity_map
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewInit()
    }

    private fun viewInit() {

        title_view.setOnRightListener { startActivity(Intent(this, DetailsActivity::class.java)) }
        imgDeleteRecord.setOnClickListener {
            showDeleteTipDialog()
        }
        btn_go_login.setOnClickListener {
            val search = ViewUtils.getNoSpaceText(et_input)
            toSearch(search, true)
        }
        text_see_match_rule.setOnClickListener {
            startActivity(Intent(this, DetailsActivity::class.java))
        }
        mAdapter.setOnMultiItemClickListener { viewHolder, data, position, viewType ->
            mAdapter.onRecordItemClick(position)
            mPresenter!!.searchRecord = mAdapter.data
            toSearch(data!!, false)
        }
        rvSearchHistory.layoutManager = mManager
        rvSearchHistory.adapter = mAdapter
        val list = mPresenter!!.searchRecord
        if (list == null || list.isEmpty()) {
            vRecord!!.visibility = View.GONE
        } else {
            mAdapter.setInitData(list)
            vRecord!!.visibility = View.VISIBLE
        }
    }

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

    private fun showDeleteTipDialog() {
        CommonDialog.Builder(mContext)
                .setTitle("是否删除搜索记录")
                .setLeftTitle("取消")
                .setRightTitle("确定")
                .setCancelable(true)
                .setDialogClickListener(object : CommonDialog.onDialogClickListener {
                    override fun onRightClick() {
                        vRecord.visibility=View.GONE
                        mPresenter?.clearSearchRecord()
                        mAdapter.clearData()
                    }
                }).create().apply {
                    show()
                }

    }

    private fun toSearch(search: String, needToSave: Boolean) {
        if (TextUtils.isEmpty(search)) {
            GasAppUtils.toast("请输入正确的匹配规则")
            return
        }
        if (needToSave) {
            if (vRecord!!.visibility == View.GONE) {
                vRecord!!.visibility = View.VISIBLE
            }
            mAdapter!!.addItem(search)
            mPresenter!!.searchRecord = mAdapter!!.data
        }
        launchActivity(mContext, search)
    }

    private fun exitBy2Click() {
        val tExit: Timer
        if (!isExit) {
            isExit = true // 准备退出
            ArmsUtils.snackbarText("再按一次退出程序")
            tExit = Timer()
            tExit.schedule(object : TimerTask() {
                override fun run() {
                    isExit = false // 取消退出
                }
            }, 2000) //如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            ArmsUtils.exitApp()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click() //调用双击退出函数
        }
        return false
    }

    override fun showLoading() {
        loadDialog.show()
    }

    override fun hideLoading() {
        loadDialog.dismiss()
    }

    override fun getRxPermissions(): RxPermissions {
        return mRxPermissions!!
    }

    override fun getActivity(): Activity {
        return this
    }


    companion object {
        /**
         * 双击退出函数
         */
        private var isExit = false
    }
}