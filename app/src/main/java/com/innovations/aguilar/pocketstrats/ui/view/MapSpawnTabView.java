package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.MapSearchItemAdapter;

public class MapSpawnTabView extends LinearLayout {
    TextView mapIcon;
    TabLayout spawnTabs;

    public MapSpawnTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.map_spawn_tabs, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mapIcon = (TextView)findViewById(R.id.map_icon);
        spawnTabs = (TabLayout)findViewById(R.id.spawn_tabs);

        spawnTabs.addTab(configureTab(SpawnSide.Attack));
        spawnTabs.addTab(configureTab(SpawnSide.Defend));
    }

    TabLayout.Tab configureTab(SpawnSide side) {
        Context context = getContext();
        TabLayout.Tab sideTab = spawnTabs.newTab();
        // TODO: Inflate custom textviews and setup like mapIcon.
        // See also: https://stackoverflow.com/questions/31640563/how-do-i-change-a-tab-background-color-when-using-tablayout
        //sideTab.setCustomView();
        sideTab.setText(side.toString());
        return sideTab;
    }

    public void loadMapData(MapDataDTO map) {
        Context context = getContext();
        mapIcon.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(context.getAssets()));
        mapIcon.setText(map.getMapName());
        mapIcon.setBackground(DrawableCompat.unwrap(MapSearchItemAdapter.getItemBackground(context, map)));
    }
}
