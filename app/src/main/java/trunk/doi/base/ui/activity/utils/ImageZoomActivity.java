package trunk.doi.base.ui.activity.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.lib.base.BaseActivity;
import com.base.lib.base.BaseFragment;
import com.base.lib.view.StatusBarHeight;
import com.base.lib.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.view.ZoomViewPager;

/**
 * 显示图片的工具界面
 */

public class ImageZoomActivity extends BaseActivity {


    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView tv_title;
    @BindView(R.id.zoomViewPager)
    ZoomViewPager viewPager;
    @BindView(R.id.img_back)
    ImageView imgBack;

    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.tv_share)
    TextView tv_share;

    private int current;
    private List<String> urls;

    private TypePageAdapter adapter;
    private List<BaseFragment> mFragments=new ArrayList<>();


    @Override
    protected int initLayoutId(StatusBarHeight statusBar , TitleView titleView) {
        return R.layout.activity_zoomimage;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewPager.setPageMargin((int) getResources().getDisplayMetrics().density * 10);
        urls = getIntent().getStringArrayListExtra("imgpath");
        current = getIntent().getIntExtra("current", 0);

    }

    @Override
    public void setListener() {

        viewPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                current=position;
                tv_title.setText(String.valueOf((position + 1)) + "/" + String.valueOf(urls.size()));
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @Override
    public void initData() {

        if(urls.size()==0){
            return;
        }
        tv_title.setText(String.valueOf((current + 1)) + "/" + String.valueOf(urls.size()));

        for (String url : urls) {
            mFragments.add(ImageZoomFragment.newInstance(url));
        }
        adapter=new TypePageAdapter(getSupportFragmentManager());
        adapter.setData(mFragments,urls);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(current);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @OnClick({R.id.ll_back,R.id.tv_share,R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.tv_share:

                break;
            case R.id.tv_save:

                break;
        }
    }

    public static class TypePageAdapter extends FragmentPagerAdapter {
        private List<BaseFragment> fragments;
        private List<String> titles;

        public TypePageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setData(List<BaseFragment> fragments, List<String> titles) {
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
            return String.valueOf(position);
        }
    }

}
