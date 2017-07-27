package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.MainPaneContainer;

public class MapTipsView extends LinearLayout {

    ExpandableListView tipsList;

    Supplier<MainPaneContainer> mainContainer;

    public MapTipsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mainContainer = Suppliers.memoize(new Supplier<MainPaneContainer>() {
            @Override
            public MainPaneContainer get() {
                return (MainPaneContainer)((MainActivity)getContext()).findViewById(R.id.layout_main_container);
            }
        });

        tipsList = (ExpandableListView)findViewById(R.id.list_tips);
    }

    public void loadTipsForMap(MapDataDTO map) {

    }
}
