package com.gas.zhihu.ui.map.mvp;


import android.text.TextUtils;

import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import com.base.lib.di.scope.ActivityScope;
import com.base.paginate.base.SingleAdapter;
import com.gas.zhihu.bean.MapBean;
import com.gas.zhihu.utils.MapBeanDbUtils;
import com.gas.zhihu.utils.ZhihuUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/24/2020 21:09
 * ================================================
 */

@ActivityScope
public class MapModel extends BaseModel implements MapContract.Model {

    @Inject
    public MapModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    @Override
    public int getMapDataCount(){
        return MapBeanDbUtils.getMapDataCount();
    }

    @Override
    public List<String> getRecordHistory() {
       String str = ZhihuUtils.getSearchRecord();
       if(TextUtils.isEmpty(str)){
           return Collections.emptyList();
       }else {
           String[] items = str.split(",");
           return Arrays.asList(items);
       }

    }

    @Override
    public void setRecordHistory(List<String> items) {

        StringBuilder sb=new StringBuilder();
        if(items!=null && !items.isEmpty()){
            for (String item : items) {
                sb.append(item);
                sb.append(",");
            }
            ZhihuUtils.setSearchRecord(sb.toString());
        }

    }

    @Override
    public void clearRecordHistory() {
        ZhihuUtils.setSearchRecord("");
    }

    @Override
    public List<String> getSearchKeySequence(String key) {
        return MapBeanDbUtils.getSearchKeysLike(key);
    }

}