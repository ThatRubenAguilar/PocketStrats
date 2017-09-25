package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.ui.view.ListViewItem;

import java.util.List;

/**
 * Created by Ruben on 9/24/2017.
 */
public class MapTipItemAdapter extends Adapter {
    final Context context;
    private List<TipsChild> tips;

    public MapTipItemAdapter(Context context, List<TipsChild> tips) {
        super();
        this.context = context;
        this.tips = tips;
    }


    @Override
    public int getCount() {
        return tips.size();
    }

    @Override
    public Object getItem(int i) {
        return tips.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.map_tip_list_item, parent, false);
        }
        else {
            rowView = convertView;
        }

        final TextView textView = (TextView) rowView;

        final TipsChild tip = tips.get(position);
        configureMapTipItem(textView, tip);
        return rowView;

    }

    private void configureMapTipItem(final TextView textView, final TipsChild tip) {
        if (tip.isSection())
            textView.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(context.getAssets()));
        textView.setText(tip.getMessage());

    }

}
