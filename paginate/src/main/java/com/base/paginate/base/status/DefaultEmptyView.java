package com.base.paginate.base.status;

import android.content.Context;
import android.widget.TextView;

import com.base.paginate.R;

import static com.base.paginate.base.status.IStatus.STATUS_EMPTY;
import static com.base.paginate.base.status.IStatus.STATUS_FAIL;
import static com.base.paginate.base.status.IStatus.STATUS_LOADING;

public class DefaultEmptyView extends AbEmptytView {

    private TextView mTextView;
    private int mLoadMoreStatus;

    public DefaultEmptyView(Context context) {
        super(context);
        mTextView = findViewById(R.id.base_empty_tv);
        setStatus(STATUS_LOADING);
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
                mTextView.setText(R.string.str_base_adapter_end);
                break;
            case IStatus.STATUS_END:
                throw new IllegalArgumentException("The state of EmptyView is not support!");
        }

    }
}
