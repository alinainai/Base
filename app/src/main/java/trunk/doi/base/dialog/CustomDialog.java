package trunk.doi.base.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import trunk.doi.base.R;


public abstract class CustomDialog {

    //确定按钮
    private Button btn_sure;
    //取消按钮
    private ImageView img_close;
    private ImageView img_sheet;
    //上下文布局
    private Context context;
    protected Dialog dialog;

    public abstract void sure();


    /**
     * 提示签约的dialog
     * @param activity 当前界面
     */
    public CustomDialog(Activity activity, String dir) {
        this.context = activity;
        if (dialog == null) {
            View view = activity.getLayoutInflater().inflate(
                    R.layout.dialog_bank, null);
            btn_sure=(Button) view.findViewById(R.id.btn_open);
            img_close=(ImageView) view.findViewById(R.id.img_close);
            img_sheet=(ImageView) view.findViewById(R.id.img_sheet);

            Glide.with(activity)
                    .load(dir)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(img_sheet);

            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            btn_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sure();
                    dismiss();
                }
            });

            dialog = new Dialog(activity,R.style.custom_dialog );
            dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout. LayoutParams.WRAP_CONTENT));
            dialog.setCancelable(true);
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL); // 设置生效
        }
    }

    public void show() {

        if(isShowing()){
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
