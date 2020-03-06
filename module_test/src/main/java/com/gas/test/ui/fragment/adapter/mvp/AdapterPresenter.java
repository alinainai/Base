package com.gas.test.ui.fragment.adapter.mvp;


import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;
import com.base.paginate.interfaces.EmptyInterface;
import com.gas.test.R;
import com.gas.test.ui.fragment.adapter.SimpleAdapter;

import java.lang.ref.WeakReference;

import javax.inject.Inject;


@FragmentScope
public class AdapterPresenter extends BasePresenter<AdapterContract.Model, AdapterContract.View> {

    private static final int DELAYTIME = 700;
    public static final int FAKEREFRESH = 0x01;
    public static final int FAKELOADMORE = 0x02;
    public static final int ITEMCOUNT = 10;

    private int mPage = 1;
    private boolean showNormal = true;

    @Inject
    SimpleAdapter mAdapter;

    private DelayHandler mHandler;

    @Inject
    public AdapterPresenter(AdapterContract.Model model, AdapterContract.View rootView) {
        super(model, rootView);
    }

    public void initPresent(){
        mHandler=new DelayHandler(this);
        mHandler.sendEmptyMessageDelayed(FAKEREFRESH, DELAYTIME);
        mAdapter.setEmptyView(EmptyInterface.STATUS_LOADING);
    }

    public void loadData(boolean isRefresh){
        if(isRefresh){
            mHandler.sendEmptyMessageDelayed(FAKEREFRESH, DELAYTIME);
        }else{
            mHandler.sendEmptyMessageDelayed(FAKELOADMORE, DELAYTIME);
        }
    }

    public void insertData(boolean isOne){

        if(isOne){
            mAdapter.insert(mModel.provideNewItem());
        }else {
            mAdapter.insertAll(mModel.provideInsertItems(3));
        }

    }

    private void refreshData(){
        mPage = 1;
        if (showNormal) {
            mAdapter.setNewData(mModel.provideStringItems(mPage,ITEMCOUNT));
            showNormal = false;
        } else {
            mAdapter.setEmptyView(EmptyInterface.STATUS_FAIL);
            showNormal = true;
        }
        mView.hideLoading();

    }

    private void loadMoreData(){
        if (mPage < 5) {
            mPage++;
            mAdapter.setLoadMoreData(mModel.provideStringItems(mPage, ITEMCOUNT));
        } else {
            mAdapter.loadEnd();
        }
    }


    @Override
    public void onDestroy() {
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler=null;
        }

        super.onDestroy();
    }

    /**
     * 这里展示静态内部类的的使用
     * Handler 防止内存泄漏
     */
    private static class DelayHandler extends Handler {

        WeakReference<AdapterPresenter> mHost;

        DelayHandler(AdapterPresenter presenter) {
            mHost = new WeakReference<>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FAKEREFRESH:
                    if (null != mHost.get())
                        mHost.get().refreshData();
                    break;
                case FAKELOADMORE:
                    if (null != mHost.get())
                        mHost.get().loadMoreData();
                    break;
            }
        }
    }


}
