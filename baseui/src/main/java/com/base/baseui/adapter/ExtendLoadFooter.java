package com.base.baseui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.base.baseui.R;
import com.base.baseui.view.QMUILoadingView;
import com.base.paginate.Utils;
import com.base.paginate.interfaces.FooterInterface;


public class ExtendLoadFooter extends LinearLayout implements FooterInterface {

    private TextView mTextView;
    private QMUILoadingView mProgressBar;
    private int mLoadMoreStatus;

    public ExtendLoadFooter(Context context) {
        super(context);
        init();
    }

    public ExtendLoadFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExtendLoadFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Utils.dpToPx(getContext(), 50)));
        LayoutInflater.from(getContext()).inflate(getLayoutID(), this, true);
        setBackgroundColor(Color.WHITE);

        mTextView = findViewById(R.id.base_empty_tv);
        mProgressBar = findViewById(R.id.loading);
        setStatus(STATUS_LOADING);//初始化为loading状态

    }


    @Override
    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    @Override
    public int getLayoutID() {
        return R.layout.public_adapter_layout_footer;
    }

    @Override
    public void setStatus(int status) {

        if (mLoadMoreStatus == status)
            return;
        mLoadMoreStatus = status;

        boolean show = true;
        switch (status) {
            case FooterInterface.STATUS_LOADING://加载中
                mTextView.setText(R.string.str_base_adapter_loading);
                show = true;
                break;
            case FooterInterface.STATUS_FAIL://加载失败
                mTextView.setText(R.string.str_base_adapter_normal);
                show = false;
                break;
            case FooterInterface.STATUS_END://全部加载
                mTextView.setText(R.string.str_base_adapter_end);
                show = false;
                break;
        }
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);

    }


}
