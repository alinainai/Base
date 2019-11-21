package trunk.doi.base.ui.main.mvp;

import android.content.Context;

import com.base.lib.mvp.IView;

public interface MainContract {

    interface View extends IView {
       Context getWrapContext();
    }

}
