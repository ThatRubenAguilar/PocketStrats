package com.innovations.aguilar.pocketstrats.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.ui.viewholder.MapSubjectDetailsTipItemViewHolder;
import com.innovations.aguilar.pocketstrats.ui.TipText;

import java.util.List;

public class MapSubjectDetailsTipItemAdapter extends RecyclerView.Adapter<MapSubjectDetailsTipItemViewHolder> {

    private final Context context;
    private final List<TipText> subjectTips;

    public MapSubjectDetailsTipItemAdapter(Context context, List<TipText> subjectTips) {
        this.context = context;
        this.subjectTips = subjectTips;
    }

    @Override
    public MapSubjectDetailsTipItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.map_subject_details_tip_list_item, parent, false);
        return new MapSubjectDetailsTipItemViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(MapSubjectDetailsTipItemViewHolder holder, int position) {
        TipText tip = subjectTips.get(position);
        holder.configureMapTipItem(tip);
    }

    @Override
    public int getItemCount() {
        return subjectTips.size();
    }

}
