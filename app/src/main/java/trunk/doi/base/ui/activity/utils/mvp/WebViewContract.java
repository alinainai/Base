package trunk.doi.base.ui.activity.utils.mvp;

import android.content.Context;

import com.base.lib.mvp.IView;

import java.util.List;

import io.reactivex.Observable;
import trunk.doi.base.bean.GankItemData;

public interface WebViewContract {


    interface View extends IView {
        Context getWrapContext();
    }

}
