package com.gas.test.ui.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;
import com.gas.test.R;
import com.gas.test.R2;
import com.gas.test.test.test.Ratio2ViewActivity;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;


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
public class ArticleFragment extends BaseFragment<ArticlePresenter> implements ArticleContract.View {


    @Inject
    String name;
    @BindView(R2.id.name)
    TextView mName;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerArticleComponent.builder().view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment_article, container, false);
    }

    //
//    @Override
//    protected int initLayoutId() {
//        return R.layout.test_fragment_article;
//    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Timber.tag("test_fragment").e("test_fragment");
        mName.setText("name="+name);
        mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Ratio2ViewActivity.class));
            }
        });

    }

    @Override
    public void setData(@Nullable Object data) {

    }


    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }

    @Override
    public void showMessage(@NonNull String message) {

    }


//    @Override
//    protected void lazyLoadData() {
//
//    }


}
