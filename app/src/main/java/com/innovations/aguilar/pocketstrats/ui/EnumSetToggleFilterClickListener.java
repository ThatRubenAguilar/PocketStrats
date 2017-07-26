package com.innovations.aguilar.pocketstrats.ui;

import android.view.View;
import android.widget.Checkable;

import com.innovations.aguilar.pocketstrats.ui.filter.ItemSetFilter;
import com.innovations.aguilar.pocketstrats.ui.filter.ItemSetFilterDataProvider;
import com.innovations.aguilar.pocketstrats.ui.filter.ItemSetToggle;

/**
 * Created by Ruben on 7/26/2017.
 */
public class EnumSetToggleFilterClickListener<TEnum, TFilterData extends ItemSetToggle<TEnum>> implements View.OnClickListener {
    private TEnum enumValue;
    private ItemSetFilterDataProvider<TFilterData> setFilterDataProvider;
    private ItemSetFilter<TFilterData> setFilter;

    public EnumSetToggleFilterClickListener(TEnum enumValue,
                                            ItemSetFilterDataProvider<TFilterData> setFilterDataProvider,
                                            ItemSetFilter<TFilterData> setFilter) {
        this.enumValue = enumValue;
        this.setFilterDataProvider = setFilterDataProvider;
        this.setFilter = setFilter;
    }

    @Override
    public void onClick(View view) {
        Checkable b = (Checkable) view;
        TFilterData filterData = setFilterDataProvider.getFilterData();
        if (b.isChecked()) filterData.addItem(enumValue);
        else filterData.removeItem(enumValue);
        setFilterDataProvider.setFilterData(filterData);
        setFilter.filter(filterData);
    }
}
