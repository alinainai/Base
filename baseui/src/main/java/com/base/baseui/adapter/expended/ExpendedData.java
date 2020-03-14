package com.base.baseui.adapter.expended;

import java.util.List;

/**
 * ================================================
 * desc:
 *
 * created by author ljx
 * Date  2020-02-02
 * email 569932357@qq.com
 *
 * ================================================
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ExpendedData <T,S> {

    private GroupItem<T,S> groupItem;

    /**
     * @param groupData 组包装的数据
     * @param childData 子数据
     * @param isExpand   初始化展示数据时，该组数据是否展开
     */
    public ExpendedData(T groupData, List<S> childData, boolean isExpand) {
        this.groupItem = new GroupItem<>(groupData, childData, isExpand);
    }

    public ExpendedData(T groupData, List<S> childData) {
        this.groupItem = new GroupItem<>(groupData, childData, false);
    }

    public GroupItem getGroupItem() {
        return groupItem;
    }

    public void setGroupItem(GroupItem<T,S> groupItem) {
        this.groupItem = groupItem;
    }

    public T getGroupData() {
        return  groupItem.getGroupData();
    }

    public void removeChild(int position) {
        if (null == groupItem || !groupItem.hasChild()) {
            return;
        }
        groupItem.getChildData().remove(position);
    }

    public S getChild(int childPosition) {
        return  groupItem.getChildData().get(childPosition);
    }
}
