package com.base.paginate.base.status.footer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.base.paginate.R;
import com.base.paginate.base.status.IStatus;
import com.base.paginate.base.status.footer.AbLoadMoreFooter;

import static com.base.paginate.base.status.IStatus.STATUS_LOADING;

@SuppressLint("ViewConstructor")
public class DefaultLoadMoreFooter extends AbLoadMoreFooter {

    private TextView mTextView;
    private ProgressBar mProgressBar;
    private int mLoadMoreStatus;

    @IStatus.StatusType
    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    @Override
    public int getLayoutID() {
        return R.layout.paginate_layout_load_footer;
    }


    public DefaultLoadMoreFooter(Context context) {
        super(context);
        mTextView = findViewById(R.id.base_empty_tv);
        mProgressBar = findViewById(R.id.progress_bar);
        setStatus(STATUS_LOADING);//初始化为loading状态
    }

    @SuppressLint("SwitchIntDef")
    public void setStatus(@IStatus.StatusType int status) {

        if (mLoadMoreStatus == status)
            return;
        mLoadMoreStatus = status;

        boolean show = true;
        switch (status) {
            case IStatus.STATUS_LOADING://加载中
                mTextView.setText(R.string.str_base_adapter_loading);
                show = true;
                break;
            case IStatus.STATUS_FAIL://加载失败
                mTextView.setText(R.string.str_base_adapter_normal);
                show = false;
                break;
            case IStatus.STATUS_END://全部加载
                mTextView.setText(R.string.str_base_adapter_end);
                show = false;
                break;
        }
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }


}
