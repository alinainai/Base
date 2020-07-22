package com.gas.app.config

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import timber.log.Timber

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
@Interceptor(priority = 8, name = "RouterInterceptor")
class RouterInterceptor : IInterceptor {
    private var mContext: Context? = null
    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        Timber.i("RouterInterceptor: %s", postcard.path)
        callback.onContinue(postcard)
        //这里示例的意思是, 如果用户没有登录就只能进入登录注册等划分到 ACCOUNT 分组的页面, 用户进入其他页面将全部被拦截
//        if (postcard.getGroup().equals(RouterHub.ACCOUNT.split("/")[1])
//            callback.onContinue(postcard);
//        } else {
//            callback.onInterrupt(new Exception("用户没有登陆"));
//        }
    }

    override fun init(context: Context) {
        mContext = context
    }
}