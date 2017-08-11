package com.innovations.aguilar.pocketstrats.ui;

import android.view.View;
import android.widget.TextView;

import com.innovations.aguilar.pocketstrats.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import org.w3c.dom.Text;

import java.util.List;

public class MapTipsHeader extends ExpandableGroup<MapTipsChild> {
    public MapTipsHeader(String title, List<MapTipsChild> items) {
        super(title, items);
    }
}

