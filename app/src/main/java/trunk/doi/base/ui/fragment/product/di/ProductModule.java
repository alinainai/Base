package trunk.doi.base.ui.fragment.product.di;

import com.base.lib.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import trunk.doi.base.ui.fragment.product.mvp.ProductContract;
import trunk.doi.base.ui.fragment.product.mvp.ProductModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 14:55
 * ================================================
 */
@Module
public abstract class ProductModule {

    @Binds
    abstract ProductContract.Model bindProductModel(ProductModel model);


}