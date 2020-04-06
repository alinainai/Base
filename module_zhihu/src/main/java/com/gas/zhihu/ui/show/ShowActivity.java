package com.gas.zhihu.ui.show;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.baseui.adapter.ExtendEmptyView;
import com.base.baseui.view.TitleView;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.base.paginate.interfaces.EmptyInterface;
import com.gas.zhihu.R;
import com.gas.zhihu.R2;
import com.gas.zhihu.bean.LocationBean;
import com.gas.zhihu.bean.MapBean;
import com.gas.zhihu.dialog.QrCodeShowDialog;
import com.gas.zhihu.dialog.SelectMapDialog;
import com.gas.zhihu.ui.show.di.DaggerShowComponent;
import com.gas.zhihu.ui.show.mvp.ShowContract;
import com.gas.zhihu.ui.show.mvp.ShowPresenter;
import com.gas.zhihu.utils.LocationUtils;
import com.gas.zhihu.utils.MapBeanDbUtils;
import com.lib.commonsdk.utils.GasAppUtils;
import com.lib.commonsdk.utils.QRCode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.base.lib.util.Preconditions.checkNotNull;
import static com.gas.zhihu.utils.LocationUtils.MAP_AMAP;
import static com.gas.zhihu.utils.LocationUtils.MAP_BAIDU;
import static com.gas.zhihu.utils.LocationUtils.MAP_TECENT;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/28/2020 21:18
 * ================================================
 */

public class ShowActivity extends BaseActivity<ShowPresenter> implements ShowContract.View {


    @BindView(R2.id.title_view)
    TitleView titleView;
    @BindView(R2.id.tv_data_info)
    TextView tvDataInfo;
    @BindView(R2.id.image_address)
    ImageView imageAddress;
    @BindView(R2.id.tv_address_info_true)
    TextView tvAddressInfoTrue;
    @BindView(R2.id.tv_remark_info_true)
    TextView tvRemarkInfoTrue;
    @BindView(R2.id.image_code)
    ImageView imageCode;
    @BindView(R2.id.empty_view)
    ExtendEmptyView emptyView;

    private static final String MAP_KEY_ARG="map_key_arg";
    private Disposable mDisposable;



    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShowComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        return R.layout.zhihu_activity_show;

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        emptyView.getRefreshView().setOnClickListener(v -> loadData());
        titleView.setOnBackListener(v -> killMyself());
        loadData();
    }

    private void loadData(){
        if(getIntent()!=null&&! TextUtils.isEmpty(getIntent().getStringExtra(MAP_KEY_ARG)) ){

            Observable.create((ObservableOnSubscribe<MapBean>) emitter -> {
                MapBean map = mPresenter.queryDate(getIntent().getStringExtra(MAP_KEY_ARG));
                if(map==null){
                    emitter.onNext(new MapBean());
                }else {
                    emitter.onNext(map);
                }
                emitter.onComplete();
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<MapBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable= d;
                            showLoading();
                        }

                        @Override
                        public void onNext(MapBean mapBean) {

                            if(!TextUtils.isEmpty(mapBean.getKeyName())){
                                mPresenter.freshViewData();
                            }else {
                                emptyView();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            errorView();
                            mDisposable.dispose();
                        }

                        @Override
                        public void onComplete() {
                            mDisposable.dispose();
                        }
                    });


        }else {
            errorView();
        }
    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }


    public static void launchActivity(Context context, String key) {
        Intent intent = new Intent(context,ShowActivity.class);
        intent.putExtra(MAP_KEY_ARG,key);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @OnClick({R.id.image_address, R.id.tv_address_copy, R.id.tv_address_info_true, R.id.tv_remark_modify, R.id.image_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_address:

                break;
            case R.id.tv_address_copy:
                mPresenter.setAddressToCopy();
                break;
            case R.id.tv_address_info_true:
                showMapDialog(mPresenter.getLocationInfo());
                break;
            case R.id.tv_remark_modify:


                break;
            case R.id.image_code:
               new QrCodeShowDialog().show(this,"签到二维码",mPresenter.getQrCodeInfo());
                break;
        }
    }

    @Override
    public void setDataInfo(MapBean data) {
        tvDataInfo.setText(GasAppUtils.getString(R.string.zhihu_map_title_name, data.getMapName()));
        tvAddressInfoTrue.setText(data.getLocationInfo());
        tvRemarkInfoTrue.setText(data.getNote());
    }

    @Override
    public void setQrCode(String data) {
        imageCode.setImageBitmap(QRCode.createQRCode(data, 200));
    }

    @Override
    public void successView() {
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        emptyView.setStatus(EmptyInterface.STATUS_LOADING);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void emptyView() {
        emptyView.setStatus(EmptyInterface.STATUS_EMPTY);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void errorView() {
        emptyView.setStatus(EmptyInterface.STATUS_FAIL);
        emptyView.setVisibility(View.VISIBLE);
    }


    private void showMapDialog(LocationBean bean) {

        if(bean==null){
            GasAppUtils.toast("数据错误");
            return;
        }
        if(bean.isInfoError()){
            GasAppUtils.toast("数据错误");
            return;
        }

        // 经度：116.44000 纬度： 39.93410
        new SelectMapDialog().show(this, map -> {
            switch (map) {
                case MAP_AMAP:
                    startActivity(LocationUtils.getAMapMapIntent(bean));
                    break;
                case MAP_BAIDU:
                    startActivity(LocationUtils.getBaiduMapIntent(bean));
                    break;
                case MAP_TECENT:
                    startActivity(LocationUtils.getTecentMapIntent(bean));
                    break;
                default:
                    //do nothing
                    break;
            }
        });

    }
}
