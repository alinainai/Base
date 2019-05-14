package trunk.doi.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.base.paginate.base.status.AbLoadMoreFooter;
import com.base.paginate.base.status.IStatus;

import trunk.doi.base.R;

import static com.base.paginate.base.status.IStatus.STATUS_LOADING;

public class CustomFooterLoadMore extends AbLoadMoreFooter {

    private TextView mTextView;
    private ProgressBar mProgressBar;
    private int mLoadMoreStatus;

    @IStatus.StatusType
    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    @Override
    public int getLayoutID() {
        return com.base.paginate.R.layout.paginate_layout_load_footer;
    }


    public CustomFooterLoadMore(Context context) {
        super(context);
        mTextView = findViewById(com.base.paginate.R.id.base_empty_tv);
        mTextView.setTextColor(getResources().getColor(R.color.red));
        mProgressBar = findViewById(com.base.paginate.R.id.progress_bar);
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
                mTextView.setText(com.base.paginate.R.string.str_base_adapter_loading);
                show = true;
                break;
            case IStatus.STATUS_FAIL://加载失败
                mTextView.setText(com.base.paginate.R.string.str_base_adapter_normal);
                show = false;
                break;
            case IStatus.STATUS_END://全部加载
                mTextView.setText(com.base.paginate.R.string.str_base_adapter_end);
                show = false;
                break;
        }
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
