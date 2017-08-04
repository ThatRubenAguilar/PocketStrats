package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.common.base.Preconditions;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.ui.filter.MapItemFilter;
import com.innovations.aguilar.pocketstrats.ui.view.ListViewItem;
import com.innovations.aguilar.pocketstrats.ui.view.ViewDisplayer;

import java.util.List;

/**
 * Created by Ruben on 7/26/2017.
 */
public class MapSearchItemAdapter extends BaseAdapter implements Filterable {
    private final Context context;
    private final MapItemFilter filter;
    private final ViewDisplayer viewDisplay;

    public MapSearchItemAdapter(Context context, List<MapDataDTO> maps, ViewDisplayer<MapDataDTO> viewDisplay) {
        super();
        this.context = context;
        this.filter = new MapItemFilter(maps, this);
        this.viewDisplay = viewDisplay;
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

        final ListViewItem<MapDataDTO> textView = (ListViewItem<MapDataDTO>) rowView.findViewById(R.id.text_list_map_item);

        final MapDataDTO map = filteredList.get(position);
        configureListItemView(textView, map);
        return rowView;

    }

    private void configureListItemView(final ListViewItem<MapDataDTO> textView, final MapDataDTO map) {
        textView.setItemData(map);
        textView.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(context.getAssets()));
        textView.setText(map.getMapName());
        textView.setBackgroundDrawable(DrawableCompat.unwrap(getItemBackground(map)));
        // Using the OnItemClickListener had an odd delay on hitting back and selecting again,
        // So doing this manually here has proper UI reaction.
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDisplay.showView(map);
            }
        });
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("OnTouch", String.format("Touch Event: %s", event.getAction()));
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        int highlightColor = ContextCompat.getColor(context, R.color.accentToggleOff);
                        Drawable wrappedDrawable = DrawableCompat.wrap(textView.getBackground());
                        DrawableCompat.setTint(wrappedDrawable, highlightColor);
                        DrawableCompat.setTintMode(wrappedDrawable, PorterDuff.Mode.SCREEN);
                        textView.setBackgroundDrawable(DrawableCompat.unwrap(wrappedDrawable));
                        return false;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_SCROLL:
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL: {
                        Drawable wrappedDrawable = DrawableCompat.wrap(textView.getBackground());
                        DrawableCompat.setTintMode(wrappedDrawable, null);
                        textView.setBackgroundDrawable(DrawableCompat.unwrap(wrappedDrawable));
                        return false;
                    }
                }
                return false;
            }
        });
    }

    Drawable getItemBackground(MapDataDTO map) {
        String drawableName = String.format("ic_%s_list_item", map.getMapFileCompatName());
        int itemResourceId = context.getResources()
                .getIdentifier(drawableName, "drawable", context.getPackageName());
        return ContextCompat.getDrawable(context, itemResourceId);
    }

}
