package trunk.doi.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.base.paginate.base.status.AbEmptytView;


import trunk.doi.base.R;

import static com.base.paginate.base.status.IStatus.STATUS_EMPTY;
import static com.base.paginate.base.status.IStatus.STATUS_FAIL;
import static com.base.paginate.base.status.IStatus.STATUS_LOADING;

public class CustomEmptyView extends AbEmptytView {

    private TextView mTextView;
    private int mLoadMoreStatus;

    public CustomEmptyView(Context context) {
        super(context);
        mTextView = findViewById(R.id.base_empty_tv);
        mTextView.setTextColor(getResources().getColor(R.color.red));
        setStatus(STATUS_LOADING);
    }

    @Override
    public int getLayoutID() {
        return R.layout.paginate_layout_status_view;
    }

    @SuppressLint("SwitchIntDef")
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

        }

    }
}
