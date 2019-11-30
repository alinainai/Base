package trunk.doi.base.ui.fragment.product.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.FragmentScope;

import trunk.doi.base.ui.fragment.product.di.ProductModule;
import trunk.doi.base.ui.fragment.product.mvp.ProductContract;
import trunk.doi.base.ui.fragment.product.ProductFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 14:55
 * ================================================
 */

@FragmentScope
@Component(modules = ProductModule.class, dependencies = AppComponent.class)
public interface ProductComponent {

    void inject(ProductFragment fragment);


    @Component.Builder
    interface Builder {

        @BindsInstance
        ProductComponent.Builder view(ProductContract.View view);

        ProductComponent.Builder appComponent(AppComponent appComponent);

        ProductComponent build();

    }
}