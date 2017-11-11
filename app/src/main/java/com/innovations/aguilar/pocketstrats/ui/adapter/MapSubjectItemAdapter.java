package com.innovations.aguilar.pocketstrats.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.ui.viewholder.MapSubjectItemViewHolder;
import com.innovations.aguilar.pocketstrats.ui.listener.OnDataClickListener;

import java.util.List;

public class MapSubjectItemAdapter extends RecyclerView.Adapter<MapSubjectItemViewHolder> {

    private Context context;

    private List<MapSubjectDTO> subjects;
    private OnDataClickListener<MapSubjectDTO> listener = null;

    public MapSubjectItemAdapter(Context context, List<MapSubjectDTO> subjects) {
        super();
        this.context = context;
        this.subjects = subjects;
    }

    @Override
    public MapSubjectItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.map_subject_list_item, parent, false);
        return new MapSubjectItemViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(MapSubjectItemViewHolder holder, int position) {
        MapSubjectDTO subject = subjects.get(position);
        holder.setOnItemClickListener(listener);
        holder.configureHeaderView(subject);
    }

    public void setOnItemClickListener(OnDataClickListener<MapSubjectDTO> listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
