package com.gas.zhihu.component;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gas.zhihu.BuildConfig;
import com.lib.commonsdk.constants.RouterHub;

/**
 * ================================================
 * 声明 {@link ARouter} 拦截器, 可以根据需求自定义拦截逻辑, 比如用户没有登录就拦截其他页面
 * <p>
 * Created by JessYan on 08/08/2017 15:54
 * Contact with jess.yan.effort@gmail.com
 * Follow me on https://github.com/JessYanCoding
 * ================================================
 */
@Interceptor(priority = 7, name = "ZhihuInterceptor")
public class ZhihuInterceptor implements IInterceptor {
    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

        //模拟登录拦截
//        if (RouterHub.APP_WEBVIEWACTIVITY.equals(postcard.getPath())) {
//            ARouter.getInstance()
//                    .build(RouterHub.ZHIHU_DETAILACTIVITY)
//                    .with(postcard.getExtras())
//                    .greenChannel()
//                    .navigation(mContext);
//        } else {
//            callback.onContinue(postcard);
//        }
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
