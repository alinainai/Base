package com.gas.test.learn.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.base.paginate.base.SingleAdapter;
import com.base.paginate.interfaces.EmptyInterface;
import com.base.paginate.interfaces.FooterInterface;

public abstract class ExamAdapter<T> extends SingleAdapter<T> {


    protected OnItemRemoveListener<T> OnItemRemoveListener;

    public ExamAdapter(Context context) {
        super(context);
        init();
    }

    public ExamAdapter(Context context, boolean openLoadMore) {
        super(context, openLoadMore);
        init();
    }

    public ExamAdapter(Context context, boolean openLoadMore, boolean openEmpty) {
        super(context, openLoadMore, openEmpty);
        init();
    }

    private void init() {

    }


    @Override
    protected EmptyInterface getEmptyLayout(Context context) {
        ExamEmptyView emptyView = new ExamEmptyView(context);
        if (!TextUtils.isEmpty(getDataEmptyStr())) {
            emptyView.setDataEmptyStr(getDataEmptyStr());
        }
        if (getDataEmptyImgRes() != -1) {
            emptyView.setDataEmptyRes(getDataEmptyImgRes());
        }

        return emptyView;
    }

    @Override
    protected FooterInterface getFooterLayout(Context context) {
        return new ExamLoadFooter(context);
    }


    protected String getDataEmptyStr() {
        return "";
    }

    protected int getDataEmptyImgRes() {
        return -1;
    }

    public void setOnItemRemoveListener(OnItemRemoveListener<T> onItemRemoveListener) {
        OnItemRemoveListener = onItemRemoveListener;
    }


}
