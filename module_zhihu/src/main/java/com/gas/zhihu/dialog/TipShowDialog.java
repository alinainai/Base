package com.gas.zhihu.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.base.baseui.dialog.CommonDialog;
import com.gas.zhihu.R;

import org.jetbrains.annotations.NotNull;

public class TipShowDialog {


    public void show(Context context, @NotNull String title, @NotNull String info) {

        View view = LayoutInflater.from(context).inflate(R.layout.zhihu_dialog_forget_pwd, null);
        TextView tv_title = view.findViewById(R.id.dialog_title);
        TextView tv_info = view.findViewById(R.id.dialog_info);
        tv_title.setText(title);
        tv_info.setText(info);
        CommonDialog dialog = new CommonDialog.Builder(context)
                .setCustomView(view)
                .setCancelable(true)
                .create();
        view.findViewById(R.id.btn_sure).setOnClickListener(view1 -> {
            dialog.dismiss();
        });
        dialog.show();

    }


}
