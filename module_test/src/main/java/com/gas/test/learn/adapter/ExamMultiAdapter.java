package com.gas.test.learn.adapter;

import android.content.Context;

import com.base.paginate.base.MultiAdapter;
import com.base.paginate.interfaces.EmptyInterface;
import com.base.paginate.interfaces.FooterInterface;

public abstract class ExamMultiAdapter<T> extends MultiAdapter<T> {

    protected OnItemRemoveListener<T> OnItemRemoveListener;

    public ExamMultiAdapter(Context context) {
        super(context);
    }

    public ExamMultiAdapter(Context context, boolean openLoadMore) {
        super( context, openLoadMore);
    }

    public ExamMultiAdapter(Context context, boolean openLoadMore, boolean openEmpty) {
        super(context, openLoadMore, openEmpty);
    }

    @Override
    protected EmptyInterface getEmptyLayout(Context context) {
        return new ExamEmptyView(context);
    }

    @Override
    protected FooterInterface getFooterLayout(Context context) {
        return new ExamLoadFooter(context);
    }

    public void setOnItemRemoveListener(OnItemRemoveListener<T> onItemRemoveListener) {
        OnItemRemoveListener = onItemRemoveListener;
    }

}
