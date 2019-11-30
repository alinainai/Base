package trunk.doi.base.ui.fragment.main.di;

import com.base.lib.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import trunk.doi.base.ui.fragment.main.mvp.MainContract;
import trunk.doi.base.ui.fragment.main.mvp.MainModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 15:27
 * ================================================
 */
@Module
public abstract class MainModule {

    @Binds
    abstract MainContract.Model bindMainModel(MainModel model);


}