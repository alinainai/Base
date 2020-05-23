package com.base.baseui.dialog.itempop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.baseui.R;
import com.base.baseui.dialog.fiterpop.TagAdapter;
import com.base.baseui.dialog.select.ISelectItem;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;


/**
 * ================================================
 * desc: 删选弹框通用
 * <p>
 * created by author ljx
 * Date  2020-01-31
 * email 569932357@qq.com
 * <p>
 * ================================================
 */

public abstract class ItemPopupWindow<T extends ISelectItem> {

    private PopupWindow mPopupWindow;
    private PopItemAdapter mTagAdapter;
    private List<T> mData;

    public ItemPopupWindow(Context context, List<T> data) {

        if (data == null || data.size() == 0)
            throw new RuntimeException("data is not null");

        mData = data;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.public_dialog_select_pop_layout, null);
        RecyclerView flowLayout = view.findViewById(R.id.recycler);

        mTagAdapter = new PopItemAdapter(data, this::onPositionClick);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        flowLayout.setLayoutManager(layoutManager);
        flowLayout.setAdapter(mTagAdapter);

        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        mPopupWindow.setOutsideTouchable(true);//设置outside可点击
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOnDismissListener(this::onPopDismiss);

    }

    public abstract void onPositionClick(@NotNull ISelectItem item, int position);

    public abstract void onPopDismiss();

    public void showAsDropDown(View author, String typeId) {

        if(mData.isEmpty())
            return;
        if (mPopupWindow != null) {
            if (mPopupWindow.isShowing()) {
                dismiss();
            }
            mTagAdapter.setSelected(typeId);
            mPopupWindow.showAsDropDown(author, 0, 0);

        }
    }

    private int getItemPosition(@NotNull String typeId) {

        for (ISelectItem t : mData) {
            if (t == null)
                continue;
            if (Objects.equals(t.getId(), typeId))
                return mData.indexOf(t);
        }
        return 0;

    }

    public void dismiss() {
        if (mPopupWindow != null) {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }
        }
    }


}
