package com.gas.zhihu.ui.show;

import android.content.Intent;
import android.os.Bundle;
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
import com.gas.zhihu.dialog.SelectMapDialog;
import com.gas.zhihu.ui.show.di.DaggerShowComponent;
import com.gas.zhihu.ui.show.mvp.ShowContract;
import com.gas.zhihu.ui.show.mvp.ShowPresenter;
import com.gas.zhihu.utils.LocationUtils;
import com.lib.commonsdk.utils.GasAppUtils;
import com.lib.commonsdk.utils.QRCode;

import butterknife.BindView;
import butterknife.OnClick;

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
        mPresenter.freshViewData();

    }

    private void initView() {
        emptyView.getRefreshView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        emptyView.setStatus(EmptyInterface.STATUS_LOADING);
        emptyView.setVisibility(View.VISIBLE);

        titleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                killMyself();
            }
        });
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
                showMapDialog();
                break;
            case R.id.tv_remark_modify:

                break;
            case R.id.image_code:

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


    private void showMapDialog() {

        // 经度：116.44000 纬度： 39.93410
        LocationBean bean = new LocationBean();
        bean.dname = "东直门";
        bean.dlat = 39.93410d;
        bean.dlon = 116.44000d;

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
