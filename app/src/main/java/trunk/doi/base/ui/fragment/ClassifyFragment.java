package trunk.doi.base.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseFragment;
import trunk.doi.base.ui.adapter.SimpleFragmentPagerAdapter;
import trunk.doi.base.ui.fragment.classify.GankItemFragment;
import trunk.doi.base.util.AppUtils;
import trunk.doi.base.util.ScreenUtils;
import trunk.doi.base.view.PagerSlidingTabStrip;

/**
 * Created by ly on 2016/5/30 11:05.
 * 分类的fragment  (首页第二个栏目)
 */
public class ClassifyFragment extends BaseFragment {
    public static final String TAG = "ClassifyFragment";


    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.status_bar)
    View mStatusBar;


    public static ClassifyFragment newInstance() {
        return new ClassifyFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //添加状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(mContext);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
        List<String> mTitles = AppUtils.stringArrayToList(mContext, R.array.gank);
        List<Fragment> mFragments = new ArrayList<>();
        for (String subtype : mTitles) {
            mFragments.add(GankItemFragment.newInstance(subtype));
        }
        mViewPager.setAdapter(new SimpleFragmentPagerAdapter(mContext, getChildFragmentManager(), mFragments, AppUtils.stringArrayToList(mContext, R.array.gankTitle)));
        mViewPager.setOffscreenPageLimit(mTitles.size() - 1);
        tabs.setViewPager(mViewPager);

    }


}
