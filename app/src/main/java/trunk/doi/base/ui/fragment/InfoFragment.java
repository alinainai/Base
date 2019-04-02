package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;

import butterknife.BindView;
import trunk.doi.base.R;

/**
 * Created by ly on 2016/5/30 11:05.
 * 首页的fragment  (首页第三个栏目)
 */
public class InfoFragment extends BaseFragment {





    public static final String TAG = "InfoFragment";


    public static InfoFragment newInstance() {
        return new InfoFragment();
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_info;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


    }

    @Override
    public void initData() {

    }




}
