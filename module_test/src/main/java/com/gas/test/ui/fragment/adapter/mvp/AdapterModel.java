package com.gas.test.ui.fragment.adapter.mvp;


import com.base.lib.di.scope.FragmentScope;
import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.inject.Inject;

/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/29/2019 16:48
 * ================================================
 */

@FragmentScope
public class AdapterModel extends BaseModel implements AdapterContract.Model {

    @Inject
    public AdapterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public List<String> provideStringItems(int page, int itemCount) {

        List<String> data = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            data.add(String.format(Locale.CHINA, "第 %d 页的第 %d条数据", page, i));
        }
        return data;
    }

    @Override
    public List<String> provideInsertItems(int itemCount) {

        List<String> data = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            data.add(String.format(Locale.CHINA, "这是要插入第 %d 条新数据", i));
        }
        return data;
    }

    @Override
    public String provideNewItem() {
        return String.format(Locale.CHINA, "这是一条新数据，数据编码是 %d", new Random().nextInt(1000));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}