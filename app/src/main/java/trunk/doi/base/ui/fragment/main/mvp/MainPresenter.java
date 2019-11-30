package trunk.doi.base.ui.fragment.main.mvp;


import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;

import javax.inject.Inject;


@FragmentScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
