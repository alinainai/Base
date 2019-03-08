package trunk.doi.base.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseFragment;
import trunk.doi.base.util.ScreenUtils;

/**
 * Created by ly on 2016/5/30 11:05.
 * 首页的fragment  (首页第三个栏目)
 */
public class InfoFragment extends BaseFragment {


    @BindView(R.id.status_bar)
    View mStatusBar;


    public static final String TAG = "InfoFragment";


    public static InfoFragment newInstance() {
        return new InfoFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_info;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(mContext);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
    }


}
