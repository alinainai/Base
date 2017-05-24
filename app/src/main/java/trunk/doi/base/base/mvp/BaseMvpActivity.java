package trunk.doi.base.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import trunk.doi.base.base.BaseActivity;


/**
 * Author: Othershe
 * Time:  2016/8/11 11:14
 */
public abstract class BaseMvpActivity<V, P extends BasePresenter<V>> extends BaseActivity {
    protected P mPresenter;

    protected abstract P initPresenter();

    protected abstract void fetchData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        mPresenter.attach((V) this);

        fetchData();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }
}
