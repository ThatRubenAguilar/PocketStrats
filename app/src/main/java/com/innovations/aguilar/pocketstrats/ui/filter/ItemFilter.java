package com.innovations.aguilar.pocketstrats.ui.filter;

import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ruben on 7/26/2017.
 */
public abstract class ItemFilter<TItem, TFilterData> extends Filter
        implements ItemSetFilter<TFilterData> {
    private List<TItem> originalList;
    private List<TItem> filteredList;
    private TFilterData filterData;
    private BaseAdapter notifyAdapter;

    public ItemFilter(List<TItem> originalList, TFilterData filterData, BaseAdapter notifyAdapter) {
        this.originalList = originalList;
        this.filteredList = originalList;
        this.filterData = filterData;
        this.notifyAdapter = notifyAdapter;
    }

    public TFilterData getFilterData() {
        return filterData;
    }

    public List<TItem> getFilteredList() {
        return Collections.unmodifiableList(filteredList);
    }

    public List<TItem> getOriginalList() {
        return Collections.unmodifiableList(originalList);
    }

    public void filter(TFilterData filterData) {
        this.filterData = filterData;
        filter("");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        filteredList = (ArrayList<TItem>) results.values;
        notifyAdapter.notifyDataSetChanged();
    }

}
