package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseFragment;
import trunk.doi.base.base.mvp.BaseMvpFragment;
import trunk.doi.base.ui.fragment.classify.GankItemFragment;
import trunk.doi.base.util.AppUtils;

/**
 * Created by ly on 2016/5/30 11:05.
 * 分类的fragment
 */
public class ClassifyFragment extends BaseFragment {
    public static final String TAG = "ClassifyFragment";
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private List<BaseMvpFragment> mFragments;
    private List<String> mTitles= new ArrayList<>();
    private TypePageAdapter mTypeAdapter;

    public static ClassifyFragment newInstance() {
        return new ClassifyFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainCartTitle.setText("分类");
        llBack.setVisibility(View.GONE);
        mTitles= AppUtils.stringArrayToList(context, R.array.gank);
        mFragments = new ArrayList<>();
        for (String subtype : mTitles) {
            mFragments.add(GankItemFragment.newInstance(subtype));
        }
        mTypeAdapter = new TypePageAdapter(getChildFragmentManager());
        mTypeAdapter.setData(mFragments, mTitles);
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

    public class TypePageAdapter extends FragmentPagerAdapter {
        private List<BaseMvpFragment> fragments;
        private List<String> titles;

        public TypePageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setData(List<BaseMvpFragment> fragments, List<String> titles) {
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
