package com.gas.app.learn.calendarviewV2.utils.transformdata;

import com.gas.app.learn.calendarviewV2.data.CalendarDayModel;

public class MonthDataTransform extends AbsRowDataTransform<CalendarDayModel> {

    public MonthDataTransform() {
        super(6, 7);
    }

    @Override
    protected int transformIndex(int index, int row, int column) {
        int pageCount = row * column;

        //current page index
        int curPageIndex = index / pageCount;
        int divisor = index % pageCount;

        int transformIndex;
        if (divisor % row == 0) {
            transformIndex = divisor / row;
        } else if (divisor % row == 1) {
            transformIndex = column + divisor / row;
        } else if (divisor % row == 2) {
            transformIndex = column * 2 + divisor / row;
        } else if (divisor % row == 3) {
            transformIndex = column * 3 + divisor / row;
        } else if (divisor % row == 4) {
            transformIndex = column * 4 + divisor / row;
        } else {
            transformIndex = column * 5 + divisor / row;
        }

        //this is very important
        transformIndex += curPageIndex * pageCount;

        return transformIndex;
    }

}
