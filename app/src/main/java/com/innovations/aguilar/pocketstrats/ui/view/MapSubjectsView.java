package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.MapDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSpecificTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapSubjectDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapTypeTipDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.SpawnSide;
import com.innovations.aguilar.pocketstrats.sql.query.MapDatabaseOpenHelper;
import com.innovations.aguilar.pocketstrats.sql.query.SqlDataAccessor;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.MainActivity;
import com.innovations.aguilar.pocketstrats.ui.MainPaneContainer;
import com.innovations.aguilar.pocketstrats.ui.MapSearchItemAdapter;
import com.innovations.aguilar.pocketstrats.ui.MapSubjectHeader;
import com.innovations.aguilar.pocketstrats.ui.MapSubjectItemAdapter;
import com.innovations.aguilar.pocketstrats.ui.MapSubjectChildInfo;
import com.innovations.aguilar.pocketstrats.ui.TipsChild;

import java.util.List;
import java.util.Map;

// TODO: Add tests for these views and how they act on deletion/restore
public class MapSubjectsView extends CoordinatorLayout {


    Supplier<MainPaneContainer> mainContainer;

    private TextView mapIcon;
    private TabLayout spawnTabs;
    private FloatingActionButton returnToTop;
    RecyclerView tipsList;

    Supplier<MapSubjectItemAdapter> attackSupplier;
    Supplier<MapSubjectItemAdapter> defendSupplier;

