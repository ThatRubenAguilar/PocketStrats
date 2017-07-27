package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.ui.filter.MapItemFilter;

import java.lang.reflect.Array;
import java.util.List;

import static android.view.View.GONE;

/**
 * Created by Ruben on 7/26/2017.
 */
public class MapSearchItemAdapter extends BaseAdapter implements Filterable {
    private final Context context;
    private final MapItemFilter filter;

    public MapSearchItemAdapter(Context context, List<MapDataDTO> maps) {
        super();
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
    public int getCount() {
        return filter.getFilteredList().size();
    }

    @Override
    public Object getItem(int i) {
        return filter.getFilteredList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final List<MapDataDTO> filteredList = filter.getFilteredList();
        Preconditions.checkArgument(position < filteredList.size(),
                String.format("Position '%s' overflows the filtered results size of '%s'",
                        position, filteredList.size()));

        View rowView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.map_list_item, parent, false);
        }
        else {
            rowView = convertView;
        }

        TextView textView = (TextView) rowView.findViewById(R.id.text_list_map_item);

        final MapDataDTO map = filteredList.get(position);
        textView.setText(map.getMapName());
        return rowView;

    }

}
