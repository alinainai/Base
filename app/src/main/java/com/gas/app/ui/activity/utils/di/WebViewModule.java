/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gas.app.ui.activity.utils.di;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.base.lib.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import com.gas.app.ui.activity.utils.mvp.WebViewContract;
import com.gas.app.BuildConfig;
import com.gas.app.ui.activity.utils.ApkDownloadListener;

/**
 * ================================================
 * 展示 Module 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.5">Module wiki 官方文档</a>
 * Created by JessYan on 09/04/2016 11:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Module
public abstract class WebViewModule {


    @SuppressLint("SetJavaScriptEnabled")
    @ActivityScope
    @Provides
    static WebView provideWebView(WebViewContract.View view, ApkDownloadListener downloadListener) {


        WebView mWebView = new WebView(view.getWrapContext());
        mWebView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

        WebSettings settings = mWebView.getSettings();
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //自适应屏幕

        settings.setTextZoom(90);
        settings.setDomStorageEnabled(true);  //开启DOM
        settings.setDefaultTextEncodingName("utf-8"); //设置编码
        settings.setAllowFileAccess(true);// 支持文件流
        settings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
        settings.setLoadWithOverviewMode(true);// 调整到适合webview大小
        //打开页面时， 自适应屏幕：
        settings.setSupportZoom(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.LOAD_DEFAULT);
            settings.setBlockNetworkImage(false);
        }
        mWebView.setDownloadListener(downloadListener);


        return mWebView;
    }

    @ActivityScope
    @Provides
    static ApkDownloadListener provideApkDownloadListener(WebViewContract.View view) {
        return new ApkDownloadListener(view.getWrapContext());
    }


}
