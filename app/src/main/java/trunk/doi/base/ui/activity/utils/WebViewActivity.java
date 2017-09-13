package trunk.doi.base.ui.activity.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.util.ToastUtils;

public class WebViewActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.progressbar)
    ProgressBar bar;
    @BindView(R.id.webview)
    WebView webView;


    private String url;
    private String mTitle;



    @Override
    protected int initLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        url = getIntent().getStringExtra("url");//地址
        mTitle = getIntent().getStringExtra("title");//标题
        if(!TextUtils.isEmpty(mTitle)){
            mainCartTitle.setText(mTitle);
        }
    }

    @Override
    public void setListener() {


        mainCartTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //获取剪贴板管理器：
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建普通字符型ClipData
                        ClipData mClipData = ClipData.newPlainText("Label", url);
                        // 将ClipData内容放到系统剪贴板里。
                        cm.setPrimaryClip(mClipData);
                        ToastUtils.showShort(context,"地址复制成功");
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
                if (progress == 100) {
                    bar.setVisibility(View.GONE);
                } else {
                    try{
                        bar.setVisibility(View.VISIBLE);
                        bar.setProgress(progress);
                    }catch (NullPointerException e){

                    }
                }
                super.onProgressChanged(view, progress);
            }

            //设置标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if(TextUtils.isEmpty(mTitle)){
                    if(!TextUtils.isEmpty(title))
                        mainCartTitle.setText(title);
                }
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
        //   settings.setDomStorageEnabled(true);  //开启DOM
        //   settings.setDefaultTextEncodingName("utf-8"); //设置编码
        //  settings.setAllowFileAccess(true);// 支持文件流
        // settings.setUseWideViewPort(true);// 调整到适合webview大小
        //   settings.setLoadWithOverviewMode(true);// 调整到适合webview大小
        //    settings.setBlockNetworkImage(true); //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        //   String t = Uri.parse(url).getQueryParameter("title");

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
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {

        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }

    @OnClick({R.id.img_back,R.id.main_cart_title,R.id.img_refresh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                break;
            case R.id.main_cart_title:

                break;
            case R.id.img_refresh:
                webView.reload();
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
