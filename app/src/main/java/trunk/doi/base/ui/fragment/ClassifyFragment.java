package trunk.doi.base.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseFragment;
import trunk.doi.base.ui.adapter.SimpleFragmentPagerAdapter;
import trunk.doi.base.ui.fragment.classify.GankItemFragment;
import trunk.doi.base.util.AppUtils;
import trunk.doi.base.util.ScreenUtils;

/**
 * Created by ly on 2016/5/30 11:05.
 * 分类的fragment  (首页第二个栏目)
 */
public class ClassifyFragment extends BaseFragment {
    public static final String TAG = "ClassifyFragment";
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.status_bar)
    View mStatusBar;

    private List<Fragment> mFragments;
    private List<String> mTitles = new ArrayList<>();
    private SimpleFragmentPagerAdapter mTypeAdapter;


    public static ClassifyFragment newInstance() {
        return new ClassifyFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_classify;
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
        mainCartTitle.setText("分类");
        mTitles = AppUtils.stringArrayToList(mContext, R.array.gank);
        mFragments = new ArrayList<>();
        for (String subtype : mTitles) {
            mFragments.add(GankItemFragment.newInstance(subtype));
        }
        mTypeAdapter = new SimpleFragmentPagerAdapter(mContext,getChildFragmentManager(),mFragments, mTitles);
        mViewPager.setAdapter(mTypeAdapter);
        mViewPager.setOffscreenPageLimit(mTitles.size() - 1);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {
    }

    @Override
    public void initData(Bundle savedInstanceState) {


    }


}
