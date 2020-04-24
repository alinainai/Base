package com.base.paginate.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.base.paginate.R;
import com.base.paginate.interfaces.EmptyInterface;

public class DefaultEmptyView extends LinearLayout implements EmptyInterface {

    private TextView mTextView;
    private int mLoadMoreStatus;

    public DefaultEmptyView(Context context) {
        super(context);
        init();
    }

    public DefaultEmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DefaultEmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(getContext()).inflate(getLayoutID(), this, true);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);
        setBackgroundColor(Color.WHITE);
        mTextView = findViewById(R.id.base_empty_tv);
        setStatus(EmptyInterface.STATUS_LOADING);

    }


    @Override
    public int getLayoutID() {
        return R.layout.paginate_layout_status_view;
    }

    @Override
    public void setStatus(int status) {

        if (mLoadMoreStatus == status)
            return;
        mLoadMoreStatus = status;
        switch (status) {
            case STATUS_LOADING://加载中
                mTextView.setText(R.string.str_base_adapter_loading);
                break;
            case STATUS_FAIL://加载失败
                mTextView.setText(R.string.str_base_adapter_normal);
                break;
            case STATUS_EMPTY://数据为空
                mTextView.setText(R.string.str_base_adapter_empty);
                break;
            case STATUS_NETWORK_FAIL://网络异常，加载失败
                mTextView.setText(R.string.str_base_adapter_network_fail);
                break;
        }

    }

    @Override
    public View getRefreshView() {
        return mTextView;
    }
}
