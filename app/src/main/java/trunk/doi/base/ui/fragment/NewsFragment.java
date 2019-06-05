package trunk.doi.base.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.adapter.SimpleFragmentPagerAdapter;
import trunk.doi.base.ui.fragment.classify.ClassifyFragment;
import trunk.doi.base.util.GasUtils;
import trunk.doi.base.view.ColorFlipPagerTitleView;

/**
 * Created by ly on 2016/5/30 11:05.
 * 分类的fragment  (首页第二个栏目)
 */
public class NewsFragment extends BaseFragment {
    public static final String TAG = "NewsFragment";


    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private SimpleFragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;


    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_classify;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {

        List<String> mTitles = GasUtils.stringArrayToList(mContext, R.array.gank);
        mFragments = new ArrayList<>();

        for (String subtype : mTitles) {
            mFragments.add(ClassifyFragment.newInstance(subtype));
        }
        mAdapter = new SimpleFragmentPagerAdapter(mContext, getChildFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mTitles.size() - 1);

        CommonNavigator navigator = new CommonNavigator(mContext);
        navigator.setScrollPivotX(0.65f);
        navigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mTitles.get(index));
                simplePagerTitleView.setNormalColor(mContext.getResources().getColor(R.color.white));
                simplePagerTitleView.setSelectedColor(mContext.getResources().getColor(R.color.white));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setOnClickListener(v -> mViewPager.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setLineWidth(UIUtil.dip2px(context, 20));
                indicator.setRoundRadius(UIUtil.dip2px(context, 1.5));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(mContext.getResources().getColor(R.color.white));
                return indicator;
            }
        });
        magicIndicator.setNavigator(navigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);

    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    public void scrollToTop() {

        Fragment fragment= mFragments.get(mViewPager.getCurrentItem());
        if(fragment instanceof ClassifyFragment){
            ( (ClassifyFragment)fragment).scrollToTop();
        }

    }


}
