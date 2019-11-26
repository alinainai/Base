package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.lib.base.LazyLoadFragment;
import com.base.lib.di.component.AppComponent;

import trunk.doi.base.R;

/**
 * Created by ly on 2016/5/30 11:05.
 * 分类的fragment  (首页第二个栏目)
 */
public class NewsFragment extends LazyLoadFragment {
    public static final String TAG = "NewsFragment";


    public static NewsFragment newInstance() {
        return new NewsFragment();
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classify, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


    }


    @Override
    public void setData(@Nullable Object data) {


    }


    @Override
    protected void lazyLoadData() {

    }
}
