package com.gas.zhihu.ui.detial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.gas.zhihu.R
import com.lib.commonsdk.constants.RouterHub
import kotlinx.android.synthetic.main.zhihu_activity_details.*

@Route(path = RouterHub.ZHIHU_DETAILACTIVITY)
class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zhihu_activity_details)
        ButterKnife.bind(this)
        initView()
//        val title = intent.getStringExtra(Constants.PUBLIC_TITLE)
//        val url = intent.getStringExtra(Constants.PUBLIC_URL)
        //        tvTitle.setText(getResources().getString(R.string.zhihu_title_format, title));
//        tvUrl.setText(getResources().getString(R.string.zhihu_url_format, url));
    }

    private fun initView(){
        titleView.setOnBackListener{
            this.finish()
        }
        Glide.with(this).load(R.mipmap.zhihu_search_rule).into(wrapPhotoView)
    }


//    @OnClick(R2.id.tv_title, R2.id.tv_url, R2.id.tv_delete)
//    fun onViewClicked(view: View) {
//        val id = view.id
//        if (id == R.id.tv_title) {
//            for (i in 0..4) {
//                val map = MapBean()
//                map.mapName = "dh00$i"
//                map.keyName = "dh00$i"
//                map.locationInfo = "东直门oo$i"
//                map.pathName = "dh00$i.jpg"
//                //                经度：116.44000 纬度： 39.93410
//                map.longitude = 116.44000
//                map.latitude = 39.93410
//                MapBeanDbUtils.insertMapBean(map)
//            }
//        } else if (id == R.id.tv_delete) {
//            val beans = MapBeanDbUtils.queryAllMapData()
//            if (beans != null) {
//                Timber.e(GsonUtils.toJson(beans))
//                for (bean in beans) {
//                    Timber.e("MapBean" + bean.keyName + bean.note + bean.locationInfo)
//                }
//            }
//        } else if (id == R.id.tv_url) {
//            MapBeanDbUtils.deleteAll()
//        }
//    }
}