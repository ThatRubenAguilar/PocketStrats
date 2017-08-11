package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapTipItemAdapter extends ExpandableRecyclerViewAdapter<HeaderViewHolder, ChildrenViewHolder> {

    private Context context;

    public MapTipItemAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
    }

    @Override
    public HeaderViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.map_tip_list_group, parent, false);
        return new HeaderViewHolder(view, context);
    }

    @Override
    public ChildrenViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.map_tip_list_item, parent, false);
        return new ChildrenViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ChildrenViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {

        final MapTipsChild mapTip = (MapTipsChild)group.getItems().get(childIndex);
        holder.setChildText(mapTip);
    }

    @Override
    public void onBindGroupViewHolder(HeaderViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {

        holder.setHeaderText(group);
    }

    public void collapseAll() {
        for (int i = 0; i < expandableList.expandedGroupIndexes.length; i++) {
            expandableList.expandedGroupIndexes[i] = false;
        }
        notifyDataSetChanged();
    }

}
