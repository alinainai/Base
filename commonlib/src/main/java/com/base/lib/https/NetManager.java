package com.base.lib.https;

import android.content.Context;

import com.base.lib.util.ArmsUtils;

import okhttp3.OkHttpClient;


public class NetManager {

    public static NetManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final NetManager INSTANCE = new NetManager();
    }

    private NetManager() {
    }

    public <S> S create(Class<S> service, Context context) {
        return ArmsUtils.getAppComponent(context).retrofitProvide().create(service);
    }

    private OkHttpClient getOkHttpClient(Context context) {
        return ArmsUtils.getAppComponent(context).okHttpClient();
    }


}
