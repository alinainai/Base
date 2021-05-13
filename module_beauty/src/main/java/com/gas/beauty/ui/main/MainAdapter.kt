package com.gas.beauty.ui.main

import android.content.Context
import com.base.lib.di.component.AppComponent
import com.base.lib.https.image.ImageLoader
import com.base.lib.util.ArmsUtils
import com.base.paginate.base.SingleAdapter
import com.base.paginate.viewholder.PageViewHolder
import com.gas.beauty.R
import com.gas.beauty.bean.BeautyBean
import com.lib.commonsdk.glide.ImageConfigImpl

class MainAdapter(context: Context) : SingleAdapter<BeautyBean>(context) {
    private val mAppComponent: AppComponent = ArmsUtils.obtainAppComponentFromContext(context)

    /**
     * 用于加载图片的管理类, 默认使用 Glide, 使用策略模式, 可替换框架
     */
    private val mImageLoader: ImageLoader = mAppComponent.imageLoader()
    override fun convert(holder: PageViewHolder, data: BeautyBean, position: Int) {
        mImageLoader.loadImage(mContext,
                ImageConfigImpl
                        .builder()
                        .url(data.url)
                        .imageView(holder.getView(R.id.iv_avatar))
                        .build())

//        ImageLoader.loadSet(mContext,data.getUrl(),holder.getView(R.id.iv_avatar),R.mipmap.public_ic_launcher);
    }

    override fun getItemLayoutId(): Int {
        return R.layout.gank_recycle_list
    }

    init {
        mContext = context
    }
}