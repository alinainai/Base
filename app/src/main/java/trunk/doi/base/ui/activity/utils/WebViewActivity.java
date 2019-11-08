package trunk.doi.base.ui.activity.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.baseui.view.StatusLine;
import com.base.baseui.view.TitleView;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.lib.commonsdk.constants.RouterHub;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import timber.log.Timber;
import trunk.doi.base.BuildConfig;
import trunk.doi.base.R;
import trunk.doi.base.bean.CollectionBean;
import trunk.doi.base.dialog.MorePopupWindow;
import trunk.doi.base.gen.DatabaseService;
import trunk.doi.base.util.StatusBarUtils;
import trunk.doi.base.util.ToastUtil;

import static com.lib.commonsdk.constants.Constants.PUBLIC_TITLE;
import static com.lib.commonsdk.constants.Constants.PUBLIC_URL;

@Route(path = RouterHub.APP_WEBVIEWACTIVITY)
public class WebViewActivity extends BaseActivity {


    @BindView(R.id.progressbar)
    ProgressBar bar;

    @BindView(R.id.rl_webview)
    RelativeLayout rl_webview;

    private WebView webView;
    private String url;
    private String mTitle;
    private DatabaseService service;
    private TitleView mainCartTitle;

    @Override
    public void getTitleView(TitleView titleView) {
        mainCartTitle = titleView;
        titleView.setRightText("更多");
        titleView.setCloseHide(true);
        titleView.setOnBackListener(v -> {
            if (webView != null) {
                if (webView.canGoBack()) {
                    webView.goBack();// 返回前一个页面
                    return;
                }
            }
            finish();
        });

        titleView.setOnRightListener(v -> {

            new MorePopupWindow(mContext) {

                @Override
                public void share() {

                }

                @Override
                public void collection() {

                    if (null != service.query(url)) {
                        ToastUtil.showCustomToast(mContext, "已添加过收藏", ToastUtil.TOAST_OF_WARING);
                    } else {
                        CollectionBean bean = new CollectionBean();
                        bean.setUrl(url);
                        bean.setDesc(mTitle);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
                        bean.setDataTime(sdf.format(new Date()));
                        service.addInfo(bean);
                        ToastUtil.showCustomToast(mContext, "收藏成功", ToastUtil.TOAST_OF_SUCCESS);
                    }
                }

                @Override
                public void copyLink() {

                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", url);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.showCustomToast(mContext, "地址复制成功", ToastUtil.TOAST_OF_SUCCESS);

                }
            }.showAsDropDown(mainCartTitle.getRightView(), 2, 0);


        });
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void getStatusBarHeight(StatusLine statusBar) {
        super.getStatusBarHeight(statusBar);
        if (StatusBarUtils.setStatusBarLightMode(mContext)) {
            statusBar.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            statusBar.setBackgroundColor(getResources().getColor(R.color.black));
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {


        url = getIntent().getStringExtra(PUBLIC_URL);//地址
        mTitle = getIntent().getStringExtra(PUBLIC_TITLE);//标题
        if (!TextUtils.isEmpty(mTitle)) {
            String title = null;
            if (mTitle.length() >= 7) {
                title = mTitle.substring(0, 7) + "...";
            } else {
                title = mTitle;
            }
            mainCartTitle.setTitleText(title);
        }
        webView = new WebView(mContext);
        webView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        rl_webview.addView(webView);
    }

    @Override
    public void setListener() {

        webView.setWebChromeClient(new WebChromeClient() {

            //设置进度条
            @Override
            public void onProgressChanged(WebView view, int progress) {

                if (!mContext.isDestroyed()) {
                    if (progress == 100) {
                        bar.setVisibility(View.GONE);
                    } else {
                        bar.setVisibility(View.VISIBLE);
                        bar.setProgress(progress);
                    }
                }
                super.onProgressChanged(view, progress);
            }

            //设置标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (!mContext.isDestroyed()) {
                    if (TextUtils.isEmpty(mTitle)) {
                        if (!TextUtils.isEmpty(title)) {
                            if (null != mainCartTitle) {
                                mainCartTitle.setTitleText(title);
                                mTitle = title;
                            }
                        }
                    }
                }
                super.onReceivedTitle(view, title);
            }
        });
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {


                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                WebBackForwardList webBackForwardList = webView.copyBackForwardList();
                Timber.tag(TAG).e(webBackForwardList.getSize() + "");
//                WebHistoryItem currentItem = webBackForwardList.getCurrentItem();
//                if(webBackForwardList.getSize()>0){
//
//                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

        });

        WebSettings settings = webView.getSettings();
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
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.LOAD_DEFAULT);
            settings.setBlockNetworkImage(false);
        }
        // 禁用硬件加速
        //  webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.setDownloadListener(new GQDownloadListener());
    }

    @Override
    public void initData() {
        service = new DatabaseService(mContext);
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {

        if (webView != null) {
            if (webView.canGoBack()) {
                webView.goBack();// 返回前一个页面
                return;
            }
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再destory()
            try {
                ViewParent parent = webView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(webView);
                }
                // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                webView.clearHistory();
                webView.getSettings().setJavaScriptEnabled(false);
                webView.removeAllViews();
                webView.destroy();
                webView = null;
            } catch (Exception ex) {

            }
        }
        StatusBarUtils.setStatusBarDarkMode(mContext);
        service.closeDatabase();
        super.onDestroy();
    }


    public class GQDownloadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            //检测是下载apk
            if (url.endsWith(".apk")) {
                //通过uri与Intent来调用系统通知，查看进度
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }

    }
}
