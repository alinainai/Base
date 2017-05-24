package trunk.doi.base.util;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import trunk.doi.base.R;


public class Loading {

    private static Dialog loading;
    private static Context mContext;

    public static void init(Context context){
        mContext=context;
    }

    public static void show() {
        if (loading == null) {
            loading = new AlertDialog.Builder(mContext, R.style.custom_dialog).create();
            loading.setCanceledOnTouchOutside(false);
            Window w = loading.getWindow();
          //  w.setWindowAnimations(R.style.DialogBottom); // 添加动画
            w.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            loading.show();
            w.setContentView(R.layout.activity_loading);
//           //   自定义动画
//            	ImageView proImageView=(ImageView) w.findViewById(R.id.progressBar);
//            	proImageView.setBackgroundResource(R.drawable.loading);
//            	AnimationDrawable animation = (AnimationDrawable) proImageView.getBackground();
//            	animation.start();
        }
        loading.show();
    }

    public static void dismiss() {

        if (loading != null && loading.isShowing()) {
            loading.dismiss();
        }
    }

    public static boolean isShowing() {

        if (loading != null) {
            return loading.isShowing();
        }
        return false;
    }

    public static void destroy() {
        dismiss();
        if (loading != null) {
            loading = null;
        }

    }


}
