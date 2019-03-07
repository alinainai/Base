package trunk.doi.base.dialog;

import android.app.*;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;
import trunk.doi.base.R;
import trunk.doi.base.view.RoundProgressBar;


public abstract class ApkDownDialog {

    private TextView success_text_view;
    private RoundProgressBar round_progress;
    protected Dialog dialog;

    public abstract void sure();

    /**
     * apk升级版本使用
     *
     * @param activity
     * @param url
     * @param forceUpdate //是否强制升级
     */
    public ApkDownDialog(final Activity activity, final String url, final boolean forceUpdate) {
        if (dialog == null) {
            View view = activity.getLayoutInflater().inflate(
                    R.layout.dialog_download, null);
            success_text_view =  view.findViewById(R.id.success_text_view);
            round_progress =  view.findViewById(R.id.round_progress);
            OkHttpUtils//
                    .get()//
                    .url(url)//
                    .build()//
                    .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "gson-2.2.1.jar")//
                    {
                        @Override
                        public void onBefore(Request request, int id) {
                        }

                        @Override
                        public void inProgress(float progress, long total, int id) {
                            round_progress.setProgress((int) (100 * progress));
                        }

                        @Override
                        public void onError(Call request, Exception e, int a) {
                            dismiss();
                        }

                        @Override
                        public void onResponse(File file, int a) {
                            sure();
                            dismiss();
                        }
                    });


            dialog = new Dialog(activity, R.style.custom_dialog);
            dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            dialog.setCancelable(false);
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.CENTER); // 设置生效
        }
    }

    public void show() {

        if (isShowing()) {
            return;
        }
        dialog.show();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void dismiss() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}