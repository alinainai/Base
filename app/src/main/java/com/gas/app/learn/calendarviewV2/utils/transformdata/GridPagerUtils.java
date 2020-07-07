package com.gas.app.learn.calendarviewV2.utils.transformdata;

import java.util.List;

public class GridPagerUtils {
    /**
     * transform and fill empty data
     *
     * @param orderTransform order transform
     * @param dataList
     * @param <T>
     * @return
     */
    public static <T> List<T> transformAndFillEmptyData(AbsRowDataTransform<T> orderTransform, List<T> dataList) {
        if (orderTransform == null)
            throw new IllegalArgumentException("orderTransform must be not null");

        if (dataList == null || dataList.size() == 0)
            throw new IllegalArgumentException("data list must be not null or size must > 0");

        return orderTransform.transform(dataList);
    }
}
