package trunk.doi.base.ui.fragment.classify.mvp;

import android.content.Context;

import com.base.lib.mvp.IView;

public interface ClassifyContract {

    interface View extends IView {
        Context getWrapContext();
    }

}
