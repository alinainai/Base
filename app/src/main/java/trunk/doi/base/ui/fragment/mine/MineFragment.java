package trunk.doi.base.ui.fragment.mine;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.lib.base.LazyLoadFragment;
import com.base.lib.di.component.AppComponent;

import java.util.List;

import trunk.doi.base.R;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.ui.fragment.mine.di.DaggerMineComponent;
import trunk.doi.base.ui.fragment.mine.mvp.MineContract;
import trunk.doi.base.ui.fragment.mine.mvp.MinePresenter;

/**
 * Author:
 * Time: 2016/8/12 14:28
 */
public class MineFragment extends LazyLoadFragment<MinePresenter> implements MineContract.View {

    public static final String TAG = "MineFragment";


    @Override
    public int initLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

        DaggerMineComponent.builder().appComponent(appComponent).view(this).build().inject(this);


    }


    @Override
    public void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {


    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


    }


    public static MineFragment newInstance() {
        return new MineFragment();
    }


    @Override
    public Context getWrapContext() {
        return mContext;
    }

    @Override
    public void onError() {

    }

    @Override
    protected void lazyLoadData() {

    }


}
