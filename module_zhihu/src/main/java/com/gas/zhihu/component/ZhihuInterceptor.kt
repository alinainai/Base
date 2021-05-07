package com.gas.zhihu.component

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor

/**
 * ================================================
 * 声明 [ARouter] 拦截器, 可以根据需求自定义拦截逻辑, 比如用户没有登录就拦截其他页面
 *
 *
 * Created by JessYan on 08/08/2017 15:54
 * Contact with jess.yan.effort@gmail.com
 * Follow me on https://github.com/JessYanCoding
 * ================================================
 */
@Interceptor(priority = 7, name = "ZhihuInterceptor")
class ZhihuInterceptor : IInterceptor {
    private var mContext: Context? = null
    override fun process(postcard: Postcard, callback: InterceptorCallback) {


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
        callback.onContinue(postcard)
    }

    override fun init(context: Context) {
        mContext = context
    }
}