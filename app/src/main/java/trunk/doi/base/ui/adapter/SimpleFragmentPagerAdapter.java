package trunk.doi.base.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 作者：Mr.Lee on 2017-9-12 13:41
 * 邮箱：569932357@qq.com
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private List<Fragment> mFragmentList;
    private List<String> mProtypes;
    private FragmentManager mFm;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragmentList, List<String> protypes) {
        super(fm);
        this.mFm=fm;
        this.mContext = context;
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
            removeFragment(container,position);
        return super.instantiateItem(container, position);
    }

    private void removeFragment(ViewGroup container, int index) {
        String tag = getFragmentTag(container.getId(), index);
        Fragment fragment = mFm.findFragmentByTag(tag);
        if (fragment == null)
            return;
        FragmentTransaction ft = mFm.beginTransaction();
        ft.remove(fragment);
        ft.commit();
        ft = null;
        mFm.executePendingTransactions();
    }

    private String getFragmentTag(int viewId, int index) {
        try {
            Class<FragmentPagerAdapter> cls = FragmentPagerAdapter.class;
            Class<?>[] parameterTypes = { int.class, long.class };
            Method method = cls.getDeclaredMethod("makeFragmentName",
                    parameterTypes);
            method.setAccessible(true);
            String tag = (String) method.invoke(this, viewId, index);
            return tag;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
