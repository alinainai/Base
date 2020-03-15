package com.gas.beauty.ui.article;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.base.baseui.utils.GasUtil;
import com.base.baseui.view.TitleView;
import com.base.lib.base.LazyLoadFragment;
import com.base.lib.di.component.AppComponent;
import com.gas.beauty.R;
import com.gas.beauty.R2;
import com.gas.beauty.ui.article.di.DaggerArticleComponent;
import com.gas.beauty.ui.article.mvp.ArticleContract;
import com.gas.beauty.ui.article.mvp.ArticlePresenter;
import com.google.android.material.tabs.TabLayout;
import com.lib.commonsdk.adapter.AdapterViewPager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * ================================================
 * 展示 GasLazyLoadFragment 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms">Component wiki 官方文档</a>
 * Created by JessYan on 09/04/2019 11:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */

public class ArticleFragment extends LazyLoadFragment<ArticlePresenter> implements ArticleContract.View {


    @BindView(R2.id.article_titleView)
    TitleView mTitleView;
    @BindView(R2.id.magic_indicator)
    TabLayout mTabLayout;
    @BindView(R2.id.viewpager)
    ViewPager mViewPager;

    @Inject
    AdapterViewPager mAdapter;
    @Inject
    List<Fragment> mFragments;
    @Inject
    String[] mTitles;


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerArticleComponent.builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gank_fragment_article, container, false);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }


    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }


    @Override
    public Context getWrapContext() {
        return mContext;
    }

    @Override
    public Fragment getCurrentFragment() {
        return this;
    }


    @Override
    protected void lazyLoadData() {
        GasUtil.setIndicatorWidth(mTabLayout,  5);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mTitles.length - 1);
    }


    @Override
    public void showMessage(@NonNull String message) {

    }
}
