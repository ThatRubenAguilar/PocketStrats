package com.innovations.aguilar.pocketstrats.ui.filter;

import android.widget.Filter;

/**
 * Created by Ruben on 7/26/2017.
 */
public interface ItemSetFilter<TFilterData> {
    TFilterData getFilterData();

    void filter(TFilterData filterData);
}