    public MapSubjectsView(Context context, @Nullable AttributeSet attrs) {
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
        mapIcon = (TextView)findViewById(R.id.map_icon);
        spawnTabs = (TabLayout)findViewById(R.id.spawn_tabs);
        tipsList = (RecyclerView)findViewById(R.id.list_subjects);

        spawnTabs.addTab(configureTab(spawnTabs.newTab(), SpawnSide.Attack));
        spawnTabs.addTab(configureTab(spawnTabs.newTab(),SpawnSide.Defend));

        spawnTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SpawnSide side = SpawnSide.valueOf(tab.getText().toString());
                configureTipsForTab(side);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
                ((MapSubjectItemAdapter) tipsList.getAdapter()).collapseAll();
                returnToTop.hide();
            }
        });
    }


    TabLayout.Tab configureTab(TabLayout.Tab sideTab, SpawnSide side) {
        Context context = getContext();
        // TODO: Inflate custom textviews and setup like mapIcon.
        // See also: https://stackoverflow.com/questions/31640563/how-do-i-change-a-tab-background-color-when-using-tablayout
        //sideTab.setCustomView();
        sideTab.setText(side.toString());
        return sideTab;
    }

    public void loadTipsForMap(final MapDataDTO map) {

        configureMapIcon(map);

        attackSupplier = new Supplier<MapSubjectItemAdapter>() {
            @Override
            public MapSubjectItemAdapter get() {
                List<MapSubjectHeader> groupHeaders = loadTipsForTab(map, SpawnSide.Attack);
                return new MapSubjectItemAdapter(getContext(), groupHeaders);
            }
        };
        defendSupplier = new Supplier<MapSubjectItemAdapter>() {
            @Override
            public MapSubjectItemAdapter get() {
                List<MapSubjectHeader> groupHeaders = loadTipsForTab(map, SpawnSide.Defend);
                return new MapSubjectItemAdapter(getContext(), groupHeaders);
            }
        };

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setAutoMeasureEnabled(true);
        tipsList.setLayoutManager(manager);
        configureTipsForTab(SpawnSide.Attack);
    }

    private void configureTipsForTab(SpawnSide side) {
        if (side == SpawnSide.Attack)
            tipsList.setAdapter(attackSupplier.get());
        else if (side == SpawnSide.Defend)
            tipsList.setAdapter(defendSupplier.get());
        else
            Log.w(this.getClass().toString(), "None SpawnSide Configuration Attempt");
    }

    private void configureMapIcon(MapDataDTO map) {
        Context context = getContext();
        mapIcon.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(context.getAssets()));
        mapIcon.setText(map.getMapName());
        mapIcon.setBackground(DrawableCompat.unwrap(MapSearchItemAdapter.getItemBackground(context, map)));
    }


    List<MapSubjectHeader> loadTipsForTab(MapDataDTO map, SpawnSide side) {
        List<MapSubjectDTO> subjects;
        List<MapSpecificTipDTO> specificTips;
        List<MapTypeTipDTO> typeTips;
        MapDatabaseOpenHelper openHelper = new MapDatabaseOpenHelper(getContext());
        try (SqlDataAccessor accessor = new SqlDataAccessor(openHelper.getReadableDatabase())) {
            subjects = accessor.GetMapSubjectsByMapOrSide(map.getMapId(), side);
            specificTips = accessor.GetMapSpecificTipsByMap(map.getMapId(), side);
            typeTips = accessor.GetMapTypeTipsByMapType(map.getMapTypeId(), side);
        }

        Map<Integer, List<MapSpecificTipDTO>> subjectTipMap =
                generateSubjectSpecificTipMap(specificTips);
        Map<Integer, List<MapTypeTipDTO>> typeTipMap =
                generateSubjectTypeTipMap( typeTips);

        List<MapSubjectHeader> groupHeaders = Lists.newArrayList();

        for (MapSubjectDTO subject :
                subjects) {
            if (typeTipMap.containsKey(subject.getMapSubjectId())) {
                List<MapSubjectChildInfo> children = generateChildMapTypeTips(typeTipMap, subject);
                MapSubjectHeader header = new MapSubjectHeader(subject, children);
                groupHeaders.add(header);
            }
        }

        for (MapSubjectDTO subject :
                subjects) {
            if (subjectTipMap.containsKey(subject.getMapSubjectId())) {
                List<MapSubjectChildInfo> children = generateChildMapSpecificTips(subjectTipMap, subject);
                MapSubjectHeader header = new MapSubjectHeader(subject, children);
                groupHeaders.add(header);
            }
        }

        return groupHeaders;
    }

    @NonNull
    private List<MapSubjectChildInfo> generateChildMapSpecificTips(Map<Integer, List<MapSpecificTipDTO>> subjectTipMap, MapSubjectDTO subject) {
        List<MapSpecificTipDTO> childrenSpecificTips = subjectTipMap.get(subject.getMapSubjectId());
        MapSubjectChildInfo child = new MapSubjectChildInfo();

        for (MapSpecificTipDTO specificTip :
                childrenSpecificTips) {
            boolean isSection = specificTip.getParentMapTipId() == null;
            child.getTips().add(new TipsChild(specificTip.getMapTipDescription(),
                    isSection));
        }
        return Lists.newArrayList(child);
    }
    @NonNull
    private List<MapSubjectChildInfo> generateChildMapTypeTips(Map<Integer, List<MapTypeTipDTO>> subjectTipMap, MapSubjectDTO subject) {
        List<MapTypeTipDTO> childrenTypeTips = subjectTipMap.get(subject.getMapSubjectId());
        MapSubjectChildInfo child = new MapSubjectChildInfo();
        for (MapTypeTipDTO typeTip :
                childrenTypeTips) {
            boolean isSection = typeTip.getParentMapTipId() == null;
            child.getTips().add(new TipsChild(typeTip.getMapTipDescription(),
                    isSection));
        }
        return Lists.newArrayList(child);
    }

    private Map<Integer, List<MapSpecificTipDTO>> generateSubjectSpecificTipMap(List<MapSpecificTipDTO> specificTips) {
        Map<Integer, List<MapSpecificTipDTO>> subjectTipMap = Maps.newHashMap();

        for (MapSpecificTipDTO specificTip :
                specificTips) {
            if (subjectTipMap.containsKey(specificTip.getMapSubjectId()))
                subjectTipMap.get(specificTip.getMapSubjectId()).add(specificTip);
            else {
                List<MapSpecificTipDTO> tips = Lists.newArrayList(specificTip);
                subjectTipMap.put(new Integer(specificTip.getMapSubjectId()), tips);
            }
        }
        return subjectTipMap;
    }
    private Map<Integer, List<MapTypeTipDTO>> generateSubjectTypeTipMap(List<MapTypeTipDTO> typeTips) {
        Map<Integer, List<MapTypeTipDTO>> subjectTipMap = Maps.newHashMap();

        for (MapTypeTipDTO typeTip :
                typeTips) {
            if (subjectTipMap.containsKey(typeTip.getMapSubjectId()))
                subjectTipMap.get(typeTip.getMapSubjectId()).add(typeTip);
            else {
                List<MapTypeTipDTO> tips = Lists.newArrayList(typeTip);
                subjectTipMap.put(new Integer(typeTip.getMapSubjectId()), tips);
            }
        }
        return subjectTipMap;
    }


//        List<MapSubjectHeader> groupHeaders = Lists.newArrayList();
//        List<MapTipsChild> mapTypeTips = getMapTypeTips(map);
//        if (mapTypeTips.size() > 0) {
//            groupHeaders.add(new MapSubjectHeader("Mode", mapTypeTips));
//        }
//        List<MapTipsChild> mapTips = getMapTips(map);
//        if (mapTips.size() > 0) {
//            groupHeaders.add(new MapSubjectHeader("Map", mapTips));
//        }
//        List<MapTipsChild> mapSegmentTips = getMapSegmentTipsForMap(map);
//        if (mapSegmentTips.size() > 0) {
//            // TODO: Query segments by Id to get names
//            groupHeaders.add(new MapSubjectHeader("Segment", mapSegmentTips));
//        }
//        List<MapTipsChild> mapLocationTips = getMapLocationTipsForMap(map);
//        if (mapLocationTips.size() > 0) {
//            // TODO: Query locations by Id to get names
//            groupHeaders.add(new MapSubjectHeader("Location", mapLocationTips));
//        }
//

    // TODO: Show floating button if one section is open and scrolling down has occurred


}
