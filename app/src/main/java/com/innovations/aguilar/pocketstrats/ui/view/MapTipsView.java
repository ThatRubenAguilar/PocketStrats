package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.MainPaneContainer;
import com.innovations.aguilar.pocketstrats.ui.MapTipItemAdapter;
import com.innovations.aguilar.pocketstrats.ui.MapTipsChild;
import com.innovations.aguilar.pocketstrats.ui.MapTipsHeader;

import java.util.Collections;
import java.util.List;

// TODO: Add tests for these views and how they act on deletion/restore
public class MapTipsView extends CoordinatorLayout {

    RecyclerView tipsList;
    FloatingActionButton returnToTop;

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

        returnToTop = (FloatingActionButton)findViewById(R.id.button_return_to_top);

        tipsList = (RecyclerView)findViewById(R.id.list_tips);
        tipsList.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && returnToTop.isShown())
                    returnToTop.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    returnToTop.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        returnToTop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapTipItemAdapter)tipsList.getAdapter()).collapseAll();
                returnToTop.hide();
            }
        });
    }

    public void loadTipsForMap(MapDataDTO map) {
        List<MapTipsHeader> groupHeaders = Lists.newArrayList();
        List<MapTipsChild> mapTypeTips = getMapTypeTips(map);
        if (mapTypeTips.size() > 0) {
            groupHeaders.add(new MapTipsHeader("Mode", mapTypeTips));
        }
        List<MapTipsChild> mapTips = getMapTips(map);
        if (mapTips.size() > 0) {
            groupHeaders.add(new MapTipsHeader("Map", mapTips));
        }
        List<MapTipsChild> mapSegmentTips = getMapSegmentTipsForMap(map);
        if (mapSegmentTips.size() > 0) {
            // TODO: Query segments by Id to get names
            groupHeaders.add(new MapTipsHeader("Segment", mapSegmentTips));
        }
        List<MapTipsChild> mapLocationTips = getMapLocationTipsForMap(map);
        if (mapLocationTips.size() > 0) {
            // TODO: Query locations by Id to get names
            groupHeaders.add(new MapTipsHeader("Location", mapLocationTips));
        }
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        tipsList.setLayoutManager(manager);
        tipsList.setAdapter(new MapTipItemAdapter(getContext(), groupHeaders));
    }
    // TODO: Show floating button if one section is open and scrolling down has occurred

    List<MapTipsChild> getMapTypeTips(MapDataDTO map) {
        return Collections.unmodifiableList(Lists.newArrayList(new MapTipsChild[] {
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
           new MapTipsChild("Don't Stagger"),
            new MapTipsChild("Don't Die"),
            new MapTipsChild("Kill them all")
        }));
    }
    List<MapTipsChild> getMapTips(MapDataDTO map) {
        return Collections.unmodifiableList(Lists.newArrayList(new MapTipsChild[] {
                new MapTipsChild("Don't Stagger"),
                new MapTipsChild("Don't Die"),
                new MapTipsChild("Kill them all")
        }));
    }
    List<MapTipsChild> getMapSegmentTipsForMap(MapDataDTO map) {
        return Collections.unmodifiableList(Lists.newArrayList(new MapTipsChild[] {
        }));
    }
    List<MapTipsChild> getMapLocationTipsForMap(MapDataDTO map) {
        return Collections.unmodifiableList(Lists.newArrayList(new MapTipsChild[] {
        }));
    }
}
