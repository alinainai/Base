package com.gas.beauty.ui.main

import android.content.Context
import com.base.lib.di.component.AppComponent
import com.base.lib.https.image.ImageLoader
import com.base.lib.https.image.aes.ImgBean
import com.base.lib.util.ArmsUtils
import com.base.paginate.base.SingleAdapter
import com.base.paginate.viewholder.PageViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.gas.beauty.R
import com.gas.beauty.bean.BeautyBean


class MainAdapter(context: Context) : SingleAdapter<BeautyBean>(context) {
    private val mAppComponent: AppComponent = ArmsUtils.obtainAppComponentFromContext(context)

    /**
     * 用于加载图片的管理类, 默认使用 Glide, 使用策略模式, 可替换框架
     */
    private val mImageLoader: ImageLoader = mAppComponent.imageLoader()
    override fun convert(holder: PageViewHolder, data: BeautyBean, position: Int) {
//        mImageLoader.loadImage(mContext,
//                ImageConfigImpl
//                        .builder()
////                        .url(data.url)
//                        .url("https://p3.pstatp.com/large/6c2a0008d4bf2b6df897")
//                        .imageView(holder.getView(R.id.iv_avatar))
//                        .build())

        val requestOptions = RequestOptions()
        requestOptions.skipMemoryCache(true) //不加入内存缓存，默认会加入
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不加入磁盘缓存


      val key = "fake_key"
      val url = "fake_url"
        val ib =  ImgBean(url=url,secretkey = key,encrypt = "AES128")

        Glide.with(mContext)
                .load(ib)
//                .apply(requestOptions)
//                .signature( ObjectKey(ib))
                .placeholder(R.drawable.public_default_app_logo)
                .error(R.drawable.public_map_amap)
                .into(holder.getView(R.id.iv_avatar))

//        ImageLoader.loadSet(mContext,data.getUrl(),holder.getView(R.id.iv_avatar),R.mipmap.public_ic_launcher);
    }

    override fun getItemLayoutId(): Int {
        return R.layout.gank_recycle_list
    }

    init {
        mContext = context
    }
}