package trunk.doi.base.ui.fragment.main.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.FragmentScope;

import trunk.doi.base.ui.fragment.main.di.MainModule;
import trunk.doi.base.ui.fragment.main.mvp.MainContract;
import trunk.doi.base.ui.fragment.main.MainFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 15:27
 * ================================================
 */

@FragmentScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {

    void inject(MainFragment fragment);


    @Component.Builder
    interface Builder {

        @BindsInstance
        MainComponent.Builder view(MainContract.View view);

        MainComponent.Builder appComponent(AppComponent appComponent);

        MainComponent build();

    }
}