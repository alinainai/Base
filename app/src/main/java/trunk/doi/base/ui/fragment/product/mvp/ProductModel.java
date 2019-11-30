package trunk.doi.base.ui.fragment.product.mvp;


import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import com.base.lib.di.scope.FragmentScope;

import javax.inject.Inject;

import trunk.doi.base.ui.fragment.product.mvp.ProductContract;

/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 14:55
 * ================================================
 */

@FragmentScope
public class ProductModel extends BaseModel implements ProductContract.Model {

    @Inject
    public ProductModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}