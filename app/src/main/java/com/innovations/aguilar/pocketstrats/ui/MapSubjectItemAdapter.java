package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovations.aguilar.pocketstrats.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class MapSubjectItemAdapter extends ExpandableRecyclerViewAdapter<MapSubjectHeaderViewHolder, MapSubjectChildrenViewHolder> {

    private Context context;

    public MapSubjectItemAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
    }

    @Override
    public MapSubjectHeaderViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.map_subject_list_group, parent, false);
        return new MapSubjectHeaderViewHolder(view, context);
    }

    @Override
    public MapSubjectChildrenViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.map_subject_list_item, parent, false);
        return new MapSubjectChildrenViewHolder(context, view);
    }

    @Override
    public void onBindChildViewHolder(MapSubjectChildrenViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {

        final MapSubjectChildInfo subjectChildInfo = (MapSubjectChildInfo)group.getItems().get(childIndex);
        holder.configureChildView(subjectChildInfo);
    }

    @Override
    public void onBindGroupViewHolder(MapSubjectHeaderViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {

        holder.configureHeaderView(group);
    }

    public void collapseAll() {
        for (int i = 0; i < expandableList.expandedGroupIndexes.length; i++) {
            expandableList.expandedGroupIndexes[i] = false;
        }
        notifyDataSetChanged();
    }

}
