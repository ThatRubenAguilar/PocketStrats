package com.innovations.aguilar.pocketstrats.ui.filter;

import android.widget.ArrayAdapter;

import com.innovations.aguilar.pocketstrats.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.dto.MapType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Ruben on 7/26/2017.
 */
public class MapItemFilter extends ItemFilter<MapDataDTO, MapItemFilterData> {
    public MapItemFilter(List<MapDataDTO> originalList, ArrayAdapter<MapDataDTO> notifyAdapter) {
        super(originalList, new MapItemFilterData(), notifyAdapter);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        final List<MapDataDTO> originalMaps = getOriginalList();
        final ArrayList<MapDataDTO> filterList = new ArrayList<>(originalMaps.size());

        for (MapDataDTO mapData :
                originalMaps) {
            if (matchesMapTypeAndName(mapData)) {
                filterList.add(mapData);
            }
        }

        results.values = filterList;
        results.count = filterList.size();

        return results;
    }

    boolean matchesMapTypeAndName(MapDataDTO mapData) {
        MapItemFilterData filterData = getFilterData();
        String mapNameFilter = filterData.getMapNameFilter().toString().toLowerCase();
        Set<MapType> mapTypeFilterSet = filterData.getMapTypeFilterSet();
        return (mapTypeFilterSet.contains(mapData.getMapTypeId())
                && mapData.getMapName().toLowerCase().contains(mapNameFilter));
    }
}
