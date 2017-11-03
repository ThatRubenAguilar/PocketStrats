package com.innovations.aguilar.pocketstrats.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.ui.view.SwipeListDisplayView;

import java.util.List;

public class MapSubjectDisplayItemAdapter extends SwipeListDisplayView.DataAdapter {

    private final Context context;
    private final List<MapSubjectDTO> subjects;

    public MapSubjectDisplayItemAdapter(Context context, List<MapSubjectDTO> subjects) {
        this.context = context;
        this.subjects = subjects;
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    @Override
    public void onBindData(SwipeListDisplayView view, int position) {
        MapSubjectDTO selectedSubject = subjects.get(position);
        view.setText(selectedSubject.getMapTipDescription());
    }
}
