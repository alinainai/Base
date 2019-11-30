package trunk.doi.base.ui.fragment.info.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.FragmentScope;

import trunk.doi.base.ui.fragment.info.di.InfoModule;
import trunk.doi.base.ui.fragment.info.mvp.InfoContract;
import trunk.doi.base.ui.fragment.info.InfoFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 14:54
 * ================================================
 */

@FragmentScope
@Component(modules = InfoModule.class, dependencies = AppComponent.class)
public interface InfoComponent {

    void inject(InfoFragment fragment);


    @Component.Builder
    interface Builder {

        @BindsInstance
        InfoComponent.Builder view(InfoContract.View view);

        InfoComponent.Builder appComponent(AppComponent appComponent);

        InfoComponent build();

    }
}