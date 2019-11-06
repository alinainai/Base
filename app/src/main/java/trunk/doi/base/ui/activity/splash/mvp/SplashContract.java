package trunk.doi.base.ui.activity.splash.mvp;

import com.base.lib.mvp.IModel;
import com.base.lib.mvp.IView;

public interface SplashContract {


    interface View extends IView {
        void goMainActivity();
        void showTimeDown(long time);
        void showVersionCode(String code);
    }


    class SplashModle implements IModel {

        @Override
        public void onDestroy() {

        }
    }

}
