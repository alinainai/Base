package trunk.doi.base.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import trunk.doi.base.R;

/**
 * USer  Administrator ljx
 * Date  2018/2/8
 * Email 569932357@qq.com
 * Description :
 */

public abstract class MorePopupWindow  {

    private PopupWindow mPopupWindow;
    private Context mContext;
    private LinearLayout pop_1_tv;
    private LinearLayout pop_2_tv;
    private LinearLayout pop_3_tv;

    public abstract void share();
    public abstract void collection();
    public abstract void copyLink();

    public MorePopupWindow(Context context) {
        mContext = context;
        if (mPopupWindow == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_more, null);

            pop_1_tv= view.findViewById(R.id.ll_collection);
            pop_2_tv= view.findViewById(R.id.ll_share);
            pop_3_tv= view.findViewById(R.id.ll_linked);

            pop_1_tv.setOnClickListener(view1 -> {
                collection();
                dismiss();
            });
            pop_2_tv.setOnClickListener(view12 -> {
                share();
                dismiss();
            });
            pop_3_tv.setOnClickListener(view13 -> {
                copyLink();
                dismiss();
            });

            mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.alpha_color)));
            mPopupWindow.setOutsideTouchable(true);//设置outside可点击
            mPopupWindow.setFocusable(true);
        }

    }

    public void showAsDropDown(View author , int xoff, int off) {
        if (mPopupWindow != null) {
            if(!mPopupWindow.isShowing()){
                mPopupWindow.showAsDropDown(author,xoff,off);
            }
        }
    }


    public void dismiss() {
        if (mPopupWindow != null) {
            if(mPopupWindow.isShowing()){
                mPopupWindow.dismiss();
            }
        }
    }




}
