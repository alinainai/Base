package trunk.doi.base.ui.fragment.mine.mvp;

import android.content.Context;

import com.base.lib.mvp.IView;

import java.util.List;

import io.reactivex.Observable;
import trunk.doi.base.bean.Beauty;
import trunk.doi.base.bean.GankItemData;

public interface MineContract {


    interface View extends IView {
        Context getWrapContext();
    }

    interface Model {
        Beauty getBody();
    }


}
