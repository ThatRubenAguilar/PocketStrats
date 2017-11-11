package com.innovations.aguilar.pocketstrats.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.listener.OnDataClickListener;

public class MapSubjectItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView header;
    private final ImageView arrow;
    private final Context context;
    private final View itemView;
    private OnDataClickListener<MapSubjectDTO> listener;

    private MapSubjectDTO configuredSubject;

    public MapSubjectItemViewHolder(View itemView, Context context) {
        super(itemView);
        this.header = (TextView) itemView.findViewById(R.id.text_list_map_tip_group);
        this.arrow = (ImageView)itemView.findViewById(R.id.arrow_map_tip_group);
        this.context = context;
        this.itemView = itemView;
    }

    public MapSubjectDTO getMapSubject() {
        return configuredSubject;
    }

    public void setOnItemClickListener(OnDataClickListener<MapSubjectDTO> listener) {
        this.listener = listener;
    }

    public void configureHeaderView(final MapSubjectDTO subject) {
        configuredSubject = subject;
        header.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(context.getAssets()));
        header.setText(subject.getMapTipDescription());

        if (listener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, subject);
                }
            });
        }
    }
}
