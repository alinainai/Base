package trunk.doi.base.ui.fragment.info.mvp;


import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;

import javax.inject.Inject;


@FragmentScope
public class InfoPresenter extends BasePresenter<InfoContract.Model, InfoContract.View> {

    @Inject
    public InfoPresenter(InfoContract.Model model, InfoContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
