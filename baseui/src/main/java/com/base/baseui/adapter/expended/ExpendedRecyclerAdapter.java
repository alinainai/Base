package com.base.baseui.adapter.expended;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.base.baseui.adapter.ExamMultiAdapter;
import com.base.paginate.viewholder.PageViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpendedRecyclerAdapter<G, C> extends ExamMultiAdapter<Object> {

    public static final int GROUPTYPE = 0x01;
    public static final int CHILDTYPE = 0x02;

    private List<ExpendedData<G, C>> mExpended;


    public ExpendedRecyclerAdapter(Context context) {
        super( context,true, true);
        mExpended = new ArrayList<>();
    }

    public void setNewExpendData(List<ExpendedData<G, C>> expends) {


        if (expends == null || expends.size() == 0)
            return;
        mExpended.clear();
        mExpended.addAll(expends);

        GroupItem groupItem;

        List<Object> list = new ArrayList<>();

        for (int i = 0; i < mExpended.size(); i++) {
            if (mExpended.get(i).getGroupItem() != null) {
                groupItem = mExpended.get(i).getGroupItem();
            } else {
                continue;
            }
            list.add(groupItem);
            if (groupItem.hasChild() && groupItem.isExpand()) {
                list.addAll(groupItem.getChildData());
            }
        }
        setNewData(list);


    }

    public void loadMoreExpendData(List<ExpendedData<G, C>> expends) {


        if (expends == null || expends.size() == 0)
            return;
        mExpended.addAll(expends);
        GroupItem groupItem;

        List<Object> list = new ArrayList<>();

        for (int i = 0; i < mExpended.size(); i++) {
            if (mExpended.get(i).getGroupItem() != null) {
                groupItem = mExpended.get(i).getGroupItem();
            } else {
                continue;
            }
            list.add(groupItem);
            if (groupItem.hasChild() && groupItem.isExpand()) {
                list.addAll(groupItem.getChildData());
            }
        }
        setLoadMoreData(list);


    }


    @Override
    protected int getItemLayoutId(int viewType) {

        switch (viewType) {
            case GROUPTYPE:
                return groupLayoutId();

            case CHILDTYPE:
                return childLayoutId();

        }
        return -1;
    }

    protected abstract int groupLayoutId();

    protected abstract int childLayoutId();

    protected abstract void groupConvert(PageViewHolder holder, GroupItem<G, C> data, int position);

    protected abstract void childConvert(PageViewHolder holder, C data, int position);


    @Override
    protected int getViewType(int position, Object data) {
        if (data instanceof GroupItem) {
            return GROUPTYPE;
        } else {
            return CHILDTYPE;
        }
    }

    @Override
    protected void convert(RecyclerView.ViewHolder holder, Object data, int position, int viewType) {

        if (viewType == GROUPTYPE) {
            groupConvert((PageViewHolder)holder, (GroupItem<G, C>) data, position);
        } else {
            childConvert((PageViewHolder)holder, (C) data, position);
        }

    }


    /**
     * expandGroup
     *
     * @param position showingData position
     */
    protected void expandGroup(int position) {
        Object item = mData.get(position);
        if (null == item) {
            return;
        }
        if (!(item instanceof GroupItem)) {
            return;
        }
        if (((GroupItem) item).isExpand()) {
            return;
        }
        if (!canExpandAll()) {
            for (int i = 0; i < getDataCount(); i++) {
                if (i != position) {
                    int tempPosition = collapseGroup(i);
                    if (tempPosition != -1) {
                        position = tempPosition;
                    }
                }
            }
        }

        List<Object> tempChildren;
        if (((GroupItem) item).hasChild()) {
            tempChildren = ((GroupItem) item).getChildData();
            ((GroupItem) item).onExpand();
            if (canExpandAll()) {
                insertAll(tempChildren, position + 1);
            } else {
                int tempPsi = getItemPositionInData(item);
                insertAll(tempChildren, tempPsi + 1);
            }
        }
    }

    /**
     * collapseGroup
     *
     * @param position showingDatas position
     */
    protected int collapseGroup(int position) {
        Object item = mData.get(position);
        if (null == item) {
            return -1;
        }
        if (!(item instanceof GroupItem)) {
            return -1;
        }
        if (!((GroupItem) item).isExpand()) {
            return -1;
        }

        List<C> tempChild;
        if (((GroupItem) item).hasChild()) {
            tempChild = ((GroupItem) item).getChildData();
            ((GroupItem) item).onExpand();
            removeRange(position + 1, tempChild.size());
            return position;
        }
        return -1;
    }

    public void removeChild(int position) {

        Object item = mData.get(position);
        if (null == item) {
            return;
        }
        if (item instanceof GroupItem) {
            return;
        }
        for (ExpendedData<G, C> data : mExpended) {
            if (data.getGroupItem().getChildData().contains(item)) {
                data.getGroupItem().getChildData().remove(item);
                if (data.getGroupItem().getChildData().isEmpty()) {
                    data.getGroupItem().onExpand();
                    notifyItemChanged(getItemPositionInData(data.getGroupItem()));
                }
                break;
            }
        }
        remove(position);


    }

    /**
     * if return true Allow all expand otherwise Only one can be expand at the same time
     */
    protected boolean canExpandAll() {
        return true;
    }


}
