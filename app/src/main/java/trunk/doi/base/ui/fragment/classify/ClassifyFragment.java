package trunk.doi.base.ui.fragment.classify;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.ui.fragment.classify.di.DaggerClassifyComponent;
import trunk.doi.base.ui.fragment.classify.mvp.ClassifyContract;
import trunk.doi.base.ui.fragment.classify.mvp.ClassifyPresenter;

/**
 * Author:
 * Time: 2016/8/12 14:28
 */
public class ClassifyFragment extends BaseFragment<ClassifyPresenter> implements ClassifyContract.View {


    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;

    private boolean mTag = true;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

        DaggerClassifyComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classify, container, false);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


    }

    @Override
    public void setData(@Nullable Object data) {

    }


    public static ClassifyFragment newInstance() {
        return new ClassifyFragment();
    }


    @Override
    public Context getWrapContext() {
        return mContext;
    }


    @Override
    public void showMessage(@NonNull String message) {

    }

    @OnClick({R.id.button, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:

                if (mTag) {
                    button2.setEnabled(false);
                    mTag = false;
                } else {
                    button2.setEnabled(true);
                    mTag = true;
                }

                break;
            case R.id.button2:
                break;
        }
    }
}
