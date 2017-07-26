package com.innovations.aguilar.pocketstrats;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.innovations.aguilar.pocketstrats.dto.MapDataDTO;

import java.util.List;

/**
 * Created by Ruben on 7/26/2017.
 */
public class MapSearchItemAdapter extends ArrayAdapter<MapDataDTO> implements Filterable {
    private final Context context;
    private final MapItemFilter filter;

    public MapSearchItemAdapter(Context context, List<MapDataDTO> maps) {
        super(context, R.layout.map_list_item, maps);
        this.context = context;
        this.filter = new MapItemFilter(maps, this);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    public MapItemFilter getMapFilter() {
        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final List<MapDataDTO> filteredList = filter.getFilteredList();
        Preconditions.checkArgument(position < filteredList.size(),
                String.format("Position '%s' overflows the filtered results size of '%s'",
                        position, filteredList.size()));

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView rowView = (TextView) inflater.inflate(R.layout.map_list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.text_list_map_item);

        final MapDataDTO map = filteredList.get(position);
        textView.setText(map.getMapName());

        return rowView;
    }

}
