package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.dto.MapLocation;
import com.innovations.aguilar.pocketstrats.dto.MapLocationDTO;
import com.innovations.aguilar.pocketstrats.dto.MapSegmentDTO;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.MainPaneContainer;
import com.innovations.aguilar.pocketstrats.ui.MapTipItemAdapter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        List<String> groupHeaders = Lists.newArrayList();
        Map<String, List<String>> childDataMap = Maps.newHashMapWithExpectedSize(10);
        List<String> mapTypeTips = getMapTypeTips(map);
        if (mapTypeTips.size() > 0) {
            groupHeaders.add("Mode");
            childDataMap.put("Mode", mapTypeTips);
        }
        List<String> mapTips = getMapTips(map);
        if (mapTips.size() > 0) {
            groupHeaders.add("Map");
            childDataMap.put("Map", mapTips);
        }
        List<String> mapSegmentTips = getMapSegmentTipsForMap(map);
        if (mapSegmentTips.size() > 0) {
            // TODO: Query segments by Id to get names
            groupHeaders.add("Segment");
            childDataMap.put("Segment", mapSegmentTips);
        }
        List<String> mapLocationTips = getMapLocationTipsForMap(map);
        if (mapLocationTips.size() > 0) {
            // TODO: Query locations by Id to get names
            groupHeaders.add("Location");
            childDataMap.put("Location", mapLocationTips);
        }
        tipsList.setAdapter(new MapTipItemAdapter(getContext(), groupHeaders, childDataMap));
    }
    // TODO: Show floating button if one section is open and scrolling down has occurred

    List<String> getMapTypeTips(MapDataDTO map) {
        return Collections.unmodifiableList(Lists.newArrayList(new String[] {
           "Don't Stagger",
           "Don't Die",
           "Kill them all"
        }));
    }
    List<String> getMapTips(MapDataDTO map) {
        return Collections.unmodifiableList(Lists.newArrayList(new String[] {
                "Don't Stagger",
                "Don't Die",
                "Kill them all"
        }));
    }
    List<String> getMapSegmentTipsForMap(MapDataDTO map) {
        return Collections.unmodifiableList(Lists.newArrayList(new String[] {
        }));
    }
    List<String> getMapLocationTipsForMap(MapDataDTO map) {
        return Collections.unmodifiableList(Lists.newArrayList(new String[] {
        }));
    }
}
