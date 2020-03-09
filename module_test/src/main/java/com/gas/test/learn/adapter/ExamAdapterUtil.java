package com.gas.test.learn.adapter;

import com.base.paginate.base.BaseAdapter;
import com.base.paginate.interfaces.EmptyInterface;

import java.util.List;

public class ExamAdapterUtil {

    public static final int PAGE_NUM = 10;

    public static boolean setAdapterData(BaseAdapter adapter, List data, int page) {
        return setAdapterData(adapter, data, page, PAGE_NUM);
    }


    public static boolean setAdapterData(BaseAdapter adapter, List data, int page, int pageItemCount) {

        if (null != data) {
            //如果数据为空
            if (data.size() > 0) {
                if (page == 1) {
                    adapter.setNewData(data);
                } else {
                    adapter.setLoadMoreData(data);
                }
                if (data.size() < pageItemCount) {//如果小于10个
                    adapter.loadEnd();
                    return false;
                }
                return true;
            } else {
                if (page == 1) {
                    adapter.setEmptyView(EmptyInterface.STATUS_EMPTY);
                } else {
                    adapter.loadEnd();
                }
                return false;
            }

        } else {
            if (page == 1) {
                adapter.setEmptyView(EmptyInterface.STATUS_EMPTY);
            } else {
                adapter.loadEnd();
            }
            return false;
        }

    }

    /**
     * 网络请求错误
     *
     * @param adapter
     * @param page
     */
    public static void loadFail(BaseAdapter adapter, int page) {
        loadFail(adapter, page, true);
    }

    /**
     * @param adapter   适配器
     * @param page
     * @param isNetWork 是否是网络请求错误
     */
    public static void loadFail(BaseAdapter adapter, int page, boolean isNetWork) {
        if (page > 1) {
            adapter.showFooterFail();
        } else {
            adapter.setEmptyView(!isNetWork ? EmptyInterface.STATUS_FAIL : EmptyInterface.STATUS_NETWORK_FAIL);
        }
    }
}
