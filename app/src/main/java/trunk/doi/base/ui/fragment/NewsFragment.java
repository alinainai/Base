package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.adapter.SimpleFragmentPagerAdapter;
import trunk.doi.base.ui.fragment.classify.GankItemFragment;
import trunk.doi.base.util.AppUtils;

/**
 * Created by ly on 2016/5/30 11:05.
 * 分类的fragment  (首页第二个栏目)
 */
public class NewsFragment extends BaseFragment {
    public static final String TAG = "NewsFragment";


    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;


    public static NewsFragment newInstance() {
        return new NewsFragment();
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        List<String> mTitles = AppUtils.stringArrayToList(mContext, R.array.gank);
        List<Fragment> mFragments = new ArrayList<>();
        for (String subtype : mTitles) {
            mFragments.add(GankItemFragment.newInstance(subtype));
        }
        mViewPager.setAdapter(new SimpleFragmentPagerAdapter(mContext, getChildFragmentManager(), mFragments, AppUtils.stringArrayToList(mContext, R.array.gankTitle)));
        mViewPager.setOffscreenPageLimit(mTitles.size() - 1);
        tabs.setupWithViewPager(mViewPager);

    }

    @Override
    public void initData() {

    }


}
