package com.innovations.aguilar.pocketstrats.ui;

import android.view.View;
import android.widget.TextView;

import com.innovations.aguilar.pocketstrats.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ChildrenViewHolder extends ChildViewHolder {

    private TextView childTip;

    public ChildrenViewHolder(View itemView) {
        super(itemView);
        childTip = (TextView) itemView.findViewById(R.id.text_list_map_tip_item);
    }

    public void setChildText(MapTipsChild mapTip) {
        childTip.setText(mapTip.getMapTip());
    }
}
