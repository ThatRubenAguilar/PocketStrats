package com.innovations.aguilar.pocketstrats;

/**
 * Created by Ruben on 7/26/2017.
 */
// TODO: Implement on something
public interface ItemSetFilterProvider<TFilterData extends ItemSetToggle<?>> {
    TFilterData getFilterData();

    void setFilterData(TFilterData filterData);
}
