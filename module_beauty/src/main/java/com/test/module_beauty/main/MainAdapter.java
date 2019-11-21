package com.test.module_beauty.main;

import android.content.Context;

import com.base.lib.di.component.AppComponent;
import com.base.lib.https.image.ImageLoader;
import com.base.lib.util.ArmsUtils;
import com.base.paginate.PageViewHolder;
import com.base.paginate.base.BaseAdapter;
import com.base.paginate.base.SingleAdapter;
import com.lib.commonsdk.glide.ImageConfigImpl;
import com.test.module_beauty.R;
import com.test.module_beauty.bean.GankItemBean;


public class MainAdapter extends SingleAdapter<GankItemBean> {


    private AppComponent mAppComponent;
    /**
     * 用于加载图片的管理类, 默认使用 Glide, 使用策略模式, 可替换框架
     */
    private ImageLoader mImageLoader;
    private Context mContext;




    public MainAdapter(Context context) {
        super(context);
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(context);
        mImageLoader=mAppComponent.imageLoader();
        mContext=context;
    }

    @Override
    protected void convert(PageViewHolder holder, GankItemBean data, int position) {

        mImageLoader.loadImage(mContext,
                ImageConfigImpl
                        .builder()
                        .url(data.getUrl())
                        .imageView(holder.getView(R.id.iv_avatar))
                        .build());

//        ImageLoader.loadSet(mContext,data.getUrl(),holder.getView(R.id.iv_avatar),R.mipmap.public_ic_launcher);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.gank_recycle_list;
    }
}
