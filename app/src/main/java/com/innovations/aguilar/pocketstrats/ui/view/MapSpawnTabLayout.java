package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.adapter.MapSearchItemAdapter;

import org.w3c.dom.Text;

/**
 * Created by Ruben on 9/30/2017.
 */
public class MapSpawnTabLayout extends LinearLayout {
    private TextView mapIcon;
    private TabLayout spawnTabs;

    public MapSpawnTabLayout(Context context) {
        super(context);
        inflateLayout();
    }

    public MapSpawnTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateLayout();
    }

    public MapSpawnTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout();
    }

    private void inflateLayout() {
        inflate(getContext(), R.layout.map_spawn_tab_layout, this);
        mapIcon = (TextView)findViewById(R.id.map_icon);
        spawnTabs = (TabLayout)findViewById(R.id.spawn_tabs);
    }

    public SpawnSide getSelectedSpawnSide() {
        TabLayout.Tab tab = spawnTabs.getTabAt(spawnTabs.getSelectedTabPosition());
        return toSpawnSide(tab);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        spawnTabs.addTab(configureTab(spawnTabs.newTab(), SpawnSide.Attack, true));
        spawnTabs.addTab(configureTab(spawnTabs.newTab(), SpawnSide.Defend, false));
    }


    private TabLayout.Tab configureTab(TabLayout.Tab sideTab, SpawnSide side, boolean selected) {
        Context context = getContext();
        TextView tabButton = (TextView)inflate(context, R.layout.tab_button_layout, null);
        tabButton.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(context.getAssets()));

        // TODO: wtf with the tab background, make grid bg for tips, added app ns to tab layout,
        ViewGroup.LayoutParams layoutParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tabButton.setLayoutParams(layoutParams);

        tabButton.setText(side.toString());
        sideTab.setCustomView(tabButton);
        sideTab.setText(side.toString());

        setBackgroundSelector(sideTab, selected);

        return sideTab;
    }

    private void setBackgroundSelector(TabLayout.Tab sideTab, boolean selected) {
        TextView tabButton = (TextView)sideTab.getCustomView();
        int tabButtonId = selected ? R.drawable.tab_button_on : R.drawable.tab_button_off;
        Drawable wrappedDrawable = ContextCompat.getDrawable(getContext(), tabButtonId);
        tabButton.setBackground(DrawableCompat.unwrap(wrappedDrawable));
    }

    public void setMapIcon(MapDataDTO map) {
        configureMapIcon(map);
    }


    private void configureMapIcon(MapDataDTO map) {
        Context context = getContext();
        mapIcon.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(context.getAssets()));
        mapIcon.setText(map.getMapName());
        mapIcon.setBackground(DrawableCompat.unwrap(MapSearchItemAdapter.getItemBackground(context, map)));
    }

    public TabLayout.OnTabSelectedListener addOnTabSelectedListener(final OnTabSelectedListener listener) {
        TabLayout.OnTabSelectedListener tabListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setBackgroundSelector(tab, true);
                SpawnSide side = toSpawnSide(tab);
                listener.onTabSelected(tab, side);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setBackgroundSelector(tab, false);
                SpawnSide side = toSpawnSide(tab);
                listener.onTabUnselected(tab, side);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                SpawnSide side = toSpawnSide(tab);
                listener.onTabReselected(tab, side);
            }
        };
        spawnTabs.addOnTabSelectedListener(tabListener);
        return tabListener;
    }

    public interface OnTabSelectedListener {
        void onTabSelected(TabLayout.Tab tab, SpawnSide sideSelected);

        void onTabUnselected(TabLayout.Tab tab, SpawnSide sideUnselected);

        void onTabReselected(TabLayout.Tab tab, SpawnSide sideReselected);
    }

    private static SpawnSide toSpawnSide(TabLayout.Tab tab) {
        if (tab.getCustomView() != null) {
            TextView customView = (TextView) tab.getCustomView();
            return SpawnSide.valueOf(customView.getText().toString());
        }
        return SpawnSide.valueOf(tab.getText().toString());
    }
}
