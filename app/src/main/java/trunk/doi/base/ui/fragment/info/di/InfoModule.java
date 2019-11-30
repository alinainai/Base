package trunk.doi.base.ui.fragment.info.di;

import com.base.lib.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import trunk.doi.base.ui.fragment.info.mvp.InfoContract;
import trunk.doi.base.ui.fragment.info.mvp.InfoModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 14:54
 * ================================================
 */
@Module
public abstract class InfoModule {

    @Binds
    abstract InfoContract.Model bindInfoModel(InfoModel model);


}