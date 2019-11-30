package trunk.doi.base.ui.fragment.info.mvp;


import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import com.base.lib.di.scope.FragmentScope;

import javax.inject.Inject;

import trunk.doi.base.ui.fragment.info.mvp.InfoContract;

/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 14:54
 * ================================================
 */

@FragmentScope
public class InfoModel extends BaseModel implements InfoContract.Model {

    @Inject
    public InfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}