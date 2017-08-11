package com.innovations.aguilar.pocketstrats.ui;

import android.content.Context;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovations.aguilar.pocketstrats.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class HeaderViewHolder extends GroupViewHolder {

    private final TextView header;
    private final ImageView arrow;
    private final Context context;

    public HeaderViewHolder(View itemView, Context context) {
        super(itemView);
        header = (TextView) itemView.findViewById(R.id.text_list_map_tip_group);
        arrow = (ImageView)itemView.findViewById(R.id.arrow_map_tip_group);
        this.context = context;
    }

    public void setHeaderText(ExpandableGroup group) {
        header.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(context.getAssets()));
        header.setText(group.getTitle());
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}
