package com.gas.zhihu.ui.map

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import com.base.baseui.dialog.CommonDialog
import com.base.baseui.dialog.QMUITipDialog
import com.base.baseui.utils.ViewUtils
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.lib.util.Preconditions
import com.gas.zhihu.R
import com.gas.zhihu.dialog.TextFilterDialog
import com.gas.zhihu.ui.detial.DetailsActivity
import com.gas.zhihu.ui.map.di.DaggerMapComponent
import com.gas.zhihu.ui.map.mvp.MapContract
import com.gas.zhihu.ui.map.mvp.MapPresenter
import com.gas.zhihu.ui.show.ShowActivity.Companion.launchActivity
import com.google.android.flexbox.FlexboxLayoutManager
import com.lib.commonsdk.utils.AppUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.zhihu_activity_map.*
import java.util.*
import java.util.concurrent.TimeUnit
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
    lateinit var mRxPermissions: RxPermissions
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

    private lateinit var mPublishSubject: PublishSubject<String>
    private val mCompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val popupText: TextFilterDialog by lazy {
        TextFilterDialog()
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

        title_view.setOnRightListener {

        }
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
        mAdapter.setOnMultiItemClickListener { _, data, position, _ ->
            mAdapter.onRecordItemClick(position)
            mPresenter!!.searchRecord = mAdapter.data
            toSearch(data!!, false)
        }
        rvSearchHistory.layoutManager = mManager
        rvSearchHistory.adapter = mAdapter
        val list = mPresenter!!.searchRecord
        if (list == null || list.isEmpty()) {
            vRecord.visibility = View.GONE
        } else {
            mAdapter.setInitData(list)
            vRecord.visibility = View.VISIBLE
        }
        et_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isEmpty()) {
                    startSearch("")
                } else {
                    //输入内容非空的时候才开始搜索
                    startSearch(s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })


        mPublishSubject = PublishSubject.create()
        mCompositeDisposable.add(mPublishSubject.debounce(200, TimeUnit.MILLISECONDS)
//                .filter { t -> t.isNotEmpty() }
                .switchMap { t -> getSearchObservable(t) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({showSearchResult(it)},{
                    it.printStackTrace()
                }))

        popupText.setListener {
            et_input.setText(it)
            et_input.setSelection(it.length)
            startSearch("")
            popupText.dismiss()
        }
    }

    private fun getSearchObservable(query: String):Observable<List<String>> {

        if(query.isEmpty()){
            return Observable.empty()
        }
       return Observable.create(ObservableOnSubscribe<List<String>> { emitter ->
           emitter.onNext(mPresenter!!.getSearchKeysWithKey(query))
           emitter.onComplete()
       }).subscribeOn(Schedulers.io())
    }
    private fun startSearch(str: String) {
        mPublishSubject.onNext(str)
    }

    private fun showSearchResult(list: List<String>) {
        popupText.apply {
            if (isShow()) {
                updateData(list)
            } else {
                show(et_input, list)
            }
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
                        vRecord.visibility = View.GONE
                        mPresenter?.clearSearchRecord()
                        mAdapter.clearData()
                    }
                }).create().apply {
                    show()
                }

    }

    private fun toSearch(search: String, needToSave: Boolean) {
        if (TextUtils.isEmpty(search)) {
            AppUtils.toast("请输入正确的匹配规则")
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