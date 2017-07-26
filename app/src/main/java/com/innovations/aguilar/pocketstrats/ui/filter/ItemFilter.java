package com.innovations.aguilar.pocketstrats.ui.filter;

import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ruben on 7/26/2017.
 */
public abstract class ItemFilter<TItem, TFilterData> extends Filter
        implements ItemSetFilterDataProvider<TFilterData>, ItemSetFilter<TFilterData> {
    private List<TItem> originalList;
    private List<TItem> filteredList;
    private TFilterData filterData;
    private ArrayAdapter<TItem> notifyAdapter;

    public ItemFilter(List<TItem> originalList, TFilterData filterData, ArrayAdapter<TItem> notifyAdapter) {
        this.originalList = originalList;
        this.filteredList = originalList;
        this.filterData = filterData;
        this.notifyAdapter = notifyAdapter;
    }

    public TFilterData getFilterData() {
        return filterData;
    }

    public void setFilterData(TFilterData filterData) {
        this.filterData = filterData;
    }

    public List<TItem> getFilteredList() {
        return Collections.unmodifiableList(filteredList);
    }

    public List<TItem> getOriginalList() {
        return Collections.unmodifiableList(originalList);
    }

    public void filter(TFilterData filterData) {
        filter("");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        filteredList = (ArrayList<TItem>) results.values;
        notifyAdapter.notifyDataSetChanged();
    }

}
