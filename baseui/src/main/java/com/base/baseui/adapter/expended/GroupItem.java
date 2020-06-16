package com.base.baseui.adapter.expended;

import java.util.List;

/**
 * ================================================
 * desc:
 *
 *   T 为group数据对象
 *   S 为child数据对象
 *
 * created by author ljx
 * Date  2020-02-02
 * email 569932357@qq.com
 *
 * ================================================
 */

public class GroupItem<T,C> extends BaseItem{

    /**head data*/
    private T groupData;

    /** childDatas*/
    private List<C> childData;

    /** 是否展开,  默认展开*/
    private boolean isExpand = true;


    /** 返回是否是父节点*/
    @Override
    public boolean isParent() {
        return true;
    }

    public boolean isExpand(){
        return isExpand;
    }

    public void onExpand() {
        isExpand = !isExpand;
    }

    public GroupItem(T groupData, List<C> childData, boolean isExpand) {
        this.groupData = groupData;
        this.childData = childData;
        this.isExpand = isExpand;
    }

    public boolean hasChild(){
        if(getChildData() == null || getChildData().isEmpty() ){
            return false;
        }
        return true;
    }

    public List<C> getChildData() {
        return childData;
    }

    public void setChildData(List<C> childData) {
        this.childData = childData;
    }

    public void removeChild(int childPosition){

    }

    public T getGroupData() {
        return groupData;
    }
}