package trunk.doi.base.ui.activity.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trunk.doi.base.BuildConfig;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.util.StatusBarUtils;
import trunk.doi.base.util.ToastUtil;

public class WebViewActivity extends BaseActivity {


    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.progressbar)
    ProgressBar bar;

    @BindView(R.id.rl_webview)
    RelativeLayout rl_webview;

    private WebView webView;

    private String url;
    private String mTitle;


    @Override
    protected int initLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        if (StatusBarUtils.setStatusBarLightMode(mContext)) {
            mStatusBar.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            mStatusBar.setBackgroundColor(getResources().getColor(R.color.black));
        }

        url = getIntent().getStringExtra("url");//地址
        mTitle = getIntent().getStringExtra("title");//标题
        if (!TextUtils.isEmpty(mTitle)) {
            mainCartTitle.setText(mTitle);
        }
        webView=new WebView(mContext);
        webView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        rl_webview.addView(webView);
    }

    @Override
    public void setListener() {


        mainCartTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //获取剪贴板管理器：
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建普通字符型ClipData
                        ClipData mClipData = ClipData.newPlainText("Label", url);
                        // 将ClipData内容放到系统剪贴板里。
                        cm.setPrimaryClip(mClipData);
                        ToastUtil.show(mContext, "地址复制成功");
                    }
                }).setTitle("提示")
                        .setMessage("是否复制当前网页地址到剪切板")
                        .show();
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            //设置进度条
            @Override
            public void onProgressChanged(WebView view, int progress) {

                if(!mContext.isDestroyed()){
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
                if(!mContext.isDestroyed()){
                    if (TextUtils.isEmpty(mTitle)) {
                        if (!TextUtils.isEmpty(title)) {
                            if (null != mainCartTitle) {
                                mainCartTitle.setText(title);
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
                return false;
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
        finishAnim();
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
                webView=null;
            } catch (Exception ex) {

            }
        }
        StatusBarUtils.setStatusBarDarkMode(mContext);
        super.onDestroy();
    }



    @OnClick({R.id.img_back, R.id.img_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:

                if (webView != null) {
                    if (webView.canGoBack()) {
                        webView.goBack();// 返回前一个页面
                        return;
                    }
                }
                finishAnim();

                break;
            case R.id.img_refresh:
                finishAnim();
                break;
        }
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
