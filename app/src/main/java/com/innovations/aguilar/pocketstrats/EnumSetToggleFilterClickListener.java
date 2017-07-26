package com.innovations.aguilar.pocketstrats;

import android.view.View;
import android.widget.Checkable;

/**
 * Created by Ruben on 7/26/2017.
 */
class EnumSetToggleFilterClickListener<TEnum, TFilterData extends ItemSetToggle<TEnum>> implements View.OnClickListener {
    private TEnum enumValue;
    private ItemSetFilterProvider<TFilterData> setFilterProvider;
    private ItemSetFilter<TFilterData> setFilter;

    public EnumSetToggleFilterClickListener(TEnum enumValue,
                                            ItemSetFilterProvider<TFilterData> setFilterProvider,
                                            ItemSetFilter<TFilterData> setFilter) {
        this.enumValue = enumValue;
        this.setFilterProvider = setFilterProvider;
        this.setFilter = setFilter;
    }

    @Override
    public void onClick(View view) {
        Checkable b = (Checkable) view;
        TFilterData filterData = setFilterProvider.getFilterData();
        if (b.isChecked()) filterData.addItem(enumValue);
        else filterData.removeItem(enumValue);
        setFilterProvider.setFilterData(filterData);
        setFilter.filter(filterData);
    }
}
