package com.innovations.aguilar.pocketstrats;

import com.innovations.aguilar.pocketstrats.dto.MapType;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by Ruben on 7/26/2017.
 */
public class MapItemFilterData implements ItemSetToggle<MapType> {
    private Set<MapType> mapTypeFilterSet;
    private CharSequence mapNameFilter;

    public MapItemFilterData() {
        this.mapTypeFilterSet = EnumSet.allOf(MapType.class);
        this.mapNameFilter = "";
    }

    public Set<MapType> getMapTypeFilterSet() {
        return mapTypeFilterSet;
    }

    public void setMapTypeFilterSet(Set<MapType> mapTypeFilterSet) {
        this.mapTypeFilterSet = mapTypeFilterSet;
    }

    public CharSequence getMapNameFilter() {
        return mapNameFilter;
    }

    public void setMapNameFilter(CharSequence mapNameFilter) {
        this.mapNameFilter = mapNameFilter;
    }

    @Override
    public void addItem(MapType item) {
        this.mapTypeFilterSet.add(item);
    }

    @Override
    public void removeItem(MapType item) {
        this.mapTypeFilterSet.remove(item);
    }
}
