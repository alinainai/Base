package trunk.doi.base.ui.fragment.mine.mvp;

import android.app.Application;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.rx.RxBindManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import trunk.doi.base.bean.GankItemData;

@FragmentScope
public class MinePresenter extends BasePresenter<MineModle, MineContract.View> {


    @Inject
    Application mApplication;

    @Inject
    public MinePresenter(MineModle model, MineContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
