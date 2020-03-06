package com.base.paginate.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.base.paginate.R;
import com.base.paginate.Utils;
import com.base.paginate.interfaces.FooterInterface;


public class DefaultLoadMoreFooter extends LinearLayout implements FooterInterface {

    private TextView mTextView;
    private ProgressBar mProgressBar;
    private int mLoadMoreStatus;

    public DefaultLoadMoreFooter(Context context) {
        super(context);
        init();
    }

    public DefaultLoadMoreFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DefaultLoadMoreFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dpToPx(getContext(), 50)));
        LayoutInflater.from(getContext()).inflate(getLayoutID(), this, true);
        setBackgroundColor(Color.WHITE);

        mTextView = findViewById(R.id.base_empty_tv);
        mProgressBar = findViewById(R.id.progress_bar);
        setStatus(STATUS_PRE_LOADING);//初始化为loading状态

    }


    @Override
    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    @Override
    public int getLayoutID() {
        return R.layout.paginate_layout_load_footer;
    }

    @Override
    public void setStatus(int status) {

        if (mLoadMoreStatus == status)
            return;
        mLoadMoreStatus = status;

        boolean show = true;
        switch (status) {
            case FooterInterface.STATUS_PRE_LOADING://开始进行加载布局
                mTextView.setText(R.string.str_base_adapter_loading);
                show = true;
                break;
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
