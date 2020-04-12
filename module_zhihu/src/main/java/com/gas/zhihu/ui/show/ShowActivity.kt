package com.gas.zhihu.ui.show

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import butterknife.OnClick
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.https.image.ImageLoader
import com.base.lib.util.ArmsUtils
import com.base.lib.util.Preconditions
import com.base.paginate.interfaces.EmptyInterface
import com.gas.zhihu.R
import com.gas.zhihu.R2
import com.gas.zhihu.app.ZhihuConstants.ZHIHU_TEST_IMAGE_FILe_NAME
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
import com.lib.commonsdk.glide.ImageConfigImpl
import com.lib.commonsdk.utils.GasAppUtils
import com.lib.commonsdk.utils.QRCode
import com.lib.commonsdk.utils.Utils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.zhihu_activity_show.*
import java.io.File
import javax.inject.Inject

/**
 * ================================================
 * desc:
 *
 * created by author ljx
 * date  2020/4/12
 * email 569932357@qq.com
 *
 * ================================================
 */
class ShowActivity : BaseActivity<ShowPresenter?>(), ShowContract.View {


    @Inject
    lateinit var imageLoader: ImageLoader


    private var mDisposable: Disposable? = null

    private var mImagePath: String? = null

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

        mImagePath = File(Utils.getExternalFilesDir(getActivity()).path, ZHIHU_TEST_IMAGE_FILe_NAME).path

        emptyView.refreshView.setOnClickListener { v: View? -> loadData() }
       viewClickInit()
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


    private fun viewClickInit() {

        titleView.setOnBackListener {  killMyself() }
        imageAddress.setOnClickListener {  }
        tvAddressCopy.setOnClickListener {  mPresenter!!.setAddressToCopy()}
        tvAddressInfoTrue.setOnClickListener {  showMapDialog(mPresenter!!.locationInfo) }
        imageCode.setOnClickListener {
            mPresenter!!.qrCodeInfo?.let { QrCodeShowDialog().show(this, "签到二维码", it) }
        }

    }

    override fun setDataInfo(data: MapBean?) {

        data?.let {
            tvDataInfo!!.text = GasAppUtils.getString(R.string.zhihu_map_title_name, data.mapName)
            tvAddressInfoTrue!!.text = data.locationInfo
            tvRemarkInfoTrue!!.text = data.note

            imageLoader.loadImage(this,
                    ImageConfigImpl
                            .builder()
                            .url(File(mImagePath, data.pathName).path)
                            .imageView(imageAddress)
                            .build())

        }


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

    override fun getActivity(): Activity {
        return this
    }

    /**
     * 显示地图选择弹框
     */
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