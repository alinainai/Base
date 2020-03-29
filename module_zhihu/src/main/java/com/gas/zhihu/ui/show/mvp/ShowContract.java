package com.gas.zhihu.ui.show.mvp;

import com.base.lib.mvp.IModel;
import com.base.lib.mvp.IView;
import com.gas.zhihu.bean.MapBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/28/2020 21:18
 * ================================================
 */
public interface ShowContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void setDataInfo(MapBean data);
        void setQrCode(String data);
        void successView();
        void showLoading();
        void emptyView();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        MapBean getMapInfo(String key);
        MapBean getDefaultMapInfo();
    }
}
