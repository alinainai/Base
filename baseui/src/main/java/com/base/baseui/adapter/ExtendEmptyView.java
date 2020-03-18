package com.base.baseui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.base.baseui.R;
import com.base.baseui.view.QMUILoadingView;
import com.base.paginate.interfaces.EmptyInterface;

public class ExtendEmptyView extends ConstraintLayout implements EmptyInterface {


    private TextView mTextView;
    private ImageView mImg;
    private Button mBtn;
    private QMUILoadingView mLoading;
    private int mLoadMoreStatus;

    private @DrawableRes
    int mDataEmptyRes = R.mipmap.baseui_adapter_data_empty;

    public int getDataEmptyRes() {
        return mDataEmptyRes;
    }

    public void setDataEmptyRes(int mDataEmptyRes) {
        this.mDataEmptyRes = mDataEmptyRes;
    }

    public String getDataEmptyStr() {
        return mDataEmptyStr;
    }

    public void setDataEmptyStr(String mDataEmptyStr) {
        this.mDataEmptyStr = mDataEmptyStr;
    }

    private String mDataEmptyStr = "暂时没有数据哦！";


    public ExtendEmptyView(Context context) {
        super(context);
        init();
    }

    public ExtendEmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExtendEmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {


        LayoutInflater.from(getContext()).inflate(getLayoutID(), this, true);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);
        setBackgroundColor(Color.WHITE);
        mTextView = findViewById(R.id.tv_tile);
        mImg = findViewById(R.id.imageView);
        mBtn = findViewById(R.id.btn_refresh);
        mLoading = findViewById(R.id.loading);
        setStatus(EmptyInterface.STATUS_LOADING);

    }


    @Override
    public int getLayoutID() {
        return R.layout.public_adapter_layout_empty;
    }

    @Override
    public void setStatus(int status) {

        if (mLoadMoreStatus == status)
            return;
        mLoadMoreStatus = status;
        switch (status) {
            case STATUS_LOADING://加载中
                mLoading.setVisibility(VISIBLE);
                mImg.setVisibility(GONE);
                mBtn.setVisibility(GONE);
                mTextView.setText("数据加载中...");
                break;
            case STATUS_FAIL://加载失败
                mLoading.setVisibility(GONE);
                mImg.setVisibility(VISIBLE);
                mImg.setImageResource(R.mipmap.baseui_adapter_data_load_fail);
                mBtn.setVisibility(VISIBLE);
                mTextView.setText("数据加载失败，请刷新重试");
                break;
            case STATUS_EMPTY://数据为空
                mLoading.setVisibility(GONE);
                mImg.setVisibility(VISIBLE);
                mBtn.setVisibility(GONE);
                mImg.setImageResource(getDataEmptyRes());
                mTextView.setText(getDataEmptyStr());
                break;
            case STATUS_NETWORK_FAIL://网络异常
                mLoading.setVisibility(GONE);
                mImg.setVisibility(VISIBLE);
                mImg.setImageResource(R.mipmap.baseui_adapter_network_fail);
                mBtn.setVisibility(VISIBLE);
                mTextView.setText("网络异常，请刷新重试");
                break;
        }

    }

    @Override
    public View getRefreshView() {
        return mBtn;
    }


}
