package com.gas.test.utils.view.tablayout;

import android.view.ViewGroup;

import com.gas.test.utils.view.tablayout.utils.QMUIItemViewsAdapter;


public class QMUITabAdapter extends QMUIItemViewsAdapter<QMUITab, QMUITabView> implements QMUITabView.Callback {
    private QMUIBasicTabSegment mTabSegment;

    public QMUITabAdapter(QMUIBasicTabSegment tabSegment, ViewGroup parentView) {
        super(parentView);
        mTabSegment = tabSegment;
    }

    @Override
    protected QMUITabView createView(ViewGroup parentView) {
        return new QMUITabView(parentView.getContext());
    }

    @Override
    protected final void bind(QMUITab item, QMUITabView view, int position) {
        onBindTab(item, view, position);
        view.setCallback(this);
        // reset
        if (view.isSelected()) {
            view.setSelected(false);
            view.setSelectFraction(0f);
        }
    }

    protected void onBindTab(QMUITab item, QMUITabView view, int position) {
        view.bind(item);
    }

    @Override
    public void onClick(QMUITabView view) {
        int index = getViews().indexOf(view);
        mTabSegment.onClickTab(view, index);
    }

    @Override
    public void onDoubleClick(QMUITabView view) {
        int index = getViews().indexOf(view);
        mTabSegment.onDoubleClick(index);
    }

    @Override
    public void onLongClick(QMUITabView view) {
    }
}
