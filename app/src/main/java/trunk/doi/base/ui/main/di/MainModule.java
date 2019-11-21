package trunk.doi.base.ui.main.di;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.base.lib.di.scope.ActivityScope;
import com.lib.commonsdk.adapter.AdapterViewPager;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import trunk.doi.base.ui.fragment.AdapterFragment;
import trunk.doi.base.ui.fragment.NewsFragment;
import trunk.doi.base.ui.fragment.main.MainFragment;
import trunk.doi.base.ui.fragment.mine.MineFragment;
import trunk.doi.base.ui.main.mvp.MainContract;

@Module
public abstract class MainModule {


    @ActivityScope
    @Provides
    static AdapterViewPager providePagerAdapter(MainContract.View view, List<Fragment> fragments) {
        return new AdapterViewPager(((AppCompatActivity) view.getWrapContext()).getSupportFragmentManager(), fragments);
    }

    @ActivityScope
    @Provides
    static List<Fragment> provideFragments() {
        List<Fragment> mFragments = new ArrayList<>(4);
        mFragments.add(AdapterFragment.newInstance());
        mFragments.add(MainFragment.newInstance());
        mFragments.add(NewsFragment.newInstance());
        mFragments.add(MineFragment.newInstance());
        return mFragments;
    }


}
