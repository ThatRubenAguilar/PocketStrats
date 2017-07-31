package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.innovations.aguilar.pocketstrats.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ruben on 7/28/2017.
 */

public class MapTipItemAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groupHeaders;
    private Map<String, List<String>> childDataMap;

    public MapTipItemAdapter(Context context, List<String> groupHeaders,
                                 Map<String, List<String>> childDataMap) {
        this.context = context;
        this.groupHeaders = groupHeaders;
        this.childDataMap = childDataMap;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.childDataMap.get(this.groupHeaders.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        View rowView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.map_tip_list_item, parent, false);
        }
        else {
            rowView = convertView;
        }

        TextView childView = (TextView) rowView
                .findViewById(R.id.text_list_map_tip_item);

        childView.setText(childText);
        return rowView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childDataMap.get(this.groupHeaders.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.groupHeaders.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.groupHeaders.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        View rowView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.map_tip_list_group, parent, false);
        }
        else {
            rowView = convertView;
        }

        TextView lblListHeader = (TextView) rowView
                .findViewById(R.id.text_list_map_tip_group);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return rowView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
