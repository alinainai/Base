package trunk.doi.base.ui.activity.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

public class ApkDownloadListener implements DownloadListener {


    public Context mContext;

    public ApkDownloadListener(Context context){
        this.mContext=context;
    }

    @Override
    public void onDownloadStart(String url, String userAgent,
                                String contentDisposition, String mimetype, long contentLength) {
        //检测是下载apk
        if (url.endsWith(".apk")) {
            //通过uri与Intent来调用系统通知，查看进度
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if(mContext!=null){
                mContext.startActivity(intent);
            }
        }
    }

}
