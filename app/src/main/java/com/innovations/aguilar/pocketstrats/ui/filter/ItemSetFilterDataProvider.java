package com.innovations.aguilar.pocketstrats.ui.filter;

/**
 * Created by Ruben on 7/26/2017.
 */
public interface ItemSetFilterDataProvider<TFilterData> {
    TFilterData getFilterData();

    void setFilterData(TFilterData filterData);
}
