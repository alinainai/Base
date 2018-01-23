package trunk.doi.base.ui.fragment.classify;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseFragment;
import trunk.doi.base.base.mvp.BaseMvpFragment;
import trunk.doi.base.util.AppUtils;
import trunk.doi.base.util.ScreenUtils;
import trunk.doi.base.view.BadgeView;

/**
 * Created by ly on 2016/5/30 11:05.
 * 分类的fragment  (首页第二个栏目)
 */
public class ClassifyFragment extends BaseFragment {
    public static final String TAG = "ClassifyFragment";
//    @BindView(R.id.main_cart_title)
//    TextView mainCartTitle;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.status_bar)
    View mStatusBar;

    private List<BaseMvpFragment> mFragments;
    private List<String> mTitles= new ArrayList<>();
    private TypePageAdapter mTypeAdapter;

    private ArrayList<BadgeView> badges=new ArrayList<>();
    private List<Integer> mBadgeCountList = new ArrayList<Integer>();

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

//        mainCartTitle.setText("分类");
        mTitles = AppUtils.stringArrayToList(mContext, R.array.gank);
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
        for(int i=0;i<mTabLayout.getTabCount();i++){



        }


    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {
    }

    @Override
    public void initData(Bundle savedInstanceState) {





    }

    /**
     * 设置Tablayout上的标题的角标
     */
    private void setUpTabBadge() {
        // 1. 最简单
//        for (int i = 0; i < mFragmentList.size(); i++) {
//            mBadgeViews.get(i).setTargetView(((ViewGroup) mTabLayout.getChildAt(0)).getChildAt(i));
//            mBadgeViews.get(i).setText(formatBadgeNumber(mBadgeCountList.get(i)));
//        }

        // 2. 最实用
        for (int i = 0; i < mFragments.size(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);

            // 更新Badge前,先remove原来的customView,否则Badge无法更新
            View customView = tab.getCustomView();
            if (customView != null) {
                ViewParent parent = customView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(customView);
                }
            }

            // 更新CustomView
            tab.setCustomView(mTypeAdapter.getTabItemView(i));
        }

        // 需加上以下代码,不然会出现更新Tab角标后,选中的Tab字体颜色不是选中状态的颜色
        mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getCustomView().setSelected(true);
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

        public View getTabItemView(int position) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab_layout_item, null);
            TextView textView = (TextView) view.findViewById(R.id.textview);
            textView.setText(titles.get(position));
            View target = view.findViewById(R.id.badgeview_target);
            return view;
        }



    }
}
