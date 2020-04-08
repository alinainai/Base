package com.gas.zhihu.ui.show

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.base.baseui.adapter.ExtendEmptyView
import com.base.baseui.view.TitleView
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.lib.util.Preconditions
import com.base.paginate.interfaces.EmptyInterface
import com.gas.zhihu.R
import com.gas.zhihu.R2
import com.gas.zhihu.bean.LocationBean
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.dialog.QrCodeShowDialog
import com.gas.zhihu.dialog.SelectMapDialog
import com.gas.zhihu.ui.show.di.DaggerShowComponent
import com.gas.zhihu.ui.show.mvp.ShowContract
import com.gas.zhihu.ui.show.mvp.ShowPresenter
import com.gas.zhihu.utils.LocationUtils.MAP_AMAP
import com.gas.zhihu.utils.LocationUtils.MAP_BAIDU
import com.gas.zhihu.utils.LocationUtils.MAP_TECENT
import com.gas.zhihu.utils.LocationUtils.getAMapMapIntent
import com.gas.zhihu.utils.LocationUtils.getBaiduMapIntent
import com.gas.zhihu.utils.LocationUtils.getTecentMapIntent
import com.lib.commonsdk.utils.GasAppUtils
import com.lib.commonsdk.utils.QRCode
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpTemplate on 03/28/2020 21:18
 * ================================================
 */
class ShowActivity : BaseActivity<ShowPresenter?>(), ShowContract.View {
    @BindView(R2.id.title_view)
    lateinit  var titleView: TitleView
    @BindView(R2.id.tv_data_info)
    lateinit   var tvDataInfo: TextView
    @BindView(R2.id.image_address)
    lateinit  var imageAddress: ImageView
    @BindView(R2.id.tv_address_info_true)
    lateinit  var tvAddressInfoTrue: TextView
    @BindView(R2.id.tv_remark_info_true)
    lateinit   var tvRemarkInfoTrue: TextView
    @BindView(R2.id.image_code)
    lateinit  var imageCode: ImageView
    @BindView(R2.id.empty_view)
    lateinit  var emptyView: ExtendEmptyView
    private var mDisposable: Disposable? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerShowComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)!!
                .view(this)!!
                .build()!!
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int { //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        return R.layout.zhihu_activity_show
    }

    override fun initData(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        emptyView!!.refreshView.setOnClickListener { v: View? -> loadData() }
        titleView!!.setOnBackListener { v: View? -> killMyself() }
        loadData()
    }

    private fun loadData() {
        if (intent != null && !TextUtils.isEmpty(intent.getStringExtra(MAP_KEY_ARG))) {
            Observable.create(ObservableOnSubscribe<MapBean> { emitter ->
                val map = mPresenter!!.queryDate(intent.getStringExtra(MAP_KEY_ARG))
                if (map == null) {
                    emitter.onNext(MapBean())
                } else {
                    emitter.onNext(map)
                }
                emitter.onComplete()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<MapBean> {
                        override fun onSubscribe(d: Disposable) {
                            mDisposable = d
                            showLoading()
                        }

                        override fun onNext(mapBean: MapBean) {
                            if (!TextUtils.isEmpty(mapBean.keyName)) {
                                mPresenter!!.freshViewData()
                            } else {
                                emptyView()
                            }
                        }

                        override fun onError(e: Throwable) {
                            errorView()
                            mDisposable!!.dispose()
                        }

                        override fun onComplete() {
                            mDisposable!!.dispose()
                        }
                    })
        } else {
            errorView()
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

    @OnClick(R2.id.image_address, R2.id.tv_address_copy, R2.id.tv_address_info_true, R2.id.tv_remark_modify, R2.id.image_code)
    fun onViewClicked(view: View) {
        if (view.id == R.id.image_address) {
        }
        else if (view.id == R.id.tv_address_copy) mPresenter!!.setAddressToCopy()
        else if (view.id == R.id.tv_address_info_true) showMapDialog(mPresenter!!.locationInfo)
        else if (view.id == R.id.tv_remark_modify) {
        }
        else if (view.id == R.id.image_code) mPresenter!!.qrCodeInfo?.let { QrCodeShowDialog().show(this, "签到二维码", it) }
    }

    override fun setDataInfo(data: MapBean?) {
        tvDataInfo!!.text = GasAppUtils.getString(R.string.zhihu_map_title_name, data?.mapName)
        tvAddressInfoTrue!!.text = data?.locationInfo
        tvRemarkInfoTrue!!.text = data?.note
    }

    override fun setQrCode(data: String?) {
        imageCode!!.setImageBitmap(QRCode.createQRCode(data, 200))
    }

    override fun successView() {
        emptyView!!.visibility = View.GONE
    }

    override fun showLoading() {
        emptyView!!.setStatus(EmptyInterface.STATUS_LOADING)
        emptyView!!.visibility = View.VISIBLE
    }

    override fun emptyView() {
        emptyView!!.setStatus(EmptyInterface.STATUS_EMPTY)
        emptyView!!.visibility = View.VISIBLE
    }

    override fun errorView() {
        emptyView!!.setStatus(EmptyInterface.STATUS_FAIL)
        emptyView!!.visibility = View.VISIBLE
    }

    private fun showMapDialog(bean: LocationBean?) {
        if (bean == null) {
            GasAppUtils.toast("数据错误")
            return
        }
        if (bean.isInfoError) {
            GasAppUtils.toast("数据错误")
            return
        }
        // 经度：116.44000 纬度： 39.93410
        SelectMapDialog().show(this, object : SelectMapDialog.OnMapClickListener {
            override fun onMapClick(map: Int) {
                when (map) {
                    MAP_AMAP -> startActivity(getAMapMapIntent(bean))
                    MAP_BAIDU -> startActivity(getBaiduMapIntent(bean))
                    MAP_TECENT -> startActivity(getTecentMapIntent(bean))
                    else -> {
                    }
                }
            }

        })
    }

    companion object {
        private const val MAP_KEY_ARG = "map_key_arg"
        fun launchActivity(context: Context?, key: String?) {
            val intent = Intent(context, ShowActivity::class.java)
            intent.putExtra(MAP_KEY_ARG, key)
            ArmsUtils.startActivity(intent)
        }
    }
}