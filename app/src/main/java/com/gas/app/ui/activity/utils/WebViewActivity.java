package com.gas.app.ui.activity.utils;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.baseui.view.TitleView;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.lib.commonsdk.constants.RouterHub;
import com.lib.commonsdk.utils.statusbar.StatusBarManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import com.gas.app.bean.CollectionBean;
import com.gas.app.dialog.MorePopupWindow;
import com.gas.app.gen.DatabaseService;
import com.gas.app.ui.activity.utils.mvp.WebViewContract;
import com.gas.app.ui.activity.utils.mvp.WebViewPresenter;
import com.gas.app.utils.AppMoudleUtil;
import com.gas.app.utils.ToastUtil;
import com.gas.app.R;
import com.gas.app.ui.activity.utils.di.DaggerWebViewComponent;

import static com.lib.commonsdk.constants.Constants.PUBLIC_TITLE;
import static com.lib.commonsdk.constants.Constants.PUBLIC_URL;

@Route(path = RouterHub.APP_WEBVIEWACTIVITY)
public class WebViewActivity extends BaseActivity<WebViewPresenter> implements WebViewContract.View {


    @BindView(R.id.progressbar)
    ProgressBar mBar;

    @BindView(R.id.rl_webview)
    RelativeLayout mRLWebview;

    @BindView(R.id.v_title)
    TitleView mainCartTitle;

    @Inject
    WebView mWebView;

    private String url;
    private String mTitle;
    private DatabaseService service;
    private AppCompatActivity mContext;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

        DaggerWebViewComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_webview;
    }


    public void initTitleView() {
        mainCartTitle.setRightText("更多");
        mainCartTitle.setCloseHide(true);
        mainCartTitle.setOnBackListener(v -> {
            if (mWebView != null) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();// 返回前一个页面
                    return;
                }
            }
            finish();
        });

        mainCartTitle.setOnRightListener(v -> {

            new MorePopupWindow(this) {

                @Override
                public void share() {

                }

                @Override
                public void collection() {

                    if (null != service.query(url)) {
                        ToastUtil.show(mContext, "已添加过收藏");
                    } else {

                        CollectionBean bean = new CollectionBean();
                        bean.setUrl(url);
                        bean.setDesc(mTitle);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
                        bean.setDataTime(sdf.format(new Date()));
                        service.addInfo(bean);
                        ToastUtil.show(mContext, "收藏成功");
                    }
                }

                @Override
                public void copyLink() {
                    AppMoudleUtil.copyData(mContext, url);
                    ToastUtil.show(mContext, "地址复制成功");
                }
            }.showAsDropDown(mainCartTitle.getRightView(), 2, 0);


        });
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mContext = this;



        initTitleView();

        url = getIntent().getStringExtra(PUBLIC_URL);//地址
        mTitle = getIntent().getStringExtra(PUBLIC_TITLE);//标题


        AppMoudleUtil.setUrlTitle(mainCartTitle, mTitle);
        mRLWebview.addView(mWebView);

        mWebView.setWebChromeClient(new WebChromeClient() {

            //设置进度条
            @Override
            public void onProgressChanged(WebView view, int progress) {

                if (!mContext.isDestroyed()) {
                    if (progress == 100) {
                        mBar.setVisibility(View.GONE);
                    } else {
                        mBar.setVisibility(View.VISIBLE);
                        mBar.setProgress(progress);
                    }
                }
                super.onProgressChanged(view, progress);
            }

            //设置标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (!mContext.isDestroyed()) {
                    if (TextUtils.isEmpty(mTitle)) {
                        AppMoudleUtil.setUrlTitle(mainCartTitle, mTitle);
                        mTitle = title;
                    }
                }
                super.onReceivedTitle(view, title);
            }
        });
        service = new DatabaseService(mContext);
        mWebView.loadUrl(url);

    }


    @Override
    public void onBackPressed() {

        if (mWebView != null) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();// 返回前一个页面
                return;
            }
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        AppMoudleUtil.removeWebView(mWebView);
        StatusBarManager.setStatusBarDarkMode(mContext);
        service.closeDatabase();
        super.onDestroy();
    }


    @Override
    public Context getWrapContext() {
        return this;
    }



    @Override
    public void showMessage(@NonNull String message) {

    }
}
