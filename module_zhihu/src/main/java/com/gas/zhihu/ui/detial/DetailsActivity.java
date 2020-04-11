package com.gas.zhihu.ui.detial;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gas.zhihu.R;
import com.gas.zhihu.R2;
import com.gas.zhihu.bean.MapBean;
import com.gas.zhihu.utils.MapBeanDbUtils;
import com.lib.commonsdk.constants.Constants;
import com.lib.commonsdk.constants.RouterHub;
import com.lib.commonsdk.utils.GsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;


@Route(path = RouterHub.ZHIHU_DETAILACTIVITY)
public class DetailsActivity extends AppCompatActivity {

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_url)
    TextView tvUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_activity_details);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra(Constants.PUBLIC_TITLE);
        String url = getIntent().getStringExtra(Constants.PUBLIC_URL);
        tvTitle.setText(getResources().getString(R.string.zhihu_title_format, title));
        tvUrl.setText(getResources().getString(R.string.zhihu_url_format, url));
    }

    @OnClick({R2.id.tv_title, R2.id.tv_url})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_title) {

            for (int i = 0; i < 5; i++) {

                MapBean map = new MapBean();
                map.setMapName("dh00"+i);
                map.setKeyName("dh00"+i);
                map.setLocationInfo("东直门oo"+i);
                map.setPathName("dh00"+i+".jpg");
//                经度：116.44000 纬度： 39.93410
                map.setLongitude(116.44000);
                map.setLatitude(39.93410);
                MapBeanDbUtils.insertMapBean(map);

            }


        } else if (id == R.id.tv_url) {
          List<MapBean> beans= MapBeanDbUtils.queryAllMapData();
          if(beans!=null ){

              Timber.e( GsonUtils.toJson(beans));
              for (MapBean bean : beans) {
                  Timber.e("MapBean"+bean.getKeyName()+bean.getNote()+bean.getLocationInfo());
              }

          }


        }
    }
}
