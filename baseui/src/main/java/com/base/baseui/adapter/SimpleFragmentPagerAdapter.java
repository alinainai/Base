package com.base.baseui.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 作者：Mr.Lee on 2017-9-12 13:41
 * 邮箱：569932357@qq.com
 */

public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragmentList;
    private List<String> mProtypes;
    private FragmentManager mFm;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragmentList, List<String> protypes) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mFm = fm;
        this.mFragmentList = fragmentList;
        this.mProtypes = protypes;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mProtypes.get(position);
    }


}
