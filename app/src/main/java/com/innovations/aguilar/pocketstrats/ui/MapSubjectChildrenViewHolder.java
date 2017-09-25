package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.innovations.aguilar.pocketstrats.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class MapSubjectChildrenViewHolder extends ChildViewHolder {

    private Context context;
    private RecyclerView childTipList;

    public MapSubjectChildrenViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        childTipList = (RecyclerView) itemView.findViewById(R.id.list_tips);
        //childTipList.setNestedScrollingEnabled(false);
    }

    public void configureChildView(MapSubjectChildInfo subjectChildInfo) {

        childTipList.setAdapter(new MapTipItemAdapter(context, subjectChildInfo.tips));
    }

}


